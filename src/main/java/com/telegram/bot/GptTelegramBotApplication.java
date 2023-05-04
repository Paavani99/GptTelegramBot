package com.telegram.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class GptTelegramBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(GptTelegramBotApplication.class, args);
		
	}
	
}



