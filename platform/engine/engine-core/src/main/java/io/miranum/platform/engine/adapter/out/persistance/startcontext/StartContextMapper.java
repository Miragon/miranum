package io.miranum.platform.engine.adapter.out.persistance.startcontext;

import io.miranum.platform.engine.domain.process.StartContext;
import org.springframework.stereotype.Component;

@Component
public class StartContextMapper {

    public StartContext map(StartContextEntity startContextEntity) {
        return new StartContext(startContextEntity.getId(), startContextEntity.getUserId(), startContextEntity.getDefinitionKey());
    }

    public StartContextEntity map(StartContext startContext) {
        return new StartContextEntity(startContext.getId(), startContext.getUserId(), startContext.getDefinitionKey());
    }

}
