/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package io.muenchendigital.digiwf.s3.integration.configuration;

import io.muenchendigital.digiwf.s3.integration.configuration.nfcconverter.NfcRequestFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;


/**
 * <p><em>Beispiel</em> für Konfiguration des NFC Request-Filters</p>
 * <ul>
 *     <li>Es werden alle Requests gefiltert, die an URIs unter <em>/*</em> geschickt werden.</li>
 *     <li>Filter ist in Bean <em>nfcRequestFilter</em> enthalten.</li>
 *     <li>Es werden nur Requests mit den Content-Types <em>text/plain</em>; <em>application/json</em> und <em>text/html</em> gefiltert.</li>
 * </ul>
 */
@Configuration
public class UnicodeConfiguration {

    private static final String NFC_FILTER_NAME = "nfcRequestFilter";

    private static final String NFC_WHITE_LIST = "text/plain; application/json; application/hal+json; text/html";

    private static final String[] NFC_URLS = ArrayUtils.toArray("/*");

    @Bean
    public FilterRegistrationBean<NfcRequestFilter> nfcRequestFilterRegistration(final NfcRequestFilter nfcRequestFilter) {

        final FilterRegistrationBean<NfcRequestFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(nfcRequestFilter);
        registration.setName(NFC_FILTER_NAME);
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        registration.setAsyncSupported(false);

        //
        // Setzen der URLs, auf die Filter anzuwenden ist.
        //
        registration.addUrlPatterns(NFC_URLS);

        //
        // Setzen der White-List von ContentTypes für
        //
        registration.addInitParameter(NfcRequestFilter.CONTENTTYPES_PROPERTY, NFC_WHITE_LIST);

        return registration;

    }

}
