package br.com.higia.infrastructure.doencacronica.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DoencaCronicaJpaRepository extends JpaRepository<DoencaCronicaJpaEntity, UUID> {
    Page<DoencaCronicaJpaEntity> findAll(Specification<DoencaCronicaJpaEntity> whereClause, Pageable page);

}
