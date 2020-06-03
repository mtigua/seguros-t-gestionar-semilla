package seguros.ti.gestionar.semilla.dto;

import lombok.Data;

@Data
public class CarDto{
	
	private Long id;
	private String number;
	private String color;
	
	public CarDto(String number,String color) {
		this.number = number;
		this.color = color;
	}
	
	public CarDto() {}
}
