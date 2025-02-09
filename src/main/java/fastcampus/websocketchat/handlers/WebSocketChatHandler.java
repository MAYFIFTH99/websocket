package fastcampus.websocketchat.handlers;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Slf4j
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    // 접속한 사용자 정보를 담은 Map
    final Map<String, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} connected", session.getId());
        webSocketSessionMap.put(session.getId(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
            throws Exception {
        log.info("{} sent message: {}", session.getId(), message.getPayload());
        webSocketSessionMap.values().forEach((
                socketSession -> {
                    try {
                        socketSession.sendMessage(message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {
        log.info("{} disconnected", session.getId());
        webSocketSessionMap.remove(session.getId());
    }
}
