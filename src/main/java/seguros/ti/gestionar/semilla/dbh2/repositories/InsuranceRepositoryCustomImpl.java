package seguros.ti.gestionar.semilla.dbh2.repositories;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import seguros.ti.gestionar.semilla.configuration.PropertiesSql;
import seguros.ti.gestionar.semilla.dbh2.entities.Insurance;
import seguros.ti.gestionar.semilla.configuration.PropertiesMsg;
import seguros.ti.gestionar.semilla.utils.SqlReader;
import seguros.ti.gestionar.semilla.utils.Utils;

@Repository
@Transactional
public class InsuranceRepositoryCustomImpl implements InsuranceRepositoryCustom{
	private static final Logger logger = LoggerFactory.getLogger(InsuranceRepositoryCustomImpl.class.getSimpleName());

	@Autowired
	@Qualifier("h2dbEntityManagerFactory")
	private  EntityManager entityManager;
	 
	@Autowired
    private PropertiesMsg propertiesMsg;
	
	@Autowired
	private PropertiesSql propertiesSql;

	@Override
	public Insurance findWithClientById(Long id) {
		EntityGraph<Insurance> graph = entityManager.createEntityGraph(Insurance.class);
		graph.addAttributeNodes("client");
		Map<String,Object> hints = new HashMap<String,Object>();
		hints.put("javax.persistence.loadgraph", graph);
		Insurance insurance = entityManager.find(Insurance.class, id, hints);
		return insurance;
	}	 
	
	@Override
	public seguros.ti.gestionar.semilla.dto.ClientInsuranceDto findByInsuranceId (Long id)  { 
		seguros.ti.gestionar.semilla.dto.ClientInsuranceDto clientInsurancesDto = null;
		try {
			logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
			String sqlQuery = SqlReader.readSql(propertiesSql.getFind_clients_insurances_by_insurance_id_filepath());	
			TypedQuery<seguros.ti.gestionar.semilla.dto.ClientInsuranceDto> typedQuery = entityManager.createQuery(sqlQuery, seguros.ti.gestionar.semilla.dto.ClientInsuranceDto.class);
			typedQuery.setParameter("id",id);
			clientInsurancesDto = typedQuery.getSingleResult();
			logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		} 
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		return clientInsurancesDto;
	}
	
}
