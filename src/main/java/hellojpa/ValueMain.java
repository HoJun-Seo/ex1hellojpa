package hellojpa;

public class ValueMain {
	public static void main(String[] args) {

		Integer a = new Integer(10);
		Integer b = a;
		// 이와 같은 경우 a 의 값 자체가 넘어가는 것이 아니라 a 의 메모리 주소값이 넘어간다.
		// C언어의 포인터와 같은 주소 참조 개념(객체 reference)

		// a.setValue(20);
		// 만약 여기서 setValue 와 같은 메소드를 이용해 a 의 값을 변경해주면 b 의 값도 함께 변경되어 버린다.
		// b 에 a 의 reference 가 넘어가서 같은 인스턴스를 공유하기 때문
		// 하지만 값을 변경할 수 있는 방법이 없다.(side - effect 효과가 일어날 여지가 없는것)

		System.out.println("a = " + a);
		System.out.println("b = " + b);
	}
}
