package io.miragon.miranum.connect.cloudevents;

import io.cloudevents.spring.mvc.CloudEventHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class CloudEventsAutoConfiguration implements WebMvcConfigurer {
    @Autowired
    private List<CloudEventHandler> ceHandlers;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new CloudEventHttpMessageConverter());
    }

    @Bean
    public CloudEventsController cloudEventsController() {
        // build up a lookup hash map for all registered CloudEventHandler Beans
        Map<String, CloudEventHandler> ceHandlerMap = new HashMap<>();
        ceHandlers.forEach(ceHandler -> ceHandlerMap.put(ceHandler.getType(), ceHandler));
        return new CloudEventsController(ceHandlerMap);
    }

}
