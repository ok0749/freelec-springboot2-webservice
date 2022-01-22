package com.jojoldu.book.springboot.config.auth.dto;

import com.jojoldu.book.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
// 인증된 사용자 정보만 필요하므로 name, email, picture만 필드로 선언한다.
/*
User 클래스를 사용하지 않고 SessionUser 라는 별도의 클래스를 사용하는 이유
- User 클래스를 세션에 저장하려고 하면 User 클래스에 직렬화를 구현하지 않았다는 에러가 발생한다.
- User 클래스는 entity이기 때문에 언제 다른 entity와 관계가 형성될지 모른다 그러므로 User 클래스 자체에 직렬화 코드를 넣는 것은 좋지않다.
- 직렬화 기능을 가진 세션 Dto를 하나 추가로 만드는 것이 유지보수에 유리하다.
*/

public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
