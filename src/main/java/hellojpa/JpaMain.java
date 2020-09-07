package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // persistence.xml 파일에서 설정한 unit name
        // EntityManagerFactory 를 만드는 순간 데이터베이스 와 연결 등 왠만한 작업들을 다 할 수 있다.
        // EntityManagerFactory 는 애플리케이션 로딩 시점에 딱 하나만 만들어둬야 한다.

        EntityManager em = emf.createEntityManager(); // 팩토리에서 createEntityManger 를 꺼내야 한다.
        // entityManager 의 경우 트랜잭션(클라이언트가 서비스를 이용하는 일련의 과정)의 단위마다 꼭 하나씩 만들어줘야 한다.
        // 고객의 요청이 있을 경우 EntityManager 를 통해서 작업을 하게 된다.

        EntityTransaction tx = em.getTransaction(); // getTransaction 을 통해 트랜잭션을 얻을 수 있다.
        tx.begin(); // 데이터베이스 트랜잭션 시작
        try{ // 오류가 발생했을 때를 대비하기 위해 try - catch 문을 사용한다.

            // 데이터 삽입
            /*Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");*/

            //데이터 조회
            /*Member findmember = em.find(Member.class, 1L);
            System.out.println("findMember.id = " + findmember.getId());
            System.out.println("findMember.name = " + findmember.getName());*/

            // JPQL 을 통한 조건 탐색
            /*List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(8)
                    .getResultList();
            // JPQL 는 절대 테이블을 대상으로 쿼리를 짜지 않는다, 위의 코드의 경우 Member 객체에 대하여 쿼리를 작성한 것.
            // 테이블을 대상으로 쿼리가 작성될 경우 객체 지향의 사상이 깨어진다.
            for (Member member : result){
                System.out.println("member.name = " + member.getName());
            }*/

            //데이터 삭제
            //em.remove(findmember);

            //데이터 수정
            /*Member findmember = em.find(Member.class, 1L);
            // findmember.setId(3L); // 아이디 값은 변경되지 않는다. 왜?
            findmember.setName("HelloJPA");*/

            //em.persist(findmember); // 데이터 수정의 경우 persist 를 하지 않아도 된다.
            //JPA 를 통해서 entity 를 가져오면 JPA가 관리를 해준다.
            //JPA 가 데이터가 변경되었는지 그렇지 않은지 트랜잭션이 커밋되는 시점에 체크를 한다.
            //변경점이 있을 경우 update query 를 만들어서 날려준다. 이후 트랜잭션이 커밋 된다.

            //비영속
            /*Member member = new Member();
            member.setId(101L);
            member.setName("HelloJPA");*/

            //em.persist(member); // 영속
            //em.detach(member); // 준영속

            //Member findMemeber1 = em.find(Member.class, 101L); // SELECT query 실행
            //Member findMemeber2 = em.find(Member.class, 101L); // 같은 데이터를 찾는 경우이기 때문에 1차캐시 에서 데이터를 가져오게 된다.(SELECT query 실행되지 않음)
            //System.out.println("findMemeber.id = " + findMemeber1.getId());
            //System.out.println("findMemeber.name = " + findMemeber2.getName());
            //System.out.println("result = " + (findMemeber1 == findMemeber2));

            /* 영속성 컨텍스트 : 버퍼링을 통한 쓰기 지연의 예시
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            em.persist(member1);
            em.persist(member2); // persist 하는 순간 데이터베이스에 저장되는 것이 아니라 영속성 컨텍스트에 차곡차곡 entity 와 쿼리가 쌓이게 된다.

            System.out.println("=============================");*/

            // 엔티티 수정(변경 감지)
            //Member member = em.find(Member.class, 150L);
            //member.setName("ZZZZ");
            // em.persist(member) -> 데이터 수정시에 persist 를 활용할 필요 없다.
            // persist() 메소드를 날리지 않아도 1차캐시에 있는 스냅샷을 통한 비교 결과 변경점이 있을 경우 update query 가 데이터베이스로 전달되게 된다.

            /* flush() 직접 호출
            Member member = new Member(200L, "member200");
            em.persist(member);
            em.flush(); // 강제 호출*/

            // 준영속 상태로 만들기
            Member member = em.find(Member.class, 150L); // 영속성 컨텍스트에 값을 올림
            member.setName("AAAA"); // Dirty Checking 을 통한 변경
            
            em.detach(member); // 영속성 컨텍스트에서 빼버림으로서 준영속 상태로 전환
            // JPA 가 더 이상 해당 객체를 관리하지 않음
            // 커밋을 해도 update query 가 나오지 않는다.(즉, 데이터의 변경이 일어나지 않는다.)

            tx.commit(); // 커밋하는 시점에 진짜 데이터베이스에 쿼리가 전달된다.
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close(); // EntityManager 가 내부적으로 데이터베이스 컬렉션을 물고 동작하기 때문에 사용이 끝나면 꼭 닫아줘야 한다.
        }


         // 실제 애플리케이션이 완전히 끝나면 entityManagerFactory 를 완전히 닫아줘야 한다.
        emf.close();
    }
}