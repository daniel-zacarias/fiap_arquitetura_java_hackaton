package br.com.higia.infrastructure.prontuario.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProntuarioJpaRepository extends JpaRepository<ProntuarioJpaEntity, UUID> {

    Page<ProntuarioJpaEntity> findAll(Specification<ProntuarioJpaEntity> whereClause, Pageable page);

    Optional<ProntuarioJpaEntity> findByPacienteIdAndAtivo(UUID pacienteId, boolean ativo);
}
