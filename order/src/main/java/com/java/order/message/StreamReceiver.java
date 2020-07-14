package com.java.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @author jiangli
 * @date 2019/8/25 10:20
 */
@Component
@EnableBinding(StreamClient.class)  //绑定stream接口
@Slf4j
public class StreamReceiver {

	@StreamListener(StreamClient.OUTPUT)
	@SendTo(StreamClient._OUTPUT) //向这个stream发送消息确认收到
	public String receive(Object message) {
		log.info("stream receive message : {}",message);
		return "确认收到消息";
	}

	@StreamListener(StreamClient._OUTPUT)
	public void _receive(String message) {
		log.info("stream receive message : {}",message);
	}


}
