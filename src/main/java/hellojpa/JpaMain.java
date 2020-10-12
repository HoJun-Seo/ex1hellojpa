package hellojpa;

import org.hibernate.Hibernate;

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

            /*
            Member member = em.find(Member.class, 1L);
            //printMemberAndTeam(member); // 비즈니스 로직상 member 와 team 을 같이 출력 해야하는 경우가 있는 경우?

            printMember(member); // 상황이 바뀌어서 member 만 출력하면 되는 경우? */

            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            em.persist(member2);

            em.flush();
            em.clear();

            //Member findMember = em.find(Member.class, member.getId());
            /*
            Member findMember = em.getReference(Member.class, member1.getId()); // getReference 를 통해 프록시 객체 생성
            System.out.println("findMember = " + findMember.getClass()); // 프록시 객체임을 알려주는 출력문
            System.out.println("findMember.id = " + findMember.getId()); // 이미 가지고 있는 데이터 이므로 SQL 이 출력되지 않는다.
            System.out.println("findMember = " + findMember.getUsername());
            // 프록시 객체를 통해 실제 클래스의 Entity 를 참조하여 데이터를 가져온다.*/

            /* == 비교, instance of 비교
            Member m1 = em.find(Member.class, member1.getId());
            //Member m2 = em.find(Member.class, member2.getId());
            Member m2 = em.getReference(Member.class, member2.getId());

            //System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass())); // 클래스 타입 비교
            System.out.println("m1 == m2 : " + (m1 instanceof Member));
            System.out.println("m1 == m2 : " + (m2 instanceof Member));*/

            // 영속성 컨텍스트에 찾는 Entity 가 이미 있는 경우
            /*Member m1 = em.find(Member.class, member1.getId()); // 영속성 컨텍스트에 member1 객체의 데이터를 올려놓는다.
            System.out.println("m1 = " + m1.getClass());

            Member reference = em.getReference(Member.class, member1.getId());
            System.out.println("reference = " + reference.getClass());

            System.out.println("m1 == reference : " + (m1 == reference));*/

            /*
            Member refMember = em.getReference(Member.class, member1.getId()); // 영속성 컨텍스트에 member1 객체의 데이터를 올려놓는다.
            System.out.println("refMember = " + refMember.getClass());

            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember = " + findMember.getClass());

            System.out.println("refMember == findMember : " + (refMember == findMember));
            // JPA 의 매커니즘 상 이미 프록시 객체가 생성되어 있으면 find 메소드를 통해 데이터를 가져와도
            // == 비교에서 true 를 반환해야 하기 때문에 프록시 객체로 생성된다.*/

            /* 준영속 상태가 된 프록시 객체를 초기화 하려고 하는 경우 발생하는 오류
            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());

            em.detach(refMember);
            //em.close();

            refMember.getUsername();// 프록시 객체가 실제로 사용되면서 초기화 된다.(영속성 컨텍스트를 통해 DB에 쿼리를 전달하게 된다.)
             */

            // 프록시 확인을 위한 메소드
            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());

            // EntityManagerFactory 클래스에서 지원하는 메소드이다.
            //refMember.getUsername(); // 프록시 객체 초기화
            Hibernate.initialize(refMember); // 프록시 강제 초기화
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));

            tx.commit(); // 커밋하는 시점에 진짜 데이터베이스에 쿼리가 전달된다.
        } catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close(); // EntityManager 가 내부적으로 데이터베이스 컬렉션을 물고 동작하기 때문에 사용이 끝나면 꼭 닫아줘야 한다.
        }


        // 실제 애플리케이션이 완전히 끝나면 entityManagerFactory 를 완전히 닫아줘야 한다.
        emf.close();
    }

    /*
    private static void printMember(Member member) {
        System.out.println("member = " + member.getUsername());
    }

    private static void printMemberAndTeam(Member member) {
        String username = member.getUsername();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }*/

}
