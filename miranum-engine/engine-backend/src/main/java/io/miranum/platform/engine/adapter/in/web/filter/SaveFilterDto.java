package io.miranum.platform.engine.adapter.in.web.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Detail transport object of a filter.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveFilterDto {

    /**
     * String of the filter.
     */
    @NotNull
    private String filterString;

    /**
     * Id of the page.
     */
    @NotNull
    private String pageId;

}
