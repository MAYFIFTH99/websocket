package fastcampus.websocketchat.service;

import fastcampus.websocketchat.entity.Member;
import fastcampus.websocketchat.entity.Role;
import fastcampus.websocketchat.enums.Gender;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class MemberFactory {

    public static Member create(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        return switch (userRequest.getClientRegistration().getRegistrationId()) {
            case "kakao" -> {
                Map<String, Object> attributeMap = oAuth2User.getAttribute("kakao_account");
                yield Member.builder()
                        .email((String) attributeMap.get("email"))
                        .nickname((String) ((Map) attributeMap.get("profile")).get("nickname"))
                        .name(attributeMap.get("name").toString())
                        .phoneNumber(attributeMap.get("phone_number").toString())
                        .gender(Gender.valueOf(((String) attributeMap.get("gender")).toUpperCase()))
                        .birthDay(getBirthDay(attributeMap))
                        .role(Role.USER)
                        .build();
            }
            case "google" -> {
                Map<String, Object> attributeMap = oAuth2User.getAttributes();
                yield Member.builder()
                        .email((String) attributeMap.get("email"))
                        .nickname((String) attributeMap.get("given_name"))
                        .name(attributeMap.get("name").toString())
                        .role(Role.USER)
                        .build();
            }
            default -> throw new IllegalArgumentException("Unsupported OAuth2 provider");

        };
    }

    private static LocalDate getBirthDay(Map<String, Object> attributeMap) {
        String birthYear = attributeMap.get("birthyear").toString();
        String birthDay = attributeMap.get("birthday").toString();

        return LocalDate.parse(birthYear + birthDay, DateTimeFormatter.BASIC_ISO_DATE);
    }

}
