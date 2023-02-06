package io.miragon.publicplandemocore.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GeneratePDFCommand {

    private String firstname;
    private String lastname;
}
