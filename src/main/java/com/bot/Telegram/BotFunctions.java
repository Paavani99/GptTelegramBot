package com.bot.telegram;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.bot.gpt.ChatCompletion;



@Component
public class BotFunctions {//extends Scheduler {
	
	public HashMap<String, Boolean> dictIsNote = new HashMap<>();
	
	RestTemplate restTemplate;
	
	@Autowired
	ChatCompletion chatCompletion;
	
	public BotFunctions(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate= restTemplateBuilder.build();
	}
		
	public TelegramChats getUnreadChats(boolean firstCall) {
		String uriString =  "https://api.telegram.org/bot"+BotToken.token+"/getUpdates";
		if(!firstCall) uriString += "?offset="+LatestMessageUpdateID.getOffset();
		URI uri = URI.create(uriString);
		
		TelegramChats chats = restTemplate.getForObject(uri, TelegramChats.class);
		return chats;
	}
	
	public boolean sendReply(String chat_id, String text, long update_id) {
		String message = null;
		if(text ==null) message = "Cannot deliver the response due to incorrect input type";
		else if(text.charAt(0)=='/') return runCommand(chat_id, text, update_id);
		
		if(!dictIsNote.containsKey(chat_id)) dictIsNote.put(chat_id, false);
		if(dictIsNote.get(chat_id)) { 
			LatestMessageUpdateID.setOffset(update_id + 1);
			return false;
		}
		
		if(text!=null) {
			message = chatCompletion.generateChatResponse(text);
			if(message == null) message = "Cannot deliver the response. Please try again after some time.";
		}
		
		return sendMessage(chat_id, message, update_id);
	}
	
	public boolean runCommand(String chat_id, String text, long update_id) {
		String message;
		switch(text) {
			case "/note":{
				message= "You can use this space to keep any notes";
				dictIsNote.put(chat_id, true);
				break;
			}
			case "/askgpt":{
				message = "Ask me anything";
				dictIsNote.put(chat_id, false);
				break;
			}
			default: 
				message = "Command not found";
		}
		return sendMessage(chat_id, message, update_id);
	}
	
	public boolean sendMessage(String chat_id, String text, long update_id) {
		String uriString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s&parse_mode=HTML";
	    try {
			String encodedText = URLEncoder.encode(text, "UTF-8");
			uriString = String.format(uriString, BotToken.token, chat_id, encodedText);
		    URI uri = URI.create(uriString);
	        ErrorResponse errorResponse = restTemplate.postForObject(uri, null, ErrorResponse.class);
	        if (errorResponse.ok) {
	            LatestMessageUpdateID.setOffset(update_id + 1);
	            return true;
	        }
	    }
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	   
	    return false;
	}

}
