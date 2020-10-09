package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Team team; // 일대다 관계에서 양방향 매핑을 하고 싶은 경우
    // 양쪽 도메인 모두 연관관계의 주인이 되는 경우를 막기 위해 insertable, updateable 속성을 false 로 지정하여 해당 객체를 읽기 전용으로 선언한다.

    //@Column(name = "TEAM_ID")
    //private Long teamId; // 객체 지향 스럽게 코딩하려면 Member 와 Team 클래스간에 참조로 가져가야 하나
    // DB 테이블에 맞춰 모델링을 하고 있기 때문에 해당 필드를 직접 작성해주었다.(Team 클래스의 id 필드)

    /*@ManyToOne
    @JoinColumn(name = "TEAM_ID") // team 참조값과 데이터베이스의 외래키(TEAM_ID) 가 매핑되어야 한다.(이러면 매핑 끝남)
    private Team team;*/

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    /*@ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT") // 다대다 연관관계 연결 테이블 생성
    private List<Product> products = new ArrayList<>();*/

    // 다대다 연관관계 에서 연결 테이블을 엔티티로 승격시켜서 한계를 해결할 경우
    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();

    /*
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
    // 만약 모든 클래스에 위와 같은 필드들이 있어야 한다면 어떻게 해야 할까?(일일히 복붙 하기 귀찮음)
    // 속성만 부모 클래스에서 상속 받아보자.(Mapped SuperClass) */

    /*public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        //team.getMembers().add(this);
        // 연관관계 관련 메소드에 이와 같이 역방향 삽입 코드를 작성해 줄 수 있다.
    }*/

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

    /*public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }*/

    /*@Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", team=" + team +
                '}';
    }*/
}
