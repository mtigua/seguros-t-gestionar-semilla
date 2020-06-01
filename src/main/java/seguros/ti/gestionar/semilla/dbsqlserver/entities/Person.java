package seguros.ti.gestionar.semilla.dbsqlserver.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
//@Data
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Person{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String rut;
	private String firstName;	
	private String lastName;
		
	
	public Person() {}
	
	public Person(String rut, String firstName, String lastName) {
		
		this.rut = rut;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
    
	
    @Override
    public String toString() {
        String result = String.format(
                "Client[id=%d, firstName='%s']%n",
                id, firstName);
       

        return result;
    }
   

}
