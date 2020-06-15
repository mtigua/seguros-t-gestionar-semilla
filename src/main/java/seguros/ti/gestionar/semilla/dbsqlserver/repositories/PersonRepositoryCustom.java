package seguros.ti.gestionar.semilla.dbsqlserver.repositories;

import java.util.List;

import seguros.ti.gestionar.semilla.dto.PersonDto;

public interface PersonRepositoryCustom {
	
		
	List<PersonDto> findAllSingleDto(); 
	
}
