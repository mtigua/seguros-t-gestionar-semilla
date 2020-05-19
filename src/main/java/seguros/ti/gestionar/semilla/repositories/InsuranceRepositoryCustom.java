package seguros.ti.gestionar.semilla.repositories;

import seguros.ti.gestionar.semilla.entities.Insurance;

public interface InsuranceRepositoryCustom {
	
	Insurance findWithClientById(Long id);
	seguros.ti.gestionar.semilla.dto.ClientInsuranceDto findByInsuranceId(Long id);
	

	
}
