package io.miranum.platform.engine.adapter.in.web.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserDto {

    private String searchString;

}
