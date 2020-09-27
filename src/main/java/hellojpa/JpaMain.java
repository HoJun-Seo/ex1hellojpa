package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

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

            /* 단방향 및 양방향 연관관계
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team); // 영속성 컨텍스트에 올라갈때 항상 기본 키가 같이 매핑된다.

            Member member = new Member();
            member.setUsername("member1");
            //member.setTeamId(team.getId()); // 객체지향 스러우려면 setTeam 이 되어야 하지 않을까?
            //member.setTeam(team); // 이렇게 해두면 JPA 가 알아서 team 에서 기본 키를 꺼낸 후 insert 할 때 외래키 값으로 사용한다.
            em.persist(member);

            // Entity 를 1차캐시가 아닌 DB 에서 가져오게끔 하기 위해 영속성 컨텍스트를 비워준다.
            em.flush();
            em.clear();

            //Member findMember = em.find(Member.class, member.getId());

            //Long findTeamId = findMember.getTeamId(); // 테이블 중심으로 설계하는 바람에 이번엔 팀 아이디를 찾으면서 또다시 기본키를 찾는 과정을 반복해야 한다.
            //Team findTeam = em.find(Team.class, findTeamId); // 기본 키 값을 한 번 찾는 걸로 팀을 알아낼 수는 없을까?

            //Team findTeam = findMember.getTeam(); // 팀 데이터를 바로 꺼낼 수 있다.
            //System.out.println("findTeam = " + findTeam.getName());

            // 팀을 바꾸고 싶다면?
            //Team newTeam = em.find(Team.class, 100L); // 키값이 100 번에 해당하는 팀이 있다고 가정한다.
            //findMember.setTeam(newTeam); // 찾아온 멤버 데이터의 팀을 변경해준다.(외래키가 업데이트 된다.)

            // 양방향 연관관계 매핑
            List<Member> members = findMember.getTeam().getMembers();
            for (Member m : members){
                System.out.println("m = " + m.getUsername());
            } // 왜 flush, clear 를 거쳐야 해당 출력문이 제대로 출력되는지는 다음강의 때 나오는 내용을 참고할 것
            // 참조 질문글 내용 : flush, clear 를 안 하면 members 컬렉션(List) 이 비어있는 상태로 영속성 컨텍스트에 반영된다?
             */





            // 양방향 연관관계의 주의 점 1. 연관관계의 주인의 값을 입력하지 않은 경우
            /*Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            Team team = new Team();
            team.setName("TeamA");
            team.getMembers().add(member); // 연관관계의 주인쪽 객체에서 값을 넣지 않고 주인이 아닌곳에서 값을 넣고 있다.
            // 그 결과 insert 가 제대로 반영되지 않아 멤버 데이터의 팀 값이 null 로 표시된다.
            em.persist(team);*/

            // 올바른 예시
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team); // 연관관계로 연결된 데이터 간에는 반드시 연관관계의 주인 객체에 먼저 값을 삽입해준다.
            em.persist(member);

            team.getMembers().add(member); // getMembers 를 통해 1차 캐시에서 데이터를 찾아온 후 값을 삽입해준다.
            // 여기서 이미 영속성 컨텍스트에 들어가 있는 entity 에 대해 수정의 경우 굳이 find 를 통해 찾아오거나 영속성 컨텍스트에서 빼낼 필요가 없었던 이유가 있었는데 기억이 나지 않는다.
            // 나중에 블로그에 글을 정리할 때 복습하며 참고하자.

            //em.flush();
            //em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();

            for (Member m : members){
                System.out.println("m = " + m.getUsername());
            }

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
