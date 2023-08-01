package io.miranum.platform.tasklist.adapter.out.user.easyldap;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(
        name = "ezldap",
        url = "${ezldap.client.url}"
)
public interface EasyLdapClient {

    @RequestMapping(value = "/v1/ldap/user/{lhmObjectId}", method = {RequestMethod.GET}, produces = {"application/json"})
    UserInfoResponse getUserById(@PathVariable("lhmObjectId") String lhmObjectId);

    @RequestMapping(value = "/v1/ldap/user/outree/{lhmObjectId}", method = {RequestMethod.GET}, produces = {"application/json"})
    List<String> getOuTreeByUserId(@PathVariable("lhmObjectId") String lhmObjectId);

}
