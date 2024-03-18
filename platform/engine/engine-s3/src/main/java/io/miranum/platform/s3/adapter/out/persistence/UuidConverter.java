package io.miranum.platform.s3.adapter.out.persistence;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

import static java.util.Optional.ofNullable;

/**
 * Converter to be used instead of old <code>@Type(type = "uuid-char")</code> deprecated in Hibernate 6.
 */
@Converter
public class UuidConverter implements AttributeConverter<UUID, String> {

    @Override
    public String convertToDatabaseColumn(final UUID entityValue) {
        return ofNullable(entityValue)
                .map(UUID::toString)
                .orElse(null);
    }

    @Override
    public UUID convertToEntityAttribute(final String databaseValue) {
        return ofNullable(databaseValue)
                .map(UUID::fromString)
                .orElse(null);
    }
}
