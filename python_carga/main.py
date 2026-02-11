import os
import psycopg2
from datetime import datetime, timedelta
import random
from faker import Faker

# ==========================
# CONFIGURAÇÕES
# ==========================
DB_HOST = os.getenv('DB_HOST', 'localhost')
DB_PORT = os.getenv('DB_PORT', '5432')
DB_NAME = os.getenv('DB_NAME', 'hackaton_db')
DB_USER = os.getenv('DB_USER', 'user')
DB_PASSWORD = os.getenv('DB_PASSWORD', 'password')

fake = Faker('pt_BR')

# ==========================
# CONSTANTES DO DOMÍNIO
# ==========================
DOENCAS_CRONICAS = [
    {'nome': 'Hipertensão Arterial', 'cid10': 'I10'},
    {'nome': 'Diabetes Mellitus tipo 2', 'cid10': 'E11'},

]

CEPS_SP = [
    '01000000', '02000000', '03000000',
    '04000000', '05000000', '06000000'
]

ENDERECOS_SP = [
    'Rua Augusta, 2000, Consolação, São Paulo',
    'Avenida Paulista, 1000, Bela Vista, São Paulo',
    'Rua Oscar Freire, 500, Pinheiros, São Paulo',
    'Avenida Faria Lima, 1500, Itaim Bibi, São Paulo',
    'Rua dos Pinheiros, 2500, Pinheiros, São Paulo',
    'Avenida Brasil, 3000, Consolação, São Paulo',
    'Rua Haddock Lobo, 800, Cerqueira César, São Paulo',
    'Avenida Rebouças, 1200, Pinheiros, São Paulo',
    'Rua Bela Cintra, 600, Consolação, São Paulo',
    'Avenida Imirim, 1800, Vila Madalena, São Paulo',
    'Rua Groenlândia, 400, Vila Madalena, São Paulo',
    'Avenida Brigadeiro Faria Lima, 2200, Itaim Bibi, São Paulo',
    'Rua Bandeira Paulista, 700, Itaim Bibi, São Paulo',
    'Avenida Morumbi, 1400, Vila Olímpia, São Paulo',
    'Rua Butantã, 250, Pinheiros, São Paulo',
    'Avenida Santo Amaro, 1600, Santo Amaro, São Paulo',
    'Rua Gaivota, 900, Itanhaém (zona), São Paulo',
    'Avenida Presidente Wilson, 500, Centro, São Paulo',
    'Rua 25 de Março, 800, Centro, São Paulo',
    'Avenida São João, 1300, Centro, São Paulo',
]

OBSERVACOES = [
    'Paciente relata bem-estar geral',
    'Apresenta sintomas leves de cansaço',
    'Adesão ao tratamento adequada',
    'Necessário ajuste de medicação',
    'Paciente apresenta edema em membros inferiores',
    'Controle pressórico adequado',
    'Glicemia elevada, aumentar atividade física',
    'Paciente com dispneia aos esforços',
    'Possível retenção hídrica',
    'Orientado restrição hídrica e de sódio',
    'Glicemia elevada, reforçada orientação alimentar',
    'Paciente relata dificuldade em manter dieta',
    'Boa adesão ao tratamento',
    'Uso irregular de medicação',
]

# ==========================
# FUNÇÕES AUXILIARES
# ==========================
def conectar_banco():
    """Conecta ao banco de dados"""
    try:
        conn = psycopg2.connect(
            host=DB_HOST,
            port=DB_PORT,
            database=DB_NAME,
            user=DB_USER,
            password=DB_PASSWORD
        )
        return conn
    except psycopg2.Error as e:
        print(f"Erro ao conectar ao banco de dados: {e}")
        return None


def gerar_cpf():
    """Gera um CPF válido para testes"""
    cpf = [random.randint(0, 9) for _ in range(9)]

    # Primeiro dígito verificador
    soma = sum(cpf[i] * (10 - i) for i in range(9))
    resto = soma % 11
    primeiro_digito = 0 if resto < 2 else 11 - resto
    cpf.append(primeiro_digito)

    # Segundo dígito verificador
    soma = sum(cpf[i] * (11 - i) for i in range(10))
    resto = soma % 11
    segundo_digito = 0 if resto < 2 else 11 - resto
    cpf.append(segundo_digito)

    return ''.join(map(str, cpf))


def gerar_cep():
    """Gera um CEP brasileiro válido (8 dígitos)"""
    return random.choice(CEPS_SP)

# ==========================
# INSERTS
# ==========================
def inserir_doencas_cronicas(cursor, conn):
    """Insere as doenças crônicas padrão"""
    print("Inserindo doenças crônicas...")
    ids = {}
    
    for doenca in DOENCAS_CRONICAS:
        try:
            cursor.execute(
                """
                INSERT INTO doenca_cronica (nome, cid10)
                VALUES (%s, %s)
                ON CONFLICT (cid10) DO NOTHING
                """,
                (doenca['nome'], doenca['cid10'])
            )
        except psycopg2.Error as e:
            print(f"Erro ao inserir doença {doenca['nome']}: {e}")
            conn.rollback()
            cursor = conn.cursor()
            continue

    try:
        cursor.execute("SELECT id, cid10 FROM doenca_cronica")
        for row in cursor.fetchall():
            ids[row[1]] = row[0]
    except psycopg2.Error as e:
        print(f"Erro ao selecionar doenças: {e}")
        conn.rollback()
        cursor = conn.cursor()

    return ids


def inserir_pacientes(cursor, conn, quantidade=20):
    """Insere pacientes com dados realistas"""
    print(f"Inserindo {quantidade} pacientes...")
    ids = []
    
    for _ in range(quantidade):
        nome = fake.name()
        data_nascimento = fake.date_of_birth(minimum_age=30, maximum_age=85)
        sexo = random.choices(['M', 'F', 'O'], weights=[48, 48, 4], k=1)[0]
        cpf = gerar_cpf()
        nacionalidade = 'Brasileira'
        cep = gerar_cep()
        endereco = random.choice(ENDERECOS_SP)
        
        try:
            cursor.execute(
                """
                INSERT INTO paciente
                (nome, data_nascimento, sexo, cpf, nacionalidade, cep, endereco)
                VALUES (%s, %s, %s, %s, %s, %s, %s)
                RETURNING id
                """,
                (nome, data_nascimento, sexo, cpf, nacionalidade, cep, endereco)
            )
            ids.append(cursor.fetchone()[0])
        except psycopg2.Error as e:
            print(f"Erro ao inserir paciente: {e}")
            conn.rollback()
            cursor = conn.cursor()
            continue
    
    return ids


def inserir_paciente_doenca(cursor, conn, pacientes_ids, doencas_ids):
    """Associa doenças crônicas aos pacientes"""
    print("Associando doenças aos pacientes...")
    mapa_paciente_doencas = {}

    for paciente_id in pacientes_ids:
        # Cada paciente tem entre 1 e N doenças crônicas (máximo o que tem disponível)
        max_doencas = min(3, len(doencas_ids))
        num_doencas = random.randint(1, max_doencas)
        doencas_selecionadas = random.sample(list(doencas_ids.values()), num_doencas)
        mapa_paciente_doencas[paciente_id] = doencas_selecionadas

        for doenca_id in doencas_selecionadas:
            # Data de diagnóstico entre 1 e 10 anos atrás
            dias_atras = random.randint(365, 3650)
            data_diagnostico = datetime.now().date() - timedelta(days=dias_atras)
            
            try:
                cursor.execute(
                    """
                    INSERT INTO paciente_doenca
                    (paciente_id, doenca_cronica_id, data_diagnostico, ativo)
                    VALUES (%s, %s, %s, %s)
                    """,
                    (paciente_id, doenca_id, data_diagnostico, True)
                )
            except psycopg2.Error as e:
                print(f"Erro ao associar doença ao paciente: {e}")
                conn.rollback()
                cursor = conn.cursor()
                continue

    return mapa_paciente_doencas


def inserir_prontuarios(cursor, conn, pacientes_ids):
    """Cria prontuários para cada paciente"""
    print("Criando prontuários...")
    prontuarios = {}
    
    for paciente_id in pacientes_ids:
        try:
            cursor.execute(
                """
                INSERT INTO prontuario (paciente_id, ativo)
                VALUES (%s, %s)
                RETURNING id
                """,
                (paciente_id, True)
            )
            prontuarios[paciente_id] = cursor.fetchone()[0]
        except psycopg2.Error as e:
            print(f"Erro ao criar prontuário: {e}")
            conn.rollback()
            cursor = conn.cursor()
            continue
    
    return prontuarios


def inserir_evolucoes_clinicas(cursor, conn, prontuarios, mapa_doencas):
    """Insere histórico de evolução clínica realista"""
    print("Inserindo histórico clínico...")
    
    for paciente_id, prontuario_id in prontuarios.items():
        # Cada prontuário tem entre 5 e 15 registros de evolução clínica
        num_registros = random.randint(5, 15)
        
        for i in range(num_registros):
            dias_atras = random.randint(1, 730)
            data_atendimento = datetime.now() - timedelta(days=dias_atras)
            
            # Valores realistas para pressão arterial
            pressao_sistolica = random.randint(110, 180)
            pressao_diastolica = random.randint(70, 110)
            
            # Valores realistas para glicemia (em mg/dL)
            glicemia = random.randint(80, 250)
            
            # Peso em kg (entre 50 e 120)
            peso = round(random.uniform(50, 120), 2)
            
            observacoes = random.choice(OBSERVACOES)
            
            try:
                cursor.execute(
                    """
                    INSERT INTO evolucao_clinica
                    (prontuario_id, data_atendimento, pressao_sistolica,
                     pressao_diastolica, glicemia, peso, observacoes)
                    VALUES (%s, %s, %s, %s, %s, %s, %s)
                    """,
                    (prontuario_id, data_atendimento, pressao_sistolica,
                     pressao_diastolica, glicemia, peso, observacoes)
                )
            except psycopg2.Error as e:
                print(f"Erro ao inserir evolução clínica: {e}")
                conn.rollback()
                cursor = conn.cursor()
                continue

# ==========================
# MAIN
# ==========================
def main():
    """Função principal"""
    print("Iniciando carga de dados simulados...\n")
    
    conn = conectar_banco()
    if not conn:
        return
    
    cursor = conn.cursor()

    try:
        doencas_ids = inserir_doencas_cronicas(cursor, conn)
        pacientes_ids = inserir_pacientes(cursor, conn, quantidade=20)
        mapa_doencas = inserir_paciente_doenca(cursor, conn, pacientes_ids, doencas_ids)
        prontuarios = inserir_prontuarios(cursor, conn, pacientes_ids)
        inserir_evolucoes_clinicas(cursor, conn, prontuarios, mapa_doencas)

        conn.commit()
        print("\n✓ Dados clínicos simulados inseridos com sucesso!")

    except Exception as e:
        conn.rollback()
        print(f"\nErro durante a inserção de dados: {e}")
    finally:
        cursor.close()
        conn.close()


if __name__ == '__main__':
    main()
