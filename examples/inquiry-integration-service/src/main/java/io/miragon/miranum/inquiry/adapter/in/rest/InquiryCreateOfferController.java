package io.miragon.miranum.inquiry.adapter.in.rest;

import io.miragon.miranum.inquiry.adapter.in.rest.model.OfferCreatedDto;
import io.miragon.miranum.inquiry.application.port.in.CapacityChecked;
import io.miragon.miranum.inquiry.application.port.in.OfferCreated;
import io.miragon.miranum.inquiry.application.port.in.model.CapacityCheckedCommand;
import io.miragon.miranum.inquiry.application.port.in.model.OfferCreatedCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/inquiry")
@Tag(name = "InquiryController")
public class InquiryCreateOfferController {

    private final OfferCreated offerCreated;
    private final InquiryRestMapper mapper;

    @PostMapping("/create-offer")
    public void createOffer(
            @RequestBody OfferCreatedDto dto
    ) {
        OfferCreatedCommand command = this.mapper.toCommand(dto);
        this.offerCreated.handle(command);
    }

}
