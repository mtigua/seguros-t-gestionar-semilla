package seguros.ti.gestionar.semilla.dto;

import lombok.Data;

@Data
public class KafkaMsgDto {

	private String key;
	private String body;
}
