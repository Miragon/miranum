package io.miranum.platform.engine.adapter.in.web.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Detail transport object of a filter.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterDto {

    /**
     * Id of the filter.
     */
    private String id;

    /**
     * String of the filter.
     */
    private String filterString;

    /**
     * Id of the page.
     */
    private String pageId;

}
