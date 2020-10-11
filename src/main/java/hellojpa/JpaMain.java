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


            Movie movie = new Movie();
            movie.setDirector("aaaa");
            movie.setActor("bbbb");
            movie.setName("인터스텔라");
            movie.setPrice(10000);

            // BaseEntity - MappedSuperClass
            /*Member member = new Member();
            member.setUsername("user");
            member.setCreatedBy("kim");
            member.setCreatedDate(LocalDateTime.now());*/

            //em.persist(member);

            em.persist(movie);

            //상속 관계에서 조회의 경우
            em.flush();
            em.clear();

            Item item = em.find(Item.class, movie.getId());
            System.out.println("findMovie = " + item);

            // 구현 클래스마다 테이블을 생성하는 전략을 사용할 경우 발생하는 문제점
            //Item item = em.find(Item.class, movie.getId());
            //System.out.println("Item = " + item);
            // select 쿼리 호출 시 데이터를 찾기 위해 union 명령을 이용하여 모든 테이블을 전부 다 뒤져야 한다.

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
