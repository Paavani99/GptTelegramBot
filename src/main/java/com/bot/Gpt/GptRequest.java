package com.bot.gpt;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class GptRequest {
	
	public String model ;//= "gpt-3.5-turbo";
	public ArrayList<Message> messages;
	
	public GptRequest(ArrayList<Message> messages) {
		this.model = "gpt-3.5-turbo";
		this.messages = messages;
	}
}
