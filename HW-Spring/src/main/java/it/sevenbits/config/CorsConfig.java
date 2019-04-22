package it.sevenbits.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.regex.Pattern;


/**
 * Configuratoin cors
 */
@Configuration
public class CorsConfig {

    @Value("${corsHeaders.allowOrigins:*}")
    private String allowOrigins;

    @Value("${corsHeaders.allowCredentials:true}")
    private boolean allowCredentials = true;

    @Value("${corsHeaders.allowMethods:GET,POST,OPTIONS,DELETE,PATCH}")
    private String allowMethods;

    @Value("${corsHeaders.allowHeaders:Authorization,Origin,Accept,Key,DNT,Keep-Alive,User-Agent," +
            "X-Requested-With,If-Modified-Since,Cache-Control,Content-Type}")
    private String allowHeaders;

    private Pattern delimiter = Pattern.compile("[,\\s]+");

    /**
     * @return WebMvcConfigurer
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(delimiter.split(allowOrigins))
                        .allowCredentials(allowCredentials)
                        .allowedMethods(delimiter.split(allowMethods))
                        .allowedHeaders(delimiter.split(allowHeaders));
            }
        };
    }
}
