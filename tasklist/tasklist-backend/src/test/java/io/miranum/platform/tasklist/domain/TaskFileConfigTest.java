package io.miranum.platform.tasklist.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskFileConfigTest {

    private List<String> paths;
    private List<String> readyOnlyPaths;
    private TaskFileConfig config;

    @BeforeEach
    void setupLists() {
        paths = List.of("a", "b", "c");
        readyOnlyPaths = List.of("c", "d", "e");
        config = new TaskFileConfig("file context", "async config", "sync config", paths, readyOnlyPaths);
    }

    @Test
    void checkReadAccessInReadOnlyList() {
        config.checkReadAccess("d");
    }

    @Test
    void checkReadAccessInPathsList() {
        config.checkReadAccess("a");
    }

    @Test
    void checkReadAccessInBothLists() {
        config.checkReadAccess("c");
    }

    @Test
    void checkReadAccessThrowsException() {
        Exception exception = assertThrows(IllegalResourceAccessException.class, () -> {
            config.checkReadAccess("f");
        });

        String expectedMessage = "No access to defined property";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void checkWriteAccessInPathsList() {
        config.checkWriteAccess("a");
    }

    @Test
    void checkWriteAccessInReadOnlyListThrowsException() {
        Exception exception = assertThrows(IllegalResourceAccessException.class, () -> {
            config.checkWriteAccess("d");
        });

        String expectedMessage = "No access to defined property";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void checkWriteAccessThrowsException() {
        Exception exception = assertThrows(IllegalResourceAccessException.class, () -> {
            config.checkWriteAccess("f");
        });

        String expectedMessage = "No access to defined property";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

    }
}