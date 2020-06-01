package seguros.ti.gestionar.semilla.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import seguros.ti.gestionar.semilla.configuration.PropertiesMsg;
import seguros.ti.gestionar.semilla.dbh2.dto.ClientInsuranceDto;
import seguros.ti.gestionar.semilla.dbh2.dto.InsuranceDto;
import seguros.ti.gestionar.semilla.dbh2.entities.Client;
import seguros.ti.gestionar.semilla.dbh2.entities.Insurance;
import seguros.ti.gestionar.semilla.dbh2.servicesImpl.ClientException;
import seguros.ti.gestionar.semilla.dbh2.services.ClientService;
import seguros.ti.gestionar.semilla.dbh2.services.InsuranceService;
import seguros.ti.gestionar.semilla.exceptions.ExceptionResponse;
import seguros.ti.gestionar.semilla.exceptions.ResourceNotFoundException;
import seguros.ti.gestionar.semilla.utils.Utils;

@RestController
@Api(value="Insurance Resource")
@RequestMapping("/clients/{clientId}/insurances")
@RefreshScope
@CrossOrigin(origins = "${domains.origin.allowed.semilla}", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PATCH,RequestMethod.DELETE,RequestMethod.OPTIONS})
public class InsuranceController {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientController.class.getSimpleName());
	
	private static final String MSG_HTTP200 = "Operación exitosa";
	private static final String MSG_HTTP400 = "Formato de petición erroneo";
	private static final String MSG_HTTP401 = "No autorizado";
	private static final String MSG_HTTP403 = "No autorizado";
	private static final String MSG_HTTP404 = "No se encuentra el recurso solicitado";
	private static final String MSG_HTTP500 = "Error interno del sistema";
	private static final String SWAGGER_POST_DESC = "Registro de un nuevo seguro";
	private static final String SWAGGER_GET_ALL_DESC = "Obtener detalle completo de seguros para un cliente";
	private static final String SWAGGER_GET_DESC = "Obtener detalle de un seguro para un cliente";
	

	@Autowired
    private PropertiesMsg propertiesMsg;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private InsuranceService insuranceService;
	
	
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
	public ResponseEntity<String> saveInsurance(@PathVariable("clientId")Long clientId, @RequestBody InsuranceDto insurance) throws ClientException, ResourceNotFoundException{	
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
		try {
			Client client = clientService.getClientByReference(clientId);
			if(client != null) {
				Insurance newInsurance = new Insurance(insurance.getName(),insurance.getRate());
				insuranceService.saveInsurance(client, newInsurance);
			}
			else {
				ResourceNotFoundException e = new ResourceNotFoundException();
				throw e;
			}
		}
		catch(ClientException e) {
			logger.error(e.getClass().toString() + " " + e.getMessage());
			throw e;
		}		
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return new ResponseEntity<>(Utils.encodeString(this.propertiesMsg.getHttp200()), HttpStatus.OK);
	}	
	
	@ApiOperation(value = SWAGGER_GET_ALL_DESC, notes = SWAGGER_GET_ALL_DESC)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = MSG_HTTP200, response = ClientInsuranceDto.class),
		@ApiResponse(code = 400, message = MSG_HTTP400, response = ExceptionResponse.class),
		@ApiResponse(code = 401, message = MSG_HTTP401, response = ExceptionResponse.class),
		@ApiResponse(code = 403, message = MSG_HTTP403, response = ExceptionResponse.class),
		@ApiResponse(code = 404, message = MSG_HTTP404, response = ExceptionResponse.class),
		@ApiResponse(code = 500, message = MSG_HTTP500, response = ExceptionResponse.class) 
		})
	@RequestMapping(value="/{id}", method=RequestMethod.GET)	
	public ResponseEntity<ClientInsuranceDto> getInsuranceFull(@PathVariable("id") Long id) throws ClientException, ResourceNotFoundException{	
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
		ClientInsuranceDto insurance = null;
		try {
			insurance = insuranceService.getInsuranceFull(id);
			if(insurance == null) {
				ResourceNotFoundException e = new ResourceNotFoundException();
				throw e;
			}
		}
		catch(ClientException e) {
			logger.error(e.getClass().toString() + " " + e.getMessage());
			throw e;
		}		
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return new ResponseEntity<ClientInsuranceDto>(insurance, HttpStatus.OK);
	}
	
	@ApiOperation(value = SWAGGER_GET_DESC, notes = SWAGGER_GET_DESC)
	@ApiResponses({ 
		@ApiResponse(code = 200, message = MSG_HTTP200, response = seguros.ti.gestionar.semilla.dbh2.dto.InsuranceDto.class),
		@ApiResponse(code = 400, message = MSG_HTTP400, response = ExceptionResponse.class),
		@ApiResponse(code = 401, message = MSG_HTTP401, response = ExceptionResponse.class),
		@ApiResponse(code = 403, message = MSG_HTTP403, response = ExceptionResponse.class),
		@ApiResponse(code = 404, message = MSG_HTTP404, response = ExceptionResponse.class),
		@ApiResponse(code = 500, message = MSG_HTTP500, response = ExceptionResponse.class) 
		})
	@RequestMapping(value="/{id}/simple", method=RequestMethod.GET)	
	public ResponseEntity<seguros.ti.gestionar.semilla.dbh2.dto.InsuranceDto> getInsurance(@PathVariable("id") Long id) throws ClientException, ResourceNotFoundException{	
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
		seguros.ti.gestionar.semilla.dbh2.dto.InsuranceDto insurance = null;
		try {
			insurance = insuranceService.getInsurance(id);
			if(insurance == null) {
				ResourceNotFoundException e = new ResourceNotFoundException();
				throw e;
			}
		}
		catch(ClientException e) {
			logger.error(e.getClass().toString() + " " + e.getMessage());
			throw e;
		}		
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return new ResponseEntity<seguros.ti.gestionar.semilla.dbh2.dto.InsuranceDto>(insurance, HttpStatus.OK);
	}
}
