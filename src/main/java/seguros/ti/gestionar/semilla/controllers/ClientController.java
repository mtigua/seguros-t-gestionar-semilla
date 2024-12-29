package seguros.ti.gestionar.semilla.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import seguros.ti.gestionar.semilla.configuration.KafkaProducer;
import seguros.ti.gestionar.semilla.configuration.PropertiesMsg;
import seguros.ti.gestionar.semilla.dbh2.entities.Client;
import seguros.ti.gestionar.semilla.dto.ClientDto;
import seguros.ti.gestionar.semilla.dto.KafkaMsgDto;
import seguros.ti.gestionar.semilla.exceptions.ExceptionResponse;
import seguros.ti.gestionar.semilla.exceptions.ResourceNotFoundException;
import seguros.ti.gestionar.semilla.services.ClientService;
import seguros.ti.gestionar.semilla.servicesImpl.ClientException;
import seguros.ti.gestionar.semilla.utils.Utils;

@RestController
@Api(value="Client Resource")
@RequestMapping("/clients")
@RefreshScope
@CrossOrigin(origins = "${domains.origin.allowed.semilla}", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PATCH,RequestMethod.DELETE,RequestMethod.OPTIONS})
public class ClientController {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientController.class.getSimpleName());
	
	private static final String MSG_HTTP200 = "Operación exitosa";
	private static final String MSG_HTTP400 = "Formato de petición erroneo";
	private static final String MSG_HTTP401 = "No autorizado";
	private static final String MSG_HTTP403 = "No autorizado";
	private static final String MSG_HTTP404 = "No se encuentra el recurso solicitado";
	private static final String MSG_HTTP500 = "Error interno del sistema";
	private static final String SWAGGER_POST_DESC = "Registro de un nuevo cliente";
	private static final String SWAGGER_POST_PRODUCTOR_KAFKA = "Producciòn de mensaje hacia Kafka";
	private static final String SWAGGER_GET_ALL_DESC = "Obtener listado de clientes";
	private static final String SWAGGER_GET_DESC = "Obtener detalle de un cliente";
	private static final String SWAGGER_GET_DESC_PAG = "Obtener listado de clientes paginado";
	private static final String SWAGGER_GET_DESC_BY_ID_RATE = "Obtener detalle de un cliente filtrado por el valor de la tarifa de su seguro";
	private static final String SWAGGER_PUT_DESC = "Actualizar cliente";
	private static final String SWAGGER_DELETE_DESC = "Eliminar cliente";
	private static final String PAGE_DEFAULT_SIZE = "7";
	private static final String KAFKA_TOPIC_TO_PRODUCE = "topic-semilla";
	
	@Autowired
    private PropertiesMsg propertiesMsg;
	
	@Autowired
    private KafkaProducer kafkaProduces;
	
	@Autowired
	private ClientService clientService;	
	
	@ApiOperation(value = SWAGGER_POST_PRODUCTOR_KAFKA, notes = SWAGGER_POST_PRODUCTOR_KAFKA)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = MSG_HTTP200, response = String.class),
		@ApiResponse(code = 400, message = MSG_HTTP400, response = ExceptionResponse.class),
		@ApiResponse(code = 401, message = MSG_HTTP401, response = ExceptionResponse.class),
		@ApiResponse(code = 403, message = MSG_HTTP403, response = ExceptionResponse.class),
		@ApiResponse(code = 404, message = MSG_HTTP404, response = ExceptionResponse.class),
		@ApiResponse(code = 500, message = MSG_HTTP500, response = ExceptionResponse.class) 
		})
	@RequestMapping(value="/sendKafkaMesage", method=RequestMethod.POST)
	public ResponseEntity<String> sendKafkaMessage(@RequestBody KafkaMsgDto message) throws Exception{	
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
		try {
			
			kafkaProduces.sendMessage(KAFKA_TOPIC_TO_PRODUCE, message);
		}
		catch(Exception e) {
			throw e;
		}		
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return new ResponseEntity<>(Utils.encodeString(this.propertiesMsg.getHttp200()), HttpStatus.OK);
	}
	
		
	@ApiOperation(value = SWAGGER_POST_DESC, notes = SWAGGER_POST_DESC)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = MSG_HTTP200, response = String.class),
		@ApiResponse(code = 400, message = MSG_HTTP400, response = ExceptionResponse.class),
		@ApiResponse(code = 401, message = MSG_HTTP401, response = ExceptionResponse.class),
		@ApiResponse(code = 403, message = MSG_HTTP403, response = ExceptionResponse.class),
		@ApiResponse(code = 404, message = MSG_HTTP404, response = ExceptionResponse.class),
		@ApiResponse(code = 500, message = MSG_HTTP500, response = ExceptionResponse.class) 
		})
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ResponseEntity<String> saveClient(@RequestBody ClientDto client) throws ClientException{	
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
		try {
			Client newClient = new Client(client.getRut(), client.getFirstName(), client.getLastName());
			clientService.saveClient(newClient);
		}
		catch(ClientException e) {
			throw e;
		}		
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return new ResponseEntity<>(Utils.encodeString(this.propertiesMsg.getHttp200()), HttpStatus.OK);
	}	
	
	@ApiOperation(value = SWAGGER_GET_ALL_DESC, notes = SWAGGER_GET_ALL_DESC)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = MSG_HTTP200, response = seguros.ti.gestionar.semilla.dto.ClientDto[].class),
		@ApiResponse(code = 400, message = MSG_HTTP400, response = ExceptionResponse.class),
		@ApiResponse(code = 401, message = MSG_HTTP401, response = ExceptionResponse.class),
		@ApiResponse(code = 403, message = MSG_HTTP403, response = ExceptionResponse.class),
		@ApiResponse(code = 404, message = MSG_HTTP404, response = ExceptionResponse.class),
		@ApiResponse(code = 500, message = MSG_HTTP500, response = ExceptionResponse.class) 
		})
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ResponseEntity<List<seguros.ti.gestionar.semilla.dto.ClientDto>> getClients() throws ClientException{	
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
		List<seguros.ti.gestionar.semilla.dto.ClientDto> clients = null;
		try {
			clients = clientService.getClients();
		}
		catch(ClientException e) {
			logger.error(e.getClass().toString() + " " + e.getMessage());
			throw e;
		}		
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return new ResponseEntity<List<seguros.ti.gestionar.semilla.dto.ClientDto>>(clients, HttpStatus.OK);
	}	
	
	@ApiOperation(value = SWAGGER_GET_DESC, notes = SWAGGER_GET_DESC)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = MSG_HTTP200, response = seguros.ti.gestionar.semilla.dto.ClientDto.class),
		@ApiResponse(code = 400, message = MSG_HTTP400, response = ExceptionResponse.class),
		@ApiResponse(code = 401, message = MSG_HTTP401, response = ExceptionResponse.class),
		@ApiResponse(code = 403, message = MSG_HTTP403, response = ExceptionResponse.class),
		@ApiResponse(code = 404, message = MSG_HTTP404, response = ExceptionResponse.class),
		@ApiResponse(code = 500, message = MSG_HTTP500, response = ExceptionResponse.class) 
		})
	@RequestMapping(value="/{id}/simple", method=RequestMethod.GET)	
	public ResponseEntity<seguros.ti.gestionar.semilla.dto.ClientDto> getClient(@PathVariable("id") Long id) throws ClientException, ResourceNotFoundException{	
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		seguros.ti.gestionar.semilla.dto.ClientDto client = null;
		try {
			client = clientService.getClient(id);
			if(client == null) {
				ResourceNotFoundException e = new ResourceNotFoundException();
				throw e;
			}
		}
		catch(ClientException e) {
			logger.error(e.getClass().toString() + " " + e.getMessage());
			throw e;
		}		
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return new ResponseEntity<seguros.ti.gestionar.semilla.dto.ClientDto>(client, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = SWAGGER_PUT_DESC, notes = SWAGGER_PUT_DESC)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = MSG_HTTP200, response = String.class),
		@ApiResponse(code = 400, message = MSG_HTTP400, response = ExceptionResponse.class),
		@ApiResponse(code = 401, message = MSG_HTTP401, response = ExceptionResponse.class),
		@ApiResponse(code = 403, message = MSG_HTTP403, response = ExceptionResponse.class),
		@ApiResponse(code = 404, message = MSG_HTTP404, response = ExceptionResponse.class),
		@ApiResponse(code = 500, message = MSG_HTTP500, response = ExceptionResponse.class) 
		})
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<String> updateClient(@PathVariable("id") Long id, @RequestBody ClientDto client) throws ClientException{	
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
		try {
			Client newClient = new Client(client.getRut(), client.getFirstName(), client.getLastName());
			newClient.setId(id);
			clientService.updateClient(newClient);
		}
		catch(ClientException e) {
			logger.error(e.getClass().toString() + " " + e.getMessage());
			throw e;
		}		
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return new ResponseEntity<String>(Utils.encodeString(this.propertiesMsg.getHttp200()), HttpStatus.OK);
	}	
	
	@ApiOperation(value = SWAGGER_DELETE_DESC, notes = SWAGGER_DELETE_DESC)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = MSG_HTTP200, response = String.class),
		@ApiResponse(code = 400, message = MSG_HTTP400, response = ExceptionResponse.class),
		@ApiResponse(code = 401, message = MSG_HTTP401, response = ExceptionResponse.class),
		@ApiResponse(code = 403, message = MSG_HTTP403, response = ExceptionResponse.class),
		@ApiResponse(code = 404, message = MSG_HTTP404, response = ExceptionResponse.class),
		@ApiResponse(code = 500, message = MSG_HTTP500, response = ExceptionResponse.class) 
		})
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteClient(@PathVariable("id") Long id) throws ClientException, UnsupportedEncodingException{	
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
		try {
			clientService.deleteClient(id);
		}
		catch(ClientException e) {
			logger.error(e.getClass().toString() + " " + e.getMessage());
			throw e;
		}		
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return new ResponseEntity<String>(Utils.encodeString(this.propertiesMsg.getHttp200()), HttpStatus.OK);
	}
	
	@ApiOperation(value = SWAGGER_GET_DESC_PAG, notes = SWAGGER_GET_DESC_PAG)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = MSG_HTTP200, response = Page.class),
		@ApiResponse(code = 400, message = MSG_HTTP400, response = ExceptionResponse.class),
		@ApiResponse(code = 401, message = MSG_HTTP401, response = ExceptionResponse.class),
		@ApiResponse(code = 403, message = MSG_HTTP403, response = ExceptionResponse.class),
		@ApiResponse(code = 404, message = MSG_HTTP404, response = ExceptionResponse.class),
		@ApiResponse(code = 500, message = MSG_HTTP500, response = ExceptionResponse.class) 
	})
	@RequestMapping(value="/paging", method=RequestMethod.GET)
	public ResponseEntity<Page<seguros.ti.gestionar.semilla.dto.ClientDto>> getClientsPaging(@RequestParam("page") int pageIndex,@RequestParam(value ="size",required = false,defaultValue = PAGE_DEFAULT_SIZE) int pageSize) throws ClientException{
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
		Pageable pageable = null; 
		Page<seguros.ti.gestionar.semilla.dto.ClientDto> page = null;
		try {
			pageable = PageRequest.of(pageIndex, pageSize);
		    page = clientService.getClientList(pageable);
		}
		catch(ClientException e) {
			logger.error(e.getClass().toString() + " " + e.getMessage());
			throw e;
		}
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return new ResponseEntity<Page<seguros.ti.gestionar.semilla.dto.ClientDto>>(page, HttpStatus.OK);
	} 
	

	@ApiOperation(value = SWAGGER_GET_DESC_BY_ID_RATE, notes = SWAGGER_GET_DESC_BY_ID_RATE)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = MSG_HTTP200, response = seguros.ti.gestionar.semilla.dto.ClientInsuranceDto[].class),
		@ApiResponse(code = 400, message = MSG_HTTP400, response = ExceptionResponse.class),
		@ApiResponse(code = 401, message = MSG_HTTP401, response = ExceptionResponse.class),
		@ApiResponse(code = 403, message = MSG_HTTP403, response = ExceptionResponse.class),
		@ApiResponse(code = 404, message = MSG_HTTP404, response = ExceptionResponse.class),
		@ApiResponse(code = 500, message = MSG_HTTP500, response = ExceptionResponse.class) 
	})
	@RequestMapping(value="/{id}/tarifas", method=RequestMethod.GET)	
	public ResponseEntity<List<seguros.ti.gestionar.semilla.dto.ClientInsuranceDto>> getClientByRate(@PathVariable("id") Long id,@RequestParam("minimumRate") Double minimumRate) throws ClientException, ResourceNotFoundException{	
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		List<seguros.ti.gestionar.semilla.dto.ClientInsuranceDto> clients = null;
		try {
			clients = clientService.getClientByIdAndRate(id,minimumRate);
			if(clients == null) {
				ResourceNotFoundException e = new ResourceNotFoundException();
				throw e;
			}
		}
		catch(ClientException e) {
			logger.error(e.getClass().toString() + " " + e.getMessage());
			throw e;
		}		
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return new ResponseEntity<List<seguros.ti.gestionar.semilla.dto.ClientInsuranceDto>>(clients, HttpStatus.OK);
	}

	
}
