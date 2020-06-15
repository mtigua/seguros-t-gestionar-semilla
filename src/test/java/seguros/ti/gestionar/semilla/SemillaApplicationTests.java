package seguros.ti.gestionar.semilla;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import seguros.ti.gestionar.semilla.dbh2.entities.Client;
import seguros.ti.gestionar.semilla.dbh2.repositories.ClientRepository;
import seguros.ti.gestionar.semilla.dto.ClientDto;
import seguros.ti.gestionar.semilla.servicesImpl.ClientException;
import seguros.ti.gestionar.semilla.servicesImpl.ClientServiceImpl;

import static org.mockito.Mockito.when;


public class SemillaApplicationTests {
	
	
	@InjectMocks
    ClientServiceImpl clientServiceImpl;
     
	@Mock
	ClientRepository clientRepository;
 
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void getClients() throws ClientException
    {
        when(clientRepository.findAllSingleDto()).thenReturn(this.getClientsDtoList());         
        List<ClientDto> empList = clientServiceImpl.getClients();         
        assertEquals(3, empList.size());
    }
    
    @Test
    public void getClientById() throws ClientException
    {
    	when(clientRepository.findById(2L)).thenReturn(this.getOptionalClient());         
        ClientDto client = clientServiceImpl.getClient(2L);    
        assertEquals(client.getFirstName(), "Segundo");
    }    
    
    private List<ClientDto> getClientsDtoList(){
    	
    	List<ClientDto> list = new ArrayList<ClientDto>();
        ClientDto client1 = new ClientDto(1L, "112345-k", "Primero", "App Primero");
        ClientDto client2 = new ClientDto(2L, "84663-5", "Segundo", "App Segundo");
        ClientDto client3 = new ClientDto(3L, "73663664-k", "Tercero", "App Tercero");
         
        list.add(client1);
        list.add(client2);
        list.add(client3);
        
        return list;
    	
    }
    
    private Optional<Client> getOptionalClient(){
    	Optional<Client> op = Optional.ofNullable(getClientsList().get(1));
    	return op;
    }
    
    private List<Client> getClientsList(){
    	
    	List<Client> list = new ArrayList<Client>();
    	Client client1 = new Client("112345-k", "Primero", "App Primero");
    	Client client2 = new Client("84663-5", "Segundo", "App Segundo");
    	Client client3 = new Client("73663664-k", "Tercero", "App Tercero");
         
        list.add(client1);
        list.add(client2);
        list.add(client3);
        
        return list;    	
    }
}
