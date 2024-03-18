package io.miranum.platform.s3.adapter.out.persistence;

import io.miranum.platform.s3.application.port.out.FileRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class FilePersistenceAdapter implements FileRepository {

    private final FileJpaRepository fileJpaRepository;

    @Override
    public void deleteByPathToFile(String pathToFile) {
        fileJpaRepository.deleteByPathToFile(pathToFile);
    }

    @Override
    public Stream<File> findByPathToFileStartingWith(String pathToFolder) {
        return fileJpaRepository.findByPathToFileStartingWith(pathToFolder);
    }

    @Override
    public Optional<File> findByPathToFile(String pathToFile) {
        return fileJpaRepository.findByPathToFile(pathToFile);
    }

    @Override
    public Stream<File> findAllByEndOfLifeNotNullAndEndOfLifeBefore(LocalDate date) {
        return fileJpaRepository.findAllByEndOfLifeNotNullAndEndOfLifeBefore(date);
    }

    @Override
    public Stream<File> findAllByEndOfLifeIsNull() {
        return fileJpaRepository.findAllByEndOfLifeIsNull();
    }

    @Override
    public void deleteById(UUID id) {
        fileJpaRepository.deleteById(id);
    }

    @Override
    public void save(File file) {
        fileJpaRepository.save(file);
    }
}
