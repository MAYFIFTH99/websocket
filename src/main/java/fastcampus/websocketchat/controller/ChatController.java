package fastcampus.websocketchat.controller;

import fastcampus.websocketchat.entity.ChatRoom;
import fastcampus.websocketchat.service.ChatService;
import fastcampus.websocketchat.vo.CustomOAuth2User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatRoom createChatRoom(@AuthenticationPrincipal CustomOAuth2User user,
            @RequestParam String title) {

        return chatService.createChatRoom(user.getMember(), title);
    }

    @PostMapping("/{chatRoomId}")
    public Boolean joinChatRoom(@AuthenticationPrincipal CustomOAuth2User user,
            @PathVariable Long chatRoomId) {

        return chatService.joinChatRoom(user.getMember(), chatRoomId);
    }

    @DeleteMapping("/{chatRoomId}")
    public Boolean leaveChatRoom(@AuthenticationPrincipal CustomOAuth2User user,
            @PathVariable Long chatRoomId) {

        return chatService.leaveChatRoom(user.getMember(), chatRoomId);
    }

    @GetMapping("/{memberId}")
    public List<ChatRoom> getChatRoomList(@AuthenticationPrincipal CustomOAuth2User user) {
        return chatService.getJoinedChatRooms(user.getMember());
    }

}
