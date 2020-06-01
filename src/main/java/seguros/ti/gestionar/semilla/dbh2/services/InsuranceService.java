package seguros.ti.gestionar.semilla.dbh2.services;

import seguros.ti.gestionar.semilla.dbh2.entities.Client;
import seguros.ti.gestionar.semilla.dbh2.entities.Insurance;
import seguros.ti.gestionar.semilla.dbh2.servicesImpl.ClientException;

public interface InsuranceService {

	public void saveInsurance(Client client, Insurance insurance) throws ClientException;
	public seguros.ti.gestionar.semilla.dbh2.dto.InsuranceDto getInsurance(Long insuranceId) throws ClientException;
	public seguros.ti.gestionar.semilla.dbh2.dto.ClientInsuranceDto getInsuranceFull(Long insuranceId) throws ClientException;
}
