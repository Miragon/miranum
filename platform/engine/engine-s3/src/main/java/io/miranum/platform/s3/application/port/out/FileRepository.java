package io.miranum.platform.s3.application.port.out;

import io.miranum.platform.s3.adapter.out.persistence.File;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface FileRepository {

    void deleteByPathToFile(final String pathToFile);

    Stream<File> findByPathToFileStartingWith(final String pathToFolder);

    Optional<File> findByPathToFile(final String pathToFile);

    Stream<File> findAllByEndOfLifeNotNullAndEndOfLifeBefore(final LocalDate date);

    Stream<File> findAllByEndOfLifeIsNull();

    void deleteById(UUID id);

    void save(File file);
}
