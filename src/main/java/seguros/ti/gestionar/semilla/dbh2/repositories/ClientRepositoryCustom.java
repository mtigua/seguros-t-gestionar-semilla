package seguros.ti.gestionar.semilla.dbh2.repositories;

import java.util.List;

import seguros.ti.gestionar.semilla.dbh2.entities.Client;

public interface ClientRepositoryCustom {
	
	List<seguros.ti.gestionar.semilla.dto.ClientInsuranceDto> findByIdAndRate(Long id,Double rate);
	
	Client findWithInsurancesById(Long id);
	
	List<seguros.ti.gestionar.semilla.dto.ClientDto> findAllSingleDto(); 
	
}
