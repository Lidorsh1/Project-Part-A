package Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Company {

	private int id;
	private String name;
	private String email;
	private String password;
	private List<Coupon>coupons  = new ArrayList<>();

	
	public Company() {
	}
	
	public Company(int id, String name, String email, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		 
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "Company id=" + id + ", name=" + name + ", email=" + email + ", password=" + password ;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Coupon> getList() {
		return coupons;
	}

	public void setList(List<Coupon> coupon) {
		this.coupons = coupon;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		return Objects.equals(email, other.email) && id == other.id && Objects.equals(name, other.name);
	}
	
	
	

}
