package seguros.ti.gestionar.semilla.dto;

import lombok.Data;

@Data
public class ClientDto{
	
	private Long id;
	private String rut;
	private String firstName;	
	private String lastName;
	
	public ClientDto(Long id,String rut, String firstName, String lastName) {
		this.id = id;
		this.rut = rut;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public ClientDto() {}
}
