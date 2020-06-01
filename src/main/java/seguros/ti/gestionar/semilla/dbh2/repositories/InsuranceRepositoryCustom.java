package seguros.ti.gestionar.semilla.dbh2.repositories;

import seguros.ti.gestionar.semilla.dbh2.entities.Insurance;

public interface InsuranceRepositoryCustom {
	
	Insurance findWithClientById(Long id);
	seguros.ti.gestionar.semilla.dbh2.dto.ClientInsuranceDto findByInsuranceId(Long id);
	

	
}
