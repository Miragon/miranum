package io.miragon.miranum.connect.cloudevents;

import io.cloudevents.spring.mvc.CloudEventHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CloudEventsAutoConfiguration implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new CloudEventHttpMessageConverter());
    }

    @Bean
    public CloudEventsController cloudEventsController() {
        return new CloudEventsController();
    }

}
