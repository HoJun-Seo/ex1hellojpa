package hellojpa;

import javax.persistence.*;

@Entity
public class Member {

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
