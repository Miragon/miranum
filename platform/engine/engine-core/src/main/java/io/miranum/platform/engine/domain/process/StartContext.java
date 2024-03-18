package io.miranum.platform.engine.domain.process;

import lombok.Getter;

import java.util.UUID;

/**
 * Entity representation of a Start Context.
 *
 * @author externer.dl.horn
 */
@Getter
public class StartContext {

    private final String id;
    private final String userId;
    private final String definitionKey;

    public StartContext(final String userId, final String definitionKey) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.definitionKey = definitionKey;
    }

    public StartContext(final String id, final String userId, final String definitionKey) {
        this.id = id;
        this.userId = userId;
        this.definitionKey = definitionKey;
    }

}
