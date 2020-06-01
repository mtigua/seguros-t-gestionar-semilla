package seguros.ti.gestionar.semilla.dbh2.servicesImpl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import seguros.ti.gestionar.semilla.dbh2.dto.ClientDto;
import seguros.ti.gestionar.semilla.dbh2.entities.Client;
import seguros.ti.gestionar.semilla.dbh2.repositories.ClientRepository;
import seguros.ti.gestionar.semilla.dbh2.services.ClientService;

import org.springframework.transaction.annotation.Transactional;
import java.util.function.Function;

@Service
public class ClientServiceImpl implements ClientService{
	
	private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class.getSimpleName());

	@Autowired
	private ClientRepository clientRepository;	
	
	@Override
	@Transactional
	public List<seguros.ti.gestionar.semilla.dbh2.dto.ClientDto> getClients() throws ClientException{
		try {
			//List<Client> clients = clientRepository.findAll();
			List<seguros.ti.gestionar.semilla.dbh2.dto.ClientDto> clients = clientRepository.findAllSingleDto();
			return clients;
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ClientException();
		}
	}

	@Override
	@Transactional
	public seguros.ti.gestionar.semilla.dbh2.dto.ClientDto getClient(Long id) throws ClientException {
		try {			
			Optional<Client> client = clientRepository.findById(id);
			if(!client.isPresent()){
				return null;
			}
			else{
				seguros.ti.gestionar.semilla.dbh2.dto.ClientDto clientDto = new seguros.ti.gestionar.semilla.dbh2.dto.ClientDto();
				Client clientObj = client.get();
				clientDto.setId(clientObj.getId());
				clientDto.setRut(clientObj.getRut());
				clientDto.setFirstName(clientObj.getFirstName());
				clientDto.setLastName(clientObj.getLastName());
				return clientDto;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new ClientException();
		}
	}
	

	@Override
	@Transactional
	public Client getClientByReference(Long id) throws ClientException {
		try {			
			Optional<Client> client= clientRepository.findById(id);
			return client.isPresent()?client.get():null;
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new ClientException();
		}
	}
	
	@Override
	public void saveClient(Client client) throws ClientException {
		try {			
			clientRepository.save(client);
			
		}catch(Exception e) {			
			logger.error(e.getMessage());
			throw new ClientException();
		}
	}

	@Override
	public void updateClient(Client client) throws ClientException {
		try {
			Optional<Client> clientBd= clientRepository.findById(client.getId());
			if(clientBd.isPresent()){
				clientRepository.save(client);
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			throw new ClientException();
		}
	}

	@Override
	public void deleteClient(Long id) throws ClientException {
		try {
			clientRepository.deleteById(id);
			
		}catch(Exception e) {
			logger.error(e.getMessage());
			throw new ClientException();
		}
	}
	
	@Override
	@Transactional
	public Page<ClientDto> getClientList(Pageable pageable) throws ClientException{
		try {
			Page<Client> pageClient = clientRepository.findAll(pageable);
			Page<ClientDto> pageClientDto = pageClient.map(new Function<Client, ClientDto>() {
			    @Override
			    public ClientDto apply(Client client) {
			        ClientDto clientDto = new ClientDto();
			        clientDto.setId(client.getId());
			        clientDto.setFirstName(client.getFirstName());
			        clientDto.setLastName(client.getLastName());
			        clientDto.setRut(client.getRut());
			        return clientDto;
			    }
			});
			return pageClientDto; 
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ClientException();
		}
	}
	
	@Override
	@Transactional
	public List<seguros.ti.gestionar.semilla.dbh2.dto.ClientInsuranceDto> getClientByIdAndRate(Long id, Double rate) throws ClientException {
		try {			
			List<seguros.ti.gestionar.semilla.dbh2.dto.ClientInsuranceDto> client = clientRepository.findByIdAndRate(id, rate);
			return client;
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ClientException();
		}
	}

	
}
