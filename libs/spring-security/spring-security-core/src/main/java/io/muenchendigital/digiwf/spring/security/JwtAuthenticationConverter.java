package io.muenchendigital.digiwf.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Stateful configuration of a Jwt Converter loading details from UserInfo endpoint.
 */
@RequiredArgsConstructor
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String CLAIM_ROLES = "roles";

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (source.getClaims().containsKey((CLAIM_ROLES))) {
            authorities.addAll(asAuthorities(source.getClaims().get(CLAIM_ROLES)));
        }

        return new JwtAuthenticationToken(source, authorities);
    }

    private List<SimpleGrantedAuthority> asAuthorities(Object object) {
        final List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (object instanceof Collection<?> collection) {
            object = collection.toArray(new Object[0]);
        }
        if (ObjectUtils.isArray(object)) {
            authorities.addAll(
                    Stream.of(((Object[]) object))
                            .map(Object::toString)
                            .map(SimpleGrantedAuthority::new)
                            .toList()
            );
        }
        return authorities;
    }

}

