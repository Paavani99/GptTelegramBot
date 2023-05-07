package com.bot.gpt;

import java.net.URI;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//@RestController
//@RequestMapping("home")
@Component
public class ChatCompletion {
	
	RestTemplate restTemplate;
	
	
	
	public ChatCompletion(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate= restTemplateBuilder.build();
	}
	
	
	public String generateChatResponse(String content){
		
		HttpHeaders reqHeader = new HttpHeaders();
		reqHeader.setBearerAuth(Auth.getBearertoken());
		reqHeader.setContentType(MediaType.APPLICATION_JSON);
		
		URI uri = URI.create("https://api.openai.com/v1/chat/completions");
		
		
		ArrayList<Message> messages = new ArrayList<Message>();
		
		messages.add(new Message("user", content));
		
		GptRequest request = new GptRequest(messages);
		
		HttpEntity<GptRequest> httpEntity = new HttpEntity<>(request,reqHeader);
		
		try {
			ChatResponse response = restTemplate.postForObject(uri, httpEntity, ChatResponse.class );
			String reply = response.choices.get(0).message.content;
			
			return reply;
			
			//if(reply==null)
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		return null;	
	}
	



	
	
}
