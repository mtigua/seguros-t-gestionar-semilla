package seguros.ti.gestionar.semilla.services;

import seguros.ti.gestionar.semilla.entities.Client;
import seguros.ti.gestionar.semilla.entities.Insurance;
import seguros.ti.gestionar.semilla.servicesImpl.ClientException;

public interface InsuranceService {

	public void saveInsurance(Client client, Insurance insurance) throws ClientException;
	public seguros.ti.gestionar.semilla.dto.InsuranceDto getInsurance(Long insuranceId) throws ClientException;
	public seguros.ti.gestionar.semilla.dto.ClientInsuranceDto getInsuranceFull(Long insuranceId) throws ClientException;
}
