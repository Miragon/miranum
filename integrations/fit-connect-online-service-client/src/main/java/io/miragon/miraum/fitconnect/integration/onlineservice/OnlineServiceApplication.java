package io.miragon.miraum.fitconnect.integration.onlineservice;

import io.miragon.miraum.fitconnect.integration.gen.api.EinreichungsbermittlungApi;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.UUID;

@SpringBootApplication
@Log
public class OnlineServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OnlineServiceApplication.class, args);

        var api = context.getBean(EinreichungsbermittlungApi.class);
        var info = api.getDestinationInfo(UUID.fromString("e61f0d18-5bbd-46da-9dba-6abfe97604de")).block();
        log.info(info.toString());
//        var submission = new CreateSubmission();
//        submission.destinationId(UUID.fromString("e61f0d18-5bbd-46da-9dba-6abfe97604de"));
//        var response = api.createSubmissionWithResponseSpec(submission);
//        System.out.println(response);
    }
}