package io.miragon.miranum.integrations.user.adapter.out.ldap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The uids associated with a ou.
 *
 * @author externer.dl.horn
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OuInfo {

    private List<String> uids = new ArrayList<>();

}
