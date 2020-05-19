package seguros.ti.gestionar.semilla.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
//@Data

@Getter 
@Setter

public class Insurance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Double rate; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLIENT_ID", nullable = false)
	@JsonIgnoreProperties("insurances")
	private Client client;
	
	public Insurance() {}
	
	public Insurance(String name,Double rate) {
		this.name = name;
		this.rate = rate;
	}

}
