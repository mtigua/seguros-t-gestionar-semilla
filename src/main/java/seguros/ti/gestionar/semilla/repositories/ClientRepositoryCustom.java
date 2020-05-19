package seguros.ti.gestionar.semilla.repositories;

import java.util.List;

import seguros.ti.gestionar.semilla.entities.Client;

public interface ClientRepositoryCustom {
	
	List<seguros.ti.gestionar.semilla.dto.ClientInsuranceDto> findByIdAndRate(Long id,Double rate);
	
	Client findWithInsurancesById(Long id);
	
	List<seguros.ti.gestionar.semilla.dto.ClientDto> findAllSingleDto(); 
	
}
