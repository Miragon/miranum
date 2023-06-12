package io.miragon.miranum.integrations.example.adapter.out;

import io.miragon.miranum.integrations.example.application.port.out.DataQuery;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DataBaseAdapter implements DataQuery {

    private final DataRepository dataRepository;

    @Override
    public String getDataString() {
        return dataRepository.findAll().stream().map(DataContainer::getName).collect(Collectors.joining(", "));
    }

    @Override
    public List<String> getDataStrings() {
        return dataRepository.findAll().stream().map(DataContainer::getName).collect(Collectors.toList());
    }
}
