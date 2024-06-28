package io.miragon.miranum.inquiry.adapter.in.rest;

import io.miragon.miranum.inquiry.adapter.in.rest.model.NewInquiryDto;
import io.miragon.miranum.inquiry.adapter.in.rest.model.NewInquiryResponseDto;
import io.miragon.miranum.inquiry.application.port.in.InquiryReceived;
import io.miragon.miranum.inquiry.application.port.in.model.NewInquiryCommand;
import io.miragon.miranum.inquiry.domain.InquiryId;
import io.miragon.miranum.platform.security.authentication.UserAuthenticationProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/inquiry")
@Tag(name = "InquiryController")
public class InquiryCreateController {

    private final InquiryReceived inquiryReceived;
    private final InquiryRestMapper mapper;

    @PostMapping(value ="create")
    @Operation(summary = "Receive customers inquiry", description = "Handles consulting inquiries")
    public ResponseEntity<NewInquiryResponseDto> handleInquiry(@RequestBody final NewInquiryDto dto) {

        NewInquiryCommand command = this.mapper.toCommand(dto);
        InquiryId inquiryId = this.inquiryReceived.handle(command);

        return ResponseEntity.ok(
                NewInquiryResponseDto.builder()
                        .inquiryId(inquiryId.toString())
                        .build()
        );
    }

}
