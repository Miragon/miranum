package io.miragon.miranum.inquiry.adapter.in.rest;

import io.miragon.miranum.inquiry.adapter.in.rest.model.MailDto;
import io.miragon.miranum.inquiry.application.port.in.model.NewCustomerMailCommand;
import io.miragon.miranum.inquiry.application.port.in.CustomerMailReceived;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/mail")
@RequiredArgsConstructor
@Tag(name = "MailController", description = "API to receive user mails")
public class MailController {

    private final CustomerMailReceived mailReceived;
    private final InquiryRestMapper mapper;

    @PostMapping("/receive")
    public void receiveMail(
           @RequestBody MailDto dto
    ) {
        NewCustomerMailCommand command = this.mapper.toCommand(dto);
        this.mailReceived.handle(command);
    }
}
