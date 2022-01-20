package com.jojoldu.book.springboot.domain.posts;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

// 클래스 내 모든 필드의 Getter 메소드 자동 생성
@Getter
// 기본 생성자 자동 추가
@NoArgsConstructor
// 테이블과 링크될 클래스임을 나타낸다.
// 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름을 매칭한다.
@Entity
public class Posts extends BaseTimeEntity {
    // PK 필드
    @Id
    // PK 생성 규칙
    // GenerationType.IDENTITY: auto_increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이 된다.
    // 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용
    // VARCHAR(255) -> VARCHAR(500)
    @Column(length = 500, nullable = false)
    private String title;

    // VARCHAR -> TEXT 타입 변경
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    // 해당 클래스의 빌더 패턴 클래스 생성
    // 생성자 상단에 선언 시 생성자에 포홤된 필드만 빌더에 포함
    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content =  content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
