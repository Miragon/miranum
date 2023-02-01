package io.miragon.miranum.integrations.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "miranum.mail")
public class MailProperties {

    /**
     * Type of output.
     * Currently mail or sendgrid
     */
    public String output;

}

