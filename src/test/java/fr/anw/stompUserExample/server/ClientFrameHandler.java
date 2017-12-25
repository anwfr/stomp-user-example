package fr.anw.stompUserExample.server;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import java.lang.reflect.Type;
import java.util.function.Consumer;

public class ClientFrameHandler implements StompFrameHandler {
    private final Consumer<String> frameHandler;

    public ClientFrameHandler(Consumer<String> frameHandler) {
        this.frameHandler = frameHandler;
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return String.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        frameHandler.accept(payload.toString());
    }
}