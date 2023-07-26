/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package io.muenchendigital.digiwf.s3.integration.configuration.nfcconverter;

import org.apache.commons.collections4.list.UnmodifiableList;
import org.apache.commons.collections4.map.UnmodifiableMap;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class NfcConverterTest {

    private static final String NAME_NFD = "aM\u0302ao\u0308a";

    private static final String VALUE_NFD = "b M\u0302 b o\u0308 b";

    private static final String VALUE2_NFD = "c M\u0302 c o\u0308 c";

    private static final String NAME_NFC = Normalizer.normalize(NAME_NFD, Normalizer.Form.NFC);

    private static final String VALUE_NFC = Normalizer.normalize(VALUE_NFD, Normalizer.Form.NFC);

    @SuppressWarnings("unused")
    private static final String VALUE2_NFC = Normalizer.normalize(VALUE2_NFD, Normalizer.Form.NFC);

    // Für Stellen der API an denen Strings bestimmten Regeln genügen müssen.
    public static final String TOKEN = "token";

    private static final Charset UTF8 = StandardCharsets.UTF_8;

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse resp;

    @Mock
    private FilterChain chain;

    private final NfcRequestFilter filter = new NfcRequestFilter();

    //
    // Test, das Request mit konfigriertem ContentType auf NFC normalisiert wird.
    //
    @Test
    public void testFilterIfContenttypeInWhitelist() throws ServletException, IOException {
        mockRequest("text/plain");

        filter.setContentTypes("text/plain;text/html;application/json");

        filter.doFilter(req, resp, chain);

        //
        // Check
        //
        ArgumentCaptor<HttpServletRequest> reqCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        Mockito.verify(chain, Mockito.times(1)).doFilter(reqCaptor.capture(), Mockito.any(ServletResponse.class));

        assertEquals(VALUE_NFC, reqCaptor.getValue().getParameter(NAME_NFC));
        assertEquals(VALUE_NFC, reqCaptor.getValue().getHeader(NAME_NFC));
        assertEquals(VALUE_NFC, reqCaptor.getValue().getCookies()[0].getValue());
        assertEquals(VALUE_NFC, IOUtils.toString(reqCaptor.getValue().getReader()));

        //
        // Prüfen, das Multipart-Requests nicht angefasst werden.
        //
        assertArrayEquals(VALUE_NFD.getBytes(UTF8), IOUtils.toByteArray(reqCaptor.getValue().getPart(NAME_NFD).getInputStream()));
    }

    //
    // Test, das Request nicht konfigriertem ContentType auf unverändert bleibt, d.h. nicht
    // auf NFC normalisiert wird.
    //
    @Test
    public void testSkipFilterIfContenttypeNotInWhitelist() throws ServletException, IOException {
        mockRequest("application/postscript");

        filter.setContentTypes("text/plain;text/html");

        filter.doFilter(req, resp, chain);

        //
        // Check
        //
        ArgumentCaptor<HttpServletRequest> reqCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        Mockito.verify(chain, Mockito.times(1)).doFilter(reqCaptor.capture(), Mockito.any(ServletResponse.class));

        assertEquals(VALUE_NFD, reqCaptor.getValue().getParameter(NAME_NFD));
        assertEquals(VALUE_NFD, reqCaptor.getValue().getHeader(NAME_NFD));
        assertEquals(VALUE_NFD, reqCaptor.getValue().getCookies()[0].getValue());
        assertEquals(VALUE_NFD, IOUtils.toString(reqCaptor.getValue().getReader()));

        //
        // Prüfen, das Multipart-Requests nicht angefasst werden.
        //
        assertArrayEquals(VALUE_NFD.getBytes(UTF8), IOUtils.toByteArray(reqCaptor.getValue().getPart(NAME_NFD).getInputStream()));
    }

    private void mockRequest(final String contentType) throws IOException, ServletException {
        Mockito.when(req.getContentType()).thenReturn(contentType);
        Mockito.when(req.getRequestURI()).thenReturn("/index.html?type=" + contentType);

        Map<String, String[]> baseMapParams = new HashMap<>();
        baseMapParams.put(NAME_NFD, new String[] { VALUE_NFD, VALUE2_NFD });
        final Map<String, String[]> params = UnmodifiableMap.unmodifiableMap(baseMapParams);
        Mockito.when(req.getParameter(NAME_NFD)).thenReturn(params.get(NAME_NFD)[0]);
        Mockito.when(req.getParameterMap()).thenReturn(params);
        Map<String, String> baseMapHeaders = new HashMap<>();
        baseMapHeaders.put(NAME_NFD, VALUE_NFD);
        final Map<String, String> headers = UnmodifiableMap.unmodifiableMap(baseMapHeaders);
        Mockito.when(req.getHeaderNames()).thenReturn(Collections.enumeration(headers.keySet()));
        Mockito.when(req.getHeader(NAME_NFD)).thenReturn(headers.get(NAME_NFD));
        List<String> baseListvalues = new ArrayList<>();
        baseListvalues.add(VALUE_NFD);
        final UnmodifiableList<String> values = new UnmodifiableList<>(baseListvalues);
        Mockito.when(req.getHeaders(NAME_NFD)).thenReturn(Collections.enumeration(values));
        Mockito.when(req.getCookies()).thenReturn(mockCookies());

        Mockito.when(req.getReader()).thenReturn(new BufferedReader(new StringReader(VALUE_NFD)));

        final Collection<Part> parts = mockParts();
        Mockito.when(req.getPart(NAME_NFD)).thenReturn(parts.iterator().next());
    }

    private Cookie[] mockCookies() {
        final Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie(TOKEN, VALUE_NFD);
        cookies[0].setComment(VALUE_NFD);
        cookies[0].setDomain(VALUE_NFD);
        cookies[0].setPath(VALUE_NFD);
        cookies[0].setVersion(1);
        return cookies;
    }

    private List<Part> mockParts() throws IOException {
        Part part = Mockito.mock(Part.class);
        Mockito.when(part.getInputStream()).thenReturn(new ByteArrayInputStream(VALUE_NFD.getBytes(UTF8)));
        List<Part> baseListParts = new ArrayList<>();
        baseListParts.add(part);
        return new UnmodifiableList<>(baseListParts);
    }

}
