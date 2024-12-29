package seguros.ti.gestionar.semilla.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import seguros.ti.gestionar.semilla.dbh2.entities.Client;
import seguros.ti.gestionar.semilla.servicesImpl.ClientException;

public interface ClientService {

	public List<seguros.ti.gestionar.semilla.dto.ClientDto> getClients()throws ClientException;

	public seguros.ti.gestionar.semilla.dto.ClientDto getClient(Long id) throws ClientException;
	
	public Client getClientByReference(Long id) throws ClientException;

	public void saveClient(Client client) throws ClientException;

	public void updateClient(Client client) throws ClientException;

	public void deleteClient(Long id) throws ClientException;
	
	public Page<seguros.ti.gestionar.semilla.dto.ClientDto > getClientList(Pageable pageable) throws ClientException;
	
	public List<seguros.ti.gestionar.semilla.dto.ClientInsuranceDto> getClientByIdAndRate(Long id, Double rate) throws ClientException;
	
}
