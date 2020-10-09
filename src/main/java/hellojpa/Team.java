package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends BaseEntity{

	@Id @GeneratedValue
	@Column(name = "TEAM_ID")
	private Long id;

	private String name;

	// 일대다 매핑에서 어떤것과 연결되어 있는지를 알려준다.(반대편 도메인에서 참조값으로 사용하고 있는 변수명으로 지정해주어야 한다.)
	@OneToMany //(mappedBy = "team") // Member 에서는 JoinColumn 으로 알려줬는데 여기는 왜 다를까?
	@JoinColumn(name = "TEAM_ID") // 일대다(1 : N) 매핑의 경우(도메인 클래스와 외래 키가 존재하는 테이블의 이름이 다른 경우)
	private List<Member> members = new ArrayList<>(); // 양방향 연관관계
	// ArrayList 로 초기화 하는 이유? add 할 때 NullPointer 가 발생하지 않는다.(일종의 관례)


	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
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

	/*@Override
	public String toString() {
		return "Team{" +
				"id=" + id +
				", name='" + name + '\'' +
				", members=" + members +
				'}';
	}*/
}
