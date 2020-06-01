package seguros.ti.gestionar.semilla.dbh2.servicesImpl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import seguros.ti.gestionar.semilla.dbh2.dto.ClientInsuranceDto;
import seguros.ti.gestionar.semilla.dbh2.entities.Client;
import seguros.ti.gestionar.semilla.dbh2.entities.Insurance;
import seguros.ti.gestionar.semilla.dbh2.repositories.InsuranceRepository;
import seguros.ti.gestionar.semilla.dbh2.services.InsuranceService;

@Service
public class InsuranceServiceImpl implements InsuranceService{

	private static final Logger logger = LoggerFactory.getLogger(InsuranceServiceImpl.class.getSimpleName());
	
	@Autowired
	private InsuranceRepository insuranceRepository;
	
	
	@Override
	@Transactional
	public void saveInsurance(Client client, Insurance insurance) throws ClientException {
		try {
			insurance.setClient(client);	
			insuranceRepository.save(insurance);	
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ClientException();
		}
	}
	
	@Override
	public seguros.ti.gestionar.semilla.dbh2.dto.InsuranceDto getInsurance(Long insuranceId) throws ClientException {
		try {
			
			Optional<Insurance> insurance = insuranceRepository.findById(insuranceId);
			if(!insurance.isPresent()) {
				return null;
			}
			seguros.ti.gestionar.semilla.dbh2.dto.InsuranceDto result = new seguros.ti.gestionar.semilla.dbh2.dto.InsuranceDto();
			Insurance insuranceObj = insurance.get();
			result.setId(insuranceObj.getId());
			result.setName(insuranceObj.getName());
			result.setRate(insuranceObj.getRate());
			return result;
			
		}catch(Exception e) {
			logger.error(e.getMessage());
			throw new ClientException();
		}
	}
	
	@Override
	@Transactional
	public ClientInsuranceDto getInsuranceFull(Long insuranceId) throws ClientException {
		try {
			ClientInsuranceDto clientInsuranceDto = insuranceRepository.findByInsuranceId(insuranceId);
			return clientInsuranceDto;

		}catch(Exception e) {
			logger.error(e.getMessage());
			throw new ClientException();
		}
	}
}
