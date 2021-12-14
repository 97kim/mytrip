package shop.kimkj.mytrip.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("shop.kimkj.mytrip")
                .pathsToMatch("/**")
                .packagesToScan("shop.kimkj.mytrip.controller")
                .build();
    }
}