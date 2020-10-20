package hellojpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// 값 타입 컬렉션을 사용하는 대신 일대다 관계로 매핑해서 사용해주는 것이 좋다.
@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

	@Id @GeneratedValue
	private int id;

	private Address address;

	public AddressEntity() {
	}

	public AddressEntity(Address address) {
		this.address = address;
	}

	public AddressEntity(String city, String street, String zipcode) {
		this.address = new Address(city, street, zipcode);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
