package fastcampus.websocketchat.controller;

import fastcampus.websocketchat.dto.ChatMessage;
import fastcampus.websocketchat.dto.ChatRoomDto;
import fastcampus.websocketchat.entity.Message;
import fastcampus.websocketchat.service.ChatService;
import fastcampus.websocketchat.vo.CustomOAuth2User;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StompChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chats/{chatroomId}") // /pub/chats
    @SendTo("/sub/chats/{chatroomId}")
    public ChatMessage handleMessage(@AuthenticationPrincipal Principal principal,
            @DestinationVariable Long chatroomId,
            @Payload Map<String, String> payload) {

        log.info("{} sent {} in {}", principal.getName(), payload, chatroomId);
        CustomOAuth2User user = (CustomOAuth2User) ((AbstractAuthenticationToken) principal).getPrincipal();
        Message message = chatService.saveMessage(user.getMember(), chatroomId,
                payload.get("message"));
        messagingTemplate.convertAndSend("/sub/chats/updates", chatService.getChatRoom(chatroomId));
        return new ChatMessage(principal.getName(), payload.get("message"));
    }
}
