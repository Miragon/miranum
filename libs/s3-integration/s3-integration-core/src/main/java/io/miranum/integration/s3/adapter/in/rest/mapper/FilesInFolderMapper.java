package io.miranum.integration.s3.adapter.in.rest.mapper;

import io.miranum.integration.s3.adapter.in.rest.dto.FilesInFolderDto;
import io.miranum.integration.s3.model.FilesInFolder;
import io.miranum.integration.s3.infrastructure.mapper.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface FilesInFolderMapper {

    FilesInFolderDto model2Dto(final FilesInFolder filesInFolder);

}
