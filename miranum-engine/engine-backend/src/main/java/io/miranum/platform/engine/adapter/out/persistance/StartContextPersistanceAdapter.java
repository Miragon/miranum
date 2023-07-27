package io.miranum.platform.engine.adapter.out.persistance;

import io.miranum.platform.engine.application.port.out.process.StartContextPort;
import io.miranum.platform.engine.domain.process.StartContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StartContextPersistanceAdapter implements StartContextPort {

    private final StartContextRepository startContextRepository;
    private final StartContextMapper startContextMapper;

    @Override
    public Optional<StartContext> searchStartContext(String userId, String definitionKey) {
        return startContextRepository.findByUserIdAndDefinitionKey(userId, definitionKey)
                .map(startContextMapper::map);
    }

    @Override
    public void save(StartContext startContext) {
        startContextRepository.save(startContextMapper.map(startContext));
    }

    @Override
    public void delete(String id) {
        startContextRepository.deleteById(id);
    }

}
