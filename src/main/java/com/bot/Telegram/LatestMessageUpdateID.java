package com.bot.telegram;

import org.springframework.stereotype.Component;

//import javax.swing.Spring;

@Component
public class LatestMessageUpdateID {
	public static long offset;

	public static long getOffset() {
		return offset;
	}

	public static void setOffset(long offset) {
		LatestMessageUpdateID.offset = offset;
	}	
}
