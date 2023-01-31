package io.miragon.miranum.integrations.example.api.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload to search for users.
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserTO {

    /**
     * Search string.
     */
    private String searchString;

    /**
     * Groups to restrict the search result.
     */
    private String groups;

}
