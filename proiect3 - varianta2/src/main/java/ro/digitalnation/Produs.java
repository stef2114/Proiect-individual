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
	
	public Produs(String name, String overview, String use, String nutrinfo) {
		super();
		this.name = name;
		this.overview = overview;
		this.use = use;
		this.nutrinfo = nutrinfo;
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
