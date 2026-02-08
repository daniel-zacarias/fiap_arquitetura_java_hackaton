package br.com.higia.infrastructure.pacientedoenca.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PacienteDoencaJpaRepository extends JpaRepository<PacienteDoencaJpaEntity, UUID> {

    Page<PacienteDoencaJpaEntity> findAll(Specification<PacienteDoencaJpaEntity> whereClause, Pageable page);
}
