package com.java.order.controller;

import com.java.order.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author jiangli
 * @date 2019/8/25 10:27
 */
@RestController
public class StreamController {
	@Autowired
	private StreamClient streamClient;

	@GetMapping("/sendMsg")
	public void sendMsg() {
		streamClient.output().send(MessageBuilder.withPayload("now "+new Date()).build());
	}
}
