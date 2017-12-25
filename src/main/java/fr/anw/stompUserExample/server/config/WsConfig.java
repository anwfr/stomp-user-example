package fr.anw.stompUserExample.server.config;

import fr.anw.stompUserExample.server.controllers.RegisterController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.lang.invoke.MethodHandles;

/**
 * Spring websocket configuration with STOMP.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WsConfig extends AbstractWebSocketMessageBrokerConfigurer {
    private static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String ENDPOINT_CONNECT = "/connect";
    public static final String SUBSCRIBE_USER_PREFIX = "/private";
    public static final String SUBSCRIBE_USER_REPLY = "/reply";
    public static final String SUBSCRIBE_QUEUE = "/queue";

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(ENDPOINT_CONNECT, RegisterController.ENDPOINT_REGISTER)
            // assign a random username as principal for each websocket client
            // this is needed to be able to communicate with a specific client
            .setHandshakeHandler(new AssignPrincipalHandshakeHandler());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(SUBSCRIBE_QUEUE, SUBSCRIBE_USER_REPLY);
        registry.setUserDestinationPrefix(SUBSCRIBE_USER_PREFIX);
    }

    // various listeners for debugging purpose

    @EventListener
    public void handleSubscribeEvent(SessionSubscribeEvent event) {
        log.info("<==> handleSubscribeEvent: username="+event.getUser().getName()+", event="+event);
    }

    @EventListener
    public void handleConnectEvent(SessionConnectEvent event) {
        log.info("===> handleConnectEvent: username="+event.getUser().getName()+", event="+event);
    }

    @EventListener
    public void handleDisconnectEvent(SessionDisconnectEvent event) {
        log.info("<=== handleDisconnectEvent: username="+event.getUser().getName()+", event="+event);
    }
}