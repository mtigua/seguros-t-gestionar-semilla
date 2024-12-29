package seguros.ti.gestionar.semilla.dto;

import lombok.Data;
@Data
public class ClientInsuranceDto {
	
	private String rut;
	private String firstName;	
	private String lastName;
	private String name;
	private Double rate; 
	
	public ClientInsuranceDto() {}

	public ClientInsuranceDto(String rut, String firstName, String lastName, String name, Double rate) {
		this.rut = rut;
		this.firstName = firstName;
		this.lastName = lastName;
		this.name = name;
		this.rate = rate;
	}
	
	
}
