package com.wenable.hemanth.controllers;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wenable.hemanth.configurations.RabbitMqConfig;
import com.wenable.hemanth.model.UserMessage;
import com.wenable.hemanth.service.UserMessageService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/message")
public class UserMessageController {


	@Autowired
	RabbitTemplate template;

	@Autowired
	UserMessageService service;

	@PostMapping("/publish")
	public String publishMessage(@RequestBody UserMessage bean) {
		template.convertAndSend(RabbitMqConfig.EXCHANGE, RabbitMqConfig.QUEUE, bean);
		service.saveMessage(bean);

		return "User Message Published!!!";
	}

	@DeleteMapping("/user/{email}")
	public void deleteUserMessage(@PathVariable String email) {
		service.deleteByEmail(email);
	}

	@GetMapping("/user/msg/{email}")
	public ResponseEntity<String> getUserMessage(@PathVariable String email) {
		UserMessage user = service.findByEmail(email);
		return ResponseEntity.ok(user.getMessage());
	}

	@GetMapping("/user/bulk/{email}")
	public ResponseEntity<List<UserMessage>> getUserBulkMessage(@PathVariable String email) {
		List<UserMessage> user = service.findListOfMessages(email);
		return ResponseEntity.ok(user);
	}
}
