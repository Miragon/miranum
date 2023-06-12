package io.miragon.miranum.integrations.example.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<DataContainer, String> {
}