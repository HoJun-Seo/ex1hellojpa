package hellojpa;

import javax.persistence.*;

@Entity
public class Member extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne(fetch = FetchType.LAZY) // Team 클래스의 데이터를 프록시 객체로 조회하게 하는 속성
    @JoinColumn
    private Team team; // 일대다 관계에서 양방향 매핑을 하고 싶은 경우
    // 양쪽 도메인 모두 연관관계의 주인이 되는 경우를 막기 위해 insertable, updateable 속성을 false 로 지정하여 해당 객체를 읽기 전용으로 선언한다.


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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
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
