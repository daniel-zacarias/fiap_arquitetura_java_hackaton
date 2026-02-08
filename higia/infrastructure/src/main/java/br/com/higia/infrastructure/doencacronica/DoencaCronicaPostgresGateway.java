package br.com.higia.infrastructure.doencacronica;

import br.com.higia.domain.doencacronica.DoencaCronica;
import br.com.higia.domain.doencacronica.DoencaCronicaGateway;
import br.com.higia.domain.doencacronica.DoencaCronicaID;
import br.com.higia.infrastructure.doencacronica.persistence.DoencaCronicaJpaEntity;
import br.com.higia.infrastructure.doencacronica.persistence.DoencaCronicaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static br.com.higia.domain.utils.IdUtils.uuid;

@Component
public class DoencaCronicaPostgresGateway implements DoencaCronicaGateway {

    private final DoencaCronicaJpaRepository doencaCronicaRepository;

    public DoencaCronicaPostgresGateway(final DoencaCronicaJpaRepository doencaCronicaRepository) {
        this.doencaCronicaRepository = Objects.requireNonNull(doencaCronicaRepository);
    }

    @Override
    public Optional<DoencaCronica> findById(final DoencaCronicaID id) {
        return doencaCronicaRepository.findById(uuid(id.getValue()))
                .map(DoencaCronicaJpaEntity::toAggregate);
    }
}
