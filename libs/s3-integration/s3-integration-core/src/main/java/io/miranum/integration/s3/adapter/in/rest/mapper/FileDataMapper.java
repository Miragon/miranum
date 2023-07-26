package io.miranum.integration.s3.adapter.in.rest.mapper;

import io.miranum.integration.s3.adapter.in.rest.dto.FileDataDto;
import io.miranum.integration.s3.infrastructure.mapper.MapstructConfiguration;
import io.miranum.integration.s3.model.FileData;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface FileDataMapper {

    FileData dto2Model(final FileDataDto fileDataDto);

}
