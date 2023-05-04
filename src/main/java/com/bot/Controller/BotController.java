package com.bot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bot.Gpt.ChatCompletion;
import com.bot.Gpt.Content;


@RestController
//@RequestMapping("/home")
public class BotController {
	
	@Autowired
	ChatCompletion chatCompletion;
	
	@PostMapping(value = "/chatgpt", produces = "application/json")
	public String showResponse(@RequestBody Content req )
	{
		
		return this.chatCompletion.generateChatResponse(req.content);
	}
}
