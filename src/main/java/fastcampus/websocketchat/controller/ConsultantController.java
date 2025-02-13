package fastcampus.websocketchat.controller;

import fastcampus.websocketchat.dto.ChatRoomDto;
import fastcampus.websocketchat.dto.MemberDto;
import fastcampus.websocketchat.service.ConsultantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/consultants")
@RequiredArgsConstructor
public class ConsultantController {

    private final ConsultantService consultantService;

    @PostMapping
    @ResponseBody
    public MemberDto saveMember(@RequestBody MemberDto memberDto){
        return consultantService.saveMember(memberDto);
    }

    @GetMapping
    public String index(){
        return "consultants/index.html";
    }

    @GetMapping("/chats")
    public Page<ChatRoomDto> getAllChatrooms(Pageable pageable){
        return consultantService.getAllChatrooms(pageable);
    }

}
