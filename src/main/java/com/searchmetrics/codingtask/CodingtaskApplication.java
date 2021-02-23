package com.searchmetrics.codingtask;

import com.searchmetrics.codingtask.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
@EnableScheduling
public class CodingtaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingtaskApplication.class, args);
	}

}
