package fastcampus.websocketchat.config;

import fastcampus.websocketchat.handlers.WebSocketChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@EnableWebSocket
@Configuration
@RequiredArgsConstructor

public class WebSocketConfiguration implements WebSocketConfigurer {

    private final WebSocketChatHandler webSocketChatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(webSocketChatHandler,"/ws/chats");
    }
}
