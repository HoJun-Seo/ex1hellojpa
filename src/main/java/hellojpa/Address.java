package hellojpa;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {

	//Address
	private String city;
	private String street;
	@Column(name = "ZIPCODE") // 컬럼의 이름을 바꿔주는 것 또한 가능하다.
	private String zipcode;

	//private Member member; 내부에 Entity 를 얻어오는 것이 가능하다.

	public Address() {
	}

	public Address(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Address address = (Address) o;
		return Objects.equals(city, address.city) &&
				Objects.equals(street, address.street) &&
				Objects.equals(zipcode, address.zipcode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, street, zipcode);
	}
}
