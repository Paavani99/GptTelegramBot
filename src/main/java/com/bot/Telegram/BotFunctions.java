package com.bot.telegram;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.http.HttpHeaders;

import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.util.UriEncoder;


@Component
public class BotFunctions {
	RestTemplate restTemplate;
	
	public BotFunctions(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate= restTemplateBuilder.build();
	}
		
	public TelegramChats getUnreadChats(boolean firstCall) {
		String uriString =  "https://api.telegram.org/bot"+BotToken.token+"/getUpdates";
		if(!firstCall) uriString += "?offset="+LatestMessageUpdateID.getOffset();
		URI uri = URI.create(uriString);
		
		try {
			TelegramChats chats = restTemplate.getForObject(uri, TelegramChats.class);
			return chats;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}
	
	public boolean sendMessage(String chat_id, String text, long update_id) {
		String uriString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s&parse_mode=HTML";
	    try {
			String encodedText = URLEncoder.encode(text, "UTF-8");
			uriString = String.format(uriString, BotToken.token, chat_id, encodedText);
		    URI uri = URI.create(uriString);
		    try {
		        ErrorResponse errorResponse = restTemplate.postForObject(uri, null, ErrorResponse.class);
		        if (errorResponse.ok) {
		            LatestMessageUpdateID.setOffset(update_id + 1);
		            return true;
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	    return false;
	}

}
