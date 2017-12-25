package fr.anw.stompUserExample.server.controllers;

import fr.anw.stompUserExample.server.config.WsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.lang.invoke.MethodHandles;
import java.security.Principal;

@Controller
public class RegisterController {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  public static final String ENDPOINT_REGISTER = "/register";

  private SimpMessagingTemplate messagingTemplate;
  
  @Autowired
  public RegisterController(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  @MessageMapping(ENDPOINT_REGISTER)
  public void register(Message<Object> message, @Payload String payload, Principal principal) throws Exception {
    String username = principal.getName();
    log.info("new registration: username="+username+", payload="+payload);

    messagingTemplate.convertAndSendToUser(username, WsConfig.SUBSCRIBE_USER_REPLY, "Thanks for your registration!");
    messagingTemplate.convertAndSend(WsConfig.SUBSCRIBE_QUEUE, "Someone just registered saying: "+payload);
  }

}