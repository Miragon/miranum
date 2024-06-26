package io.miragon.miranum.inquiry.adapter.in.rest;

import io.miragon.miranum.inquiry.adapter.in.rest.model.CapacityCheckedDto;
import io.miragon.miranum.inquiry.application.port.in.CapacityChecked;
import io.miragon.miranum.inquiry.application.port.in.model.CapacityCheckedCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/inquiry")
@Tag(name = "InquiryController")
public class InquiryCapacityCheckedController {

    private final CapacityChecked capacityChecked;
    private final InquiryRestMapper mapper;

    @PostMapping("/capacity-checked")
    public void capacityChecked(
            @RequestBody CapacityCheckedDto capacityCheckedDto
    ) {
        CapacityCheckedCommand command = this.mapper.toCommand(capacityCheckedDto);
        this.capacityChecked.handle(command);
    }

}
