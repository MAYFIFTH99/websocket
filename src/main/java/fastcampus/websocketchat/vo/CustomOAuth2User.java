package fastcampus.websocketchat.vo;

import fastcampus.websocketchat.entity.Member;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@AllArgsConstructor
@Getter
public class CustomOAuth2User implements OAuth2User {

    Member member;
    Map<String, Object> attributeMap;


    @Override
    public Map<String, Object> getAttributes() {
        return this.attributeMap;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> member.getRole().name());
    }

    @Override
    public String getName() {
        return member.getNickname();
    }
}
