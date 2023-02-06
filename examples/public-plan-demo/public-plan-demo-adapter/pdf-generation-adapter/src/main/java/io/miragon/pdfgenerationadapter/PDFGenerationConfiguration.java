package io.miragon.pdfgenerationadapter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PDFGenerationConfiguration {

    @Bean
    public PDFGenerationAdapter pdfGenerationAdapter() {
        return new PDFGenerationAdapter();
    }
}
