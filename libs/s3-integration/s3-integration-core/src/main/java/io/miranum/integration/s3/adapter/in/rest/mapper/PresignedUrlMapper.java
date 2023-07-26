package io.miranum.integration.s3.adapter.in.rest.mapper;

import io.miranum.integration.s3.adapter.in.rest.dto.PresignedUrlDto;
import io.miranum.integration.s3.infrastructure.mapper.MapstructConfiguration;
import io.miranum.integration.s3.model.PresignedUrl;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class)
public interface PresignedUrlMapper {

    PresignedUrlDto model2Dto(final PresignedUrl fileResponse);

    List<PresignedUrlDto> models2Dtos(final List<PresignedUrl> presignedUrls);

}
