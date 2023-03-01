package io.miragon.miranum.examples.waiter;

import io.miragon.miranum.examples.waiter.adapter.in.miranum.MiranumFitConnectAdapterAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MiranumFitConnectAdapterAutoConfiguration.class)
public class FitConnectExampleAutoConfiguration {
}