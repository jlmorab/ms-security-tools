package com.jlmorab.ms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration to allow access from any source
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
	
	@Value("${spring.cross.origins:*}")
	String allowedOrigins;
	
	@Override
	public void addCorsMappings( CorsRegistry registry ) {
		registry.addMapping("/**")
		.allowedOrigins(allowedOrigins)
		.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD")
		.allowedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Headers"
						, "Access-Control-Allow-Methods", "Accept"
						, "Authorization", "Content-Type", "Method"
						, "Origin", "X-Forwarded-For", "X-Real-IP");
	}//end addCorsMappings()

}
