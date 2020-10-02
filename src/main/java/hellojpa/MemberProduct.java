package hellojpa;

import javax.persistence.*;

@Entity // 다대다 연관관계의 한계를 극복하기 위한 연결 테이블 엔티티 화
public class MemberProduct {

	@Id @GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;

	// 이와 같은 형식으로 연결 테이블을 엔티티로 승격시켜 주면 원하는 정보를 얼마든지 넣어줄 수 있다.


}
