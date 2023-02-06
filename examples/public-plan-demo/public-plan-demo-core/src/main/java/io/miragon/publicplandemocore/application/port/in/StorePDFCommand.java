package io.miragon.publicplandemocore.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StorePDFCommand {

    private byte[] bytes;
}
