package com.bot.Gpt;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class Request {
	
	public String model ;//= "gpt-3.5-turbo";
	public ArrayList<Message> messages;
	
	public Request(ArrayList<Message> messages) {
		this.model = "gpt-3.5-turbo";
		this.messages = messages;
	}
}
