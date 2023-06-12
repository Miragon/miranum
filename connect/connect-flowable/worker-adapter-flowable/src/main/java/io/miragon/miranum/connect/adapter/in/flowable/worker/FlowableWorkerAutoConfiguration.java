package io.miragon.miranum.connect.adapter.in.flowable.worker;

import io.miragon.miranum.connect.worker.api.WorkerExecuteApi;
import io.miragon.miranum.connect.worker.api.WorkerSubscription;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowableWorkerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public WorkerSubscription flowableWorkerAdapter(final JavaDelegateRegistrator javaDelegateRegistrator, final JavaDelegateFactory javaDelegateFactory) {
        return new FlowableWorkerAdapter(javaDelegateRegistrator, javaDelegateFactory);
    }

    @Bean
    public JavaDelegateFactory javaDelegateFactory(final WorkerExecuteApi workerExecuteApi) {
        return new JavaDelegateFactory(workerExecuteApi);
    }

    @Bean
    public JavaDelegateRegistrator javaDelegateRegistrator(final ConfigurableListableBeanFactory registry) {
        return new JavaDelegateRegistrator(registry);
    }
}