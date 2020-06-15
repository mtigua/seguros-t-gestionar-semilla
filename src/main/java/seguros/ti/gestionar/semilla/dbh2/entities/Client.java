package seguros.ti.gestionar.semilla.dbh2.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
//@Data
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Client{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String rut;
	private String firstName;
	
	private String lastName;
		
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "client",fetch = FetchType.LAZY)
    private Set<Insurance> insurances;
	
	public Client() {}
	
	public Client(String rut, String firstName, String lastName) {
		
		this.rut = rut;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Set<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(Set<Insurance> insurances) {
        this.insurances = insurances;
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
        if (insurances != null) {
            for(Insurance insurance : insurances) {
                result += String.format(
                        "Insurance[id=%d, name='%s']%n",
                        insurance.getId(), insurance.getName());
            }
        }

        return result;
    }
   

}
