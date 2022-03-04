package ro.digitalnation;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Client{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String firstname, lastname, city,email,pasword;
	private ArrayList <Long> favorite;
	private ArrayList <Long> basket;
	
	

	public Client(String email, String pasword) {
		super();
		this.email = email;
		this.pasword = pasword;
	}

	public Client(String firstname, String lastname, String city, String email, 
			String pasword,ArrayList<Long> favorite, ArrayList<Long> basket) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.city = city;
		this.email = email;
		this.pasword = pasword;
		this.favorite = favorite;
		this.basket = basket;
	}

	public Client(String firstname, String lastname, String city, String email,
			String pasword) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.city = city;
		this.email = email;
		this.pasword = pasword;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasword() {
		return pasword;
	}

	public void setPasword(String pasword) {
		this.pasword = pasword;
	}

	public Client() {
		super();
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public ArrayList<Long> getFavorite() {
		return favorite;
	}

	public void setFavorite(ArrayList<Long> favorite) {
		this.favorite = favorite;
	}

	public ArrayList<Long> getBasket() {
		return basket;
	}

	public void setBasket(ArrayList<Long> basket) {
		this.basket = basket;
	}
	
	

}
