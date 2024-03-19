/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.miranum.platform.engine.domain.file;

import lombok.AllArgsConstructor;

/**
 * Exception is thrown when an object is not found.
 *
 * @author externer.dl.horn
 */
@AllArgsConstructor
public class IllegalResourceAccessException extends RuntimeException {

    public IllegalResourceAccessException(final String message) {
        super(message);
    }

}
