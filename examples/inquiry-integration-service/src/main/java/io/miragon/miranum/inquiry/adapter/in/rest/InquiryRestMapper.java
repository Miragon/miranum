package io.miragon.miranum.inquiry.adapter.in.rest;

import io.miragon.miranum.inquiry.adapter.in.rest.model.CapacityCheckedDto;
import io.miragon.miranum.inquiry.adapter.in.rest.model.MailDto;
import io.miragon.miranum.inquiry.adapter.in.rest.model.NewInquiryDto;
import io.miragon.miranum.inquiry.adapter.in.rest.model.OfferCreatedDto;
import io.miragon.miranum.inquiry.application.port.in.model.CapacityCheckedCommand;
import io.miragon.miranum.inquiry.application.port.in.model.NewCustomerMailCommand;
import io.miragon.miranum.inquiry.application.port.in.model.NewInquiryCommand;
import io.miragon.miranum.inquiry.application.port.in.model.OfferCreatedCommand;
import org.mapstruct.Mapper;

@Mapper
public interface InquiryRestMapper {
    CapacityCheckedCommand toCommand(CapacityCheckedDto dto);
    NewCustomerMailCommand toCommand(MailDto dto);
    NewInquiryCommand toCommand(NewInquiryDto dto);
    OfferCreatedCommand toCommand(OfferCreatedDto dto);
}
