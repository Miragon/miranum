package io.miragon.miranum.platform.adapter.in.rest;

import io.miragon.miranum.platform.adapter.in.rest.model.CompleteTaskDto;
import io.miragon.miranum.platform.adapter.in.rest.model.ReceiveMailDto;
import io.miragon.miranum.platform.application.port.in.CustomerMailCommand;
import io.miragon.miranum.platform.application.port.in.MailReceived;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/mail")
@RequiredArgsConstructor
@Tag(name = "MailController", description = "API to receive user mails")
public class ReceiveMailController {

    private final MailReceived mailReceived;

    @PostMapping("/receive")
    public void receiveMail(
           @Valid @RequestBody ReceiveMailDto dto
    ) {
        this.mailReceived.handle(new CustomerMailCommand(dto.isAccepted()));
    }
}
