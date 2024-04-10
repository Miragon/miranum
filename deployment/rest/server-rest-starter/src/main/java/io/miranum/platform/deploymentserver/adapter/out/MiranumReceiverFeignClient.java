package io.miranum.platform.deploymentserver.adapter.out;

import feign.Headers;
import feign.RequestLine;
import io.miranum.platform.deploymentserver.domain.Deployment;
import io.miranum.platform.deploymentserver.domain.DeploymentStatus;

public interface MiranumReceiverFeignClient {

    @RequestLine("POST")
    @Headers("Content-Type: application/json")
    DeploymentStatus deploy(final Deployment deployment);

}
