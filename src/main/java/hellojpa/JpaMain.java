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

            // 임베디드 타입
            /*Member member = new Member();
            member.setUsername("hello");
            member.setHomeAddress(new Address("city", "street", "10000"));
            member.setWorkPeriod(new Period());

            em.persist(member);*/

            // 값 타입과 불변객체
            /*
            Address address = new Address("city", "street", "10000");
            // member1 과 member2 가 같은 address 를 사용하고 있다.
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(address);
            member.setWorkPeriod(new Period());
            em.persist(member);*/

            // 임베디드 타입 객체 address 값 복사
            /*
            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setHomeAddress(copyAddress); // 복사한 객체를 사용한다.
            member2.setWorkPeriod(new Period());
            em.persist(member2);

            // member2 에 복사한 객체를 사용하면 member1 의 임베디드 타입 값을 변경해도 member2 의 값은 변경되지 않는다.(공유되지 않기 때문)
            member.getHomeAddress().setCity("newCity"); // member1 의 주소만 newCity 로 바꾸고 싶은 경우
             */
            
            // Setter 메소드가 생성되지 않아 Address 클래스가 불변 객체로 만들어진 경우, 값을 변경하는 법
            /*
            Address newAddress = new Address("NewCity", address.getStreet(), address.getZipcode());
            member.setHomeAddress(newAddress);
            em.persist(member);*/


            // 값 타입 저장
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFood().add("치킨");
            member.getFavoriteFood().add("족발");
            member.getFavoriteFood().add("피자");

            //member.getAddressHistory().add(new Address("old1", "street", "10000"));
            //member.getAddressHistory().add(new Address("old2", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "10000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("===============START================");
            Member findMember = em.find(Member.class, member.getId());

            /* 값 타입 조회 지연 로딩
            List<Address> addressesHistory = findMember.getAddressHistory();
            for (Address address : addressesHistory){
                System.out.println("address = " + address.getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFood();
            for (String favoriteFood : favoriteFoods){
                System.out.println("favoriteFood = " + favoriteFood);
            }*/

            // 값 타입 데이터 수정
            //findMember.getHomeAddress().setCity("newCity");
            // 위와 같이 수정하면 되지 않냐고 생각 할 수 있는데 그러면 안된다.
            // 값 타입은 immutable 해야 하기 때문에(공유 참조 문제가 발생하면 안된다.)

            //Address a = findMember.getHomeAddress();
            //findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode())); // 아예 새로 만들어야 함

            // 치킨 -> 한식 변경
            //findMember.getFavoriteFood().remove("치킨");
            //findMember.getFavoriteFood().add("한식");
            // 아예 통째로 삭제하고 다시 넣어야 한다.(String 자체가 값 타입이기 때문에 아예 통째로 갈아끼워야 한다.)


            // 주소를 바꿔보자. old1 -> new1 (값 타입 컬렉션을 사용할 경우 -> 이마저도 제대로된 수정이 되지 않는다.)
            //findMember.getAddressHistory().remove(new AddressEntity("old1", "street", "10000")); // 통째로 갈아끼운다.
            // remove 에서 삭제하는 객체를 찾을 때 내부적으로 equals 메소드로 동작하기 때문에
            // Address 도메인 클래스 자체에 equals, hashCode 메소드를 오버라이드 해서 넣어주어야 한다.(아니면 망함)
            // 두 메소드가 제대로 들어가 있지 않으면 값이 지워지질 않는다.(컬렉션을 다룰 때 의미가 있다.)
            //findMember.getAddressHistory().add(new AddressEntity("newCity1", "street", "10000"));

            // AddressEntity 도메인 클래스로 일대다 단방향 매핑으로 컬렉션을 생성할 경우 데이터 수정
            AddressEntity updateAddress = findMember.getAddressHistory().get(new AddressEntity("old1", "street", "10000").getId());
            findMember.getAddressHistory().remove(updateAddress);
            findMember.getAddressHistory().add(new AddressEntity("newCity1", "street", "10000"));
            //updateAddress.setAddress(new Address("newCity1", "street", "10000"));




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
