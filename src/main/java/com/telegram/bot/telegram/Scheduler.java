package com.telegram.bot.telegram;

import org.springframework.scheduling.annotation.Scheduled;

public class Scheduler {
	@Scheduled(fixedRate= 10000)
	public void GetChatForBot() {
		
	}
}
