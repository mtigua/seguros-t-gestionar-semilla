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
import seguros.ti.gestionar.semilla.configuration.PropertiesMsg;
import seguros.ti.gestionar.semilla.dbsqlserver.dto.PersonDto;
import seguros.ti.gestionar.semilla.dbsqlserver.entities.Person;
import seguros.ti.gestionar.semilla.dbsqlserver.services.PersonService;
import seguros.ti.gestionar.semilla.dbsqlserver.servicesImpl.PersonException;
import seguros.ti.gestionar.semilla.exceptions.ExceptionResponse;
import seguros.ti.gestionar.semilla.exceptions.ResourceNotFoundException;
import seguros.ti.gestionar.semilla.utils.Utils;

@RestController
@Api(value="Client Resource")
@RequestMapping("/persons")
@RefreshScope
@CrossOrigin(origins = "${domains.origin.allowed.semilla}", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PATCH,RequestMethod.DELETE,RequestMethod.OPTIONS})
public class PersonController {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class.getSimpleName());
	
	private static final String MSG_HTTP200 = "Operación exitosa";
	private static final String MSG_HTTP400 = "Formato de petición erroneo";
	private static final String MSG_HTTP401 = "No autorizado";
	private static final String MSG_HTTP403 = "No autorizado";
	private static final String MSG_HTTP404 = "No se encuentra el recurso solicitado";
	private static final String MSG_HTTP500 = "Error interno del sistema";
	private static final String SWAGGER_POST_DESC = "Registro de un nuevo cliente";
	private static final String SWAGGER_GET_ALL_DESC = "Obtener listado de clientes";
	private static final String SWAGGER_GET_DESC = "Obtener detalle de un cliente";
	private static final String SWAGGER_GET_DESC_PAG = "Obtener listado de clientes paginado";
	private static final String SWAGGER_PUT_DESC = "Actualizar cliente";
	private static final String SWAGGER_DELETE_DESC = "Eliminar cliente";
	private static final String PAGE_DEFAULT_SIZE = "7";
	
	@Autowired
    private PropertiesMsg propertiesMsg;
	
	
	@Autowired
	private PersonService personService;	
	
	
		
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
	public ResponseEntity<String> savePerson(@RequestBody PersonDto person) throws PersonException{	
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
		try {
			Person newPerson = new Person();
			newPerson.setRut(person.getRut());
			newPerson.setFirstName(person.getFirstName());
			newPerson.setLastName(person.getLastName());
			personService.savePerson(newPerson);
		}
		catch(PersonException e) {
			throw e;
		}		
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return new ResponseEntity<>(Utils.encodeString(this.propertiesMsg.getHttp200()), HttpStatus.OK);
	}	
	
	@ApiOperation(value = SWAGGER_GET_ALL_DESC, notes = SWAGGER_GET_ALL_DESC)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = MSG_HTTP200, response = seguros.ti.gestionar.semilla.dbh2.dto.ClientDto[].class),
		@ApiResponse(code = 400, message = MSG_HTTP400, response = ExceptionResponse.class),
		@ApiResponse(code = 401, message = MSG_HTTP401, response = ExceptionResponse.class),
		@ApiResponse(code = 403, message = MSG_HTTP403, response = ExceptionResponse.class),
		@ApiResponse(code = 404, message = MSG_HTTP404, response = ExceptionResponse.class),
		@ApiResponse(code = 500, message = MSG_HTTP500, response = ExceptionResponse.class) 
		})
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ResponseEntity<List<PersonDto>> getClients() throws PersonException{	
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
		List<PersonDto> persons = null;
		try {
			persons = personService.getPersons();
		}
		catch(PersonException e) {
			logger.error(e.getClass().toString() + " " + e.getMessage());
			throw e;
		}		
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return new ResponseEntity<List<PersonDto>>(persons, HttpStatus.OK);
	}	
	
	@ApiOperation(value = SWAGGER_GET_DESC, notes = SWAGGER_GET_DESC)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = MSG_HTTP200, response = seguros.ti.gestionar.semilla.dbh2.dto.ClientDto.class),
		@ApiResponse(code = 400, message = MSG_HTTP400, response = ExceptionResponse.class),
		@ApiResponse(code = 401, message = MSG_HTTP401, response = ExceptionResponse.class),
		@ApiResponse(code = 403, message = MSG_HTTP403, response = ExceptionResponse.class),
		@ApiResponse(code = 404, message = MSG_HTTP404, response = ExceptionResponse.class),
		@ApiResponse(code = 500, message = MSG_HTTP500, response = ExceptionResponse.class) 
		})
	@RequestMapping(value="/{id}/simple", method=RequestMethod.GET)	
	public ResponseEntity<PersonDto> getPerson(@PathVariable("id") Long id) throws PersonException, ResourceNotFoundException{	
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		PersonDto person = null;
		try {
			person = personService.getPerson(id);
			if(person == null) {
				ResourceNotFoundException e = new ResourceNotFoundException();
				throw e;
			}
		}
		catch(PersonException e) {
			logger.error(e.getClass().toString() + " " + e.getMessage());
			throw e;
		}		
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return new ResponseEntity<PersonDto>(person, HttpStatus.OK);
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
	public ResponseEntity<String> updateClient(@PathVariable("id") Long id, @RequestBody PersonDto person) throws PersonException{	
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
		try {
			Person newPerson = new Person();
			newPerson.setRut(person.getRut());
			newPerson.setFirstName(person.getFirstName());
			newPerson.setLastName(person.getLastName());
			newPerson.setId(id);
			personService.updatePerson(newPerson);
		}
		catch(PersonException e) {
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
	public ResponseEntity<String> deletePerson(@PathVariable("id") Long id) throws PersonException, UnsupportedEncodingException{	
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
		try {
			personService.deletePerson(id);
		}
		catch(PersonException e) {
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
	public ResponseEntity<Page<PersonDto>> getPersonPaging(@RequestParam("page") int pageIndex,@RequestParam(value ="size",required = false,defaultValue = PAGE_DEFAULT_SIZE) int pageSize) throws PersonException{
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
		Pageable pageable = null; 
		Page<PersonDto> page = null;
		try {
			pageable = PageRequest.of(pageIndex, pageSize);
		    page = personService.getPersonList(pageable);
		}
		catch(PersonException e) {
			logger.error(e.getClass().toString() + " " + e.getMessage());
			throw e;
		}
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return new ResponseEntity<Page<PersonDto>>(page, HttpStatus.OK);
	} 
	


	
}
