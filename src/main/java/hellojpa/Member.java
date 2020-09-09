package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity // JPA 가 관리하는 클래스임을 알리는 어노테이션
public class Member {

    @Id
    private Long id;
    @Column(unique = true, length = 10)
    private String name;

    public Member() { // JPA 는 기본적으로 내부적으로 reflection 같은것들을 쓰기 때문에 동적으로 객체를 생성해내야 한다.
        //그렇기 때문에 기본 생성자가 하나 있어야 한다
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
