package ro.digitalnation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Produs {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name, overview, use, nutrinfo;
	private int price;
	
	public Produs(String name, String overview, String use, String nutrinfo, int price) {
		super();
		this.name = name;
		this.overview = overview;
		this.use = use;
		this.nutrinfo = nutrinfo;
		this.price = price;
	}
	
	public Produs(Long id, String name, String overview, String use, String nutrinfo, int price) {
		super();
		this.id = id;
		this.name = name;
		this.overview = overview;
		this.use = use;
		this.nutrinfo = nutrinfo;
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Produs(String name) {
		super();
		this.name = name;
	}
	
	public Produs() {
		super();
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOverview() {
		return overview;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getNutrinfo() {
		return nutrinfo;
	}
	public void setNutrinfo(String nutrinfo) {
		this.nutrinfo = nutrinfo;
	}
	
}
