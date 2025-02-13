package fastcampus.websocketchat.dto;

import fastcampus.websocketchat.entity.Member;
import fastcampus.websocketchat.entity.Role;
import fastcampus.websocketchat.enums.Gender;
import java.time.LocalDate;

public record MemberDto(
        Long id,
        String email,
        String nickname,
        String name,
        String password,
        String confirmedPassword,
        Gender gender,
        String phoneNumber,
        LocalDate birthDay,
        Role role
        )

{
    public static MemberDto from(Member member){
        return new MemberDto(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getName(),
                null,
                null,
                member.getGender(),
                member.getPhoneNumber(),
                member.getBirthDay(),
                member.getRole()
        );
    }

    public static Member to(MemberDto memberDto){
        return Member.builder()
                .id(memberDto.id())
                .email(memberDto.email())
                .nickname(memberDto.nickname())
                .name(memberDto.name())
                .gender(memberDto.gender())
                .phoneNumber(memberDto.phoneNumber())
                .birthDay(memberDto.birthDay())
                .role(memberDto.role())
                .build();
    }

}
