package io.miragon.miranum.connect.adapter.in.c7.oauth;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.camunda.bpm.client.interceptor.ClientRequestInterceptor;
import org.camunda.community.rest.client.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

public class Camunda7oAuthAutoConfiguration {

    private final String bearer = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJwbS10NlhjYXM1ZU41NXNfNmZmTzhKY3hEMXVrSVY0d1VRTmFQZjY4YkU4In0.eyJleHAiOjE2NzU3MDM0MDksImlhdCI6MTY3NTcwMzEwOSwianRpIjoiMzI0YzZiY2ItNzQ0Yi00NGMwLTk1NjgtMmFlYWY4ZGQ1ZDcyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL2Zvcm1zLWZsb3ctYWkiLCJhdWQiOlsiZm9ybXMtZmxvdy13ZWIiLCJjYW11bmRhLXJlc3QtYXBpIiwiYWNjb3VudCJdLCJzdWIiOiI2OGNhNmI0OS0yOTg3LTRiNjgtYWJiNy0xNzM1ZDk5NGYxMTgiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJmb3Jtcy1mbG93LXdlYiIsInNlc3Npb25fc3RhdGUiOiJkNGNkNDg5MS1iMmY5LTRhOTItOGE4OS05NDJiZjc0NzE3ZGYiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbIioiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJmb3Jtcy1mbG93LXdlYiI6eyJyb2xlcyI6WyJmb3Jtc2Zsb3ctZGVzaWduZXIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIHByb2ZpbGUgZW1haWwgY2FtdW5kYS1yZXN0LWFwaSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicm9sZSI6WyJmb3Jtc2Zsb3ctZGVzaWduZXIiXSwibmFtZSI6IkRlc2lnbmVyIEZGQSIsImdyb3VwcyI6WyIvY2FtdW5kYS1hZG1pbiIsIi9mb3Jtc2Zsb3cvZm9ybXNmbG93LWRlc2lnbmVyIl0sInByZWZlcnJlZF91c2VybmFtZSI6ImZvcm1zZmxvdy1kZXNpZ25lciIsImdpdmVuX25hbWUiOiJEZXNpZ25lciIsImZhbWlseV9uYW1lIjoiRkZBIiwiZW1haWwiOiJmb3Jtc2Zsb3ctZGVzaWduZXJAZXhhbXBsZS5jb20ifQ.j7BzqWZ4Pj8umpYri1vNTeLPPBV6pKJu8zf8_L9M1rqOi-UombIKW3g-6fSk48gJLX599_I8lvuQqp-M65-3zDnQaD_G6WA2EiVURGUFbLxOg9i1nuOIWOwAWgqTWqbdV2AGUMJQpvIZIu-BORo0nwU6LuiwL7KsfFVdav7CbmLkjLm9lUsehsfaHOs7xXbhlSSgFVNexUMS4M9IvMshmFzG7nvGMNV8qZFb3VMKNW9jckZsbEAtC3__7h4OUGue8eswpw8qkK1iMcwYnDLb2Dhsu7EcL9LXAIz-WhHArVNlsaq3UWF3tKRyP8W9D84hnFRhmRnYbzVxe0iIJ9YLTw";

    @Bean
    public ClientRequestInterceptor interceptor() {
        return context -> {
            context.addHeader("Authorization", this.bearer);
        };
    }

    @Autowired
    public void addOAuthInterceptor(final ApiClient apiClient) {
        apiClient.setHttpClient(apiClient.getHttpClient().newBuilder().addInterceptor(this::intercept).build());
    }

    public Response intercept(final Interceptor.Chain chain) throws IOException {
        final Request originalRequest = chain.request();
        final Request requestWithToken = originalRequest.newBuilder()
                .header("Authorization", this.bearer)
                .build();
        return chain.proceed(requestWithToken);
    }

}
