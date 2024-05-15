package io.miragon.miranum.platform.deployment.server.adapter.out;

import feign.Headers;
import feign.RequestLine;
import io.miragon.miranum.platform.deployment.server.domain.Deployment;
import io.miragon.miranum.platform.deployment.server.domain.DeploymentStatus;

public interface MiranumReceiverFeignClient {

    @RequestLine("POST")
    @Headers("Content-Type: application/json")
    DeploymentStatus deploy(final Deployment deployment);

}
