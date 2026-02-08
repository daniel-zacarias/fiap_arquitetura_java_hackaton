package br.com.higia.infrastructure.evolucaoclinica.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EvolucaoClinicaJpaRepository extends JpaRepository<EvolucaoClinicaJpaEntity, UUID> {

    Page<EvolucaoClinicaJpaEntity> findAll(Specification<EvolucaoClinicaJpaEntity> whereClause, Pageable page);
}
