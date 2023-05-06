package com.bot.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bot.gpt.ChatCompletion;

@Component
public class Scheduler {
	@Autowired
	BotFunctions botFunctions;
	
	@Autowired
	LatestMessageUpdateID upID;
	
	@Autowired
	ChatCompletion chatCompletion;
	
	boolean firstCall = true;
	
	@Scheduled(fixedRate= 10000)
	public void GetChatForBot() {	
		
		TelegramChats latestChats = botFunctions.getUnreadChats(firstCall);
		
		firstCall = false;
		
		for(int i=0; i< latestChats.result.size(); i++) {
			if(latestChats.result.isEmpty()) continue;
			
			Result result = latestChats.result.get(i);
			
			String chat_id = result.message.chat.id;
			
			String text = result.message.text;
			
			//System.out.println(text);
			String response = chatCompletion.generateChatResponse(text);
			//String response= "Yo! What's uppp";
			if(response==null)botFunctions.sendMessage(chat_id, "Cannot deliver the response due to some issue" , result.update_id);
			//System.out.println(response);
			//LatestMessageUpdateID.setOffset(result.update_id+1);
			boolean send = botFunctions.sendMessage(chat_id, response , result.update_id);
			//if(!send) break;
			
		}
				
	}
}
