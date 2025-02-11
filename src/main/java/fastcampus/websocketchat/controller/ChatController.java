package fastcampus.websocketchat.controller;

import fastcampus.websocketchat.dto.ChatMessage;
import fastcampus.websocketchat.dto.ChatRoomDto;
import fastcampus.websocketchat.entity.ChatRoom;
import fastcampus.websocketchat.entity.Message;
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
    public ChatRoomDto createChatRoom(@AuthenticationPrincipal CustomOAuth2User user,
            @RequestParam String title) {

        ChatRoom chatRoom = chatService.createChatRoom(user.getMember(), title);
        return ChatRoomDto.from(chatRoom);
    }

    @PostMapping("/{chatRoomId}")
    public Boolean joinChatRoom(@AuthenticationPrincipal CustomOAuth2User user,
            @PathVariable Long chatRoomId, @RequestParam(required = false) Long currentChatroomId) {

        return chatService.joinChatRoom(user.getMember(), chatRoomId, currentChatroomId);
    }

    @DeleteMapping("/{chatRoomId}")
    public Boolean leaveChatRoom(@AuthenticationPrincipal CustomOAuth2User user,
            @PathVariable Long chatRoomId) {

        return chatService.leaveChatRoom(user.getMember(), chatRoomId);
    }

    @GetMapping
    public List<ChatRoomDto> getChatRoomList(@AuthenticationPrincipal CustomOAuth2User user) {
        List<ChatRoom> joinedChatRooms = chatService.getChatroomList(user.getMember());

        return joinedChatRooms.stream().map(ChatRoomDto::from).toList();
    }

    @GetMapping("/{chatRoomId}/messages")
    public List<ChatMessage> getMessageList(@PathVariable Long chatRoomId) {
        List<Message> messageList = chatService.getMessageList(chatRoomId);
        return messageList.stream().map(
                message ->
                        new ChatMessage(message.getMember().getNickname(), message.getText())
        ).toList();
    }
}
