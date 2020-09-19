package hellojpa;

import javax.persistence.*;

@Entity // JPA 가 관리하는 클래스임을 알리는 어노테이션
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1, allocationSize = 50
)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    private Long id;
    //@Column(unique = true, length = 10)
    @Column(name = "name", nullable = false) // 데이터베이스 column 명을 name 으로 한다.
    private String username; // 객체는 username 을 쓰고 싶은데 DB 는 name 을 써야 하는 경우 Column 어노테이션의 name 속성을 활용한다.

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public Member() { // JPA 는 기본적으로 내부적으로 reflection 같은것들을 쓰기 때문에 동적으로 객체를 생성해내야 한다.
        //그렇기 때문에 기본 생성자가 하나 있어야 한다
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
