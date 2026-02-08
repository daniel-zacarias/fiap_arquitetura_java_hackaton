package br.com.higia.infrastructure.paciente.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PacienteJpaRepository extends JpaRepository<PacienteJpaEntity, UUID> {

    Page<PacienteJpaEntity> findAll(Specification<PacienteJpaEntity> whereClause, Pageable page);

}
