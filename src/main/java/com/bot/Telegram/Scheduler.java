package com.bot.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bot.gpt.ChatCompletion;

@Component
public class Scheduler {
	
	//public boolean isNote= true;
	
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
		
		if(latestChats.ok)
			for(int i=0; i< latestChats.result.size(); i++) {
				if(latestChats.result.isEmpty()) continue;
				
				Result result = latestChats.result.get(i);
				
				String chat_id = result.message.chat.id;
				
				String text = result.message.text;
				
				//String response=null;
				
				//if(text!=null) response = chatCompletion.generateChatResponse(text);
				//botFunctions.sendMessage(chat_id, "Cannot deliver the response due to incorrect input type" , result.update_id);
				
				//if(response==null) response= "Cannot deliver the response. Please try again after some time.";
				
				botFunctions.sendReply(chat_id, text , result.update_id);
			}
	}
}
