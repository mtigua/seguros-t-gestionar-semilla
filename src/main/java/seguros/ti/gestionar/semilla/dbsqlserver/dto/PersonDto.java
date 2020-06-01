package seguros.ti.gestionar.semilla.dbsqlserver.dto;

import lombok.Data;

@Data
public class PersonDto{
	
	private Long id;
	private String rut;
	private String firstName;	
	private String lastName;
	
	public PersonDto(Long id,String rut, String firstName, String lastName) {
		this.id = id;
		this.rut = rut;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public PersonDto() {}
}
