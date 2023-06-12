package io.miragon.miranum.integrations.example.application.port.out;

import java.util.List;

public interface DataQuery {

    String getDataString();
    List<String> getDataStrings();
}