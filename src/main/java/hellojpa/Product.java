package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

	@Id @GeneratedValue
	private Long id;

	private String name;

	/*@ManyToMany(mappedBy = "products") // 다대다 연관관계 양방향 매핑 설정
	private List<Member> members = new ArrayList<>();*/

	@OneToMany(mappedBy = "product")
	private List<MemberProduct> memberProducts = new ArrayList<>();

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

	public List<MemberProduct> getMemberProducts() {
		return memberProducts;
	}

	public void setMemberProducts(List<MemberProduct> memberProducts) {
		this.memberProducts = memberProducts;
	}
}
