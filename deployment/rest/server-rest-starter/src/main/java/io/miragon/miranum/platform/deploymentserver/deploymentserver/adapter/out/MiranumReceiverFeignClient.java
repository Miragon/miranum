package io.miragon.miranum.platform.deploymentserver.deploymentserver.adapter.out;

import feign.Headers;
import feign.RequestLine;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.domain.Deployment;
import io.miragon.miranum.platform.deploymentserver.deploymentserver.domain.DeploymentStatus;

public interface MiranumReceiverFeignClient {

    @RequestLine("POST")
    @Headers("Content-Type: application/json")
    DeploymentStatus deploy(final Deployment deployment);

}
