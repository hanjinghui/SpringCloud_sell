package com.java.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author jiangli
 * @date 2019/8/25 10:17
 */
public interface StreamClient {
	String INPUT = "input";
	String OUTPUT = "output";
	String _INPUT = "_input";
	String _OUTPUT = "_output";

	@Input(StreamClient.INPUT)
	SubscribableChannel input();

	@Output(StreamClient.OUTPUT)
	MessageChannel output();

	@Output(StreamClient._OUTPUT)
	MessageChannel _output();
}
