package com.example.restservice;

import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.restservice.service.model.Person;

@SpringBootApplication
@ClientCacheApplication(name = "AccessingGemFireDataRestApplication")
@EnableEntityDefinedRegions(
	basePackageClasses = Person.class
	, clientRegionShortcut = ClientRegionShortcut.LOCAL
)
@EnableGemfireRepositories
@EnableAspectJAutoProxy
public class RestServiceLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceLearnApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				
				registry
					// CORS를 적용할 URL패턴, antPattern 가능
					.addMapping("/greeting-javaconfig")
					// 자원 공유를 허락할 Origin 모든 자원을 허용하려면, allowedOrigins("*")
					.allowedOrigins("http://localhost:8080")
					// 허용할 HTTP Method. *를 이용하면 모든 메서드 허용
					// .allowedMethods("GET", "POST")
					// 원하는 시간만큼 pre-flight 요청을 캐싱 가능(단위는 초)
					.maxAge(3000L)
				;
			}
		};
	}
	
}
