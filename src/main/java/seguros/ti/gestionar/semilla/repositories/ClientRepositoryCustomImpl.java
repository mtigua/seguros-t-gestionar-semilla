package seguros.ti.gestionar.semilla.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import seguros.ti.gestionar.semilla.configuration.PropertiesMsg;
import seguros.ti.gestionar.semilla.configuration.PropertiesSql;
import seguros.ti.gestionar.semilla.entities.Client;
import seguros.ti.gestionar.semilla.utils.SqlReader;
import seguros.ti.gestionar.semilla.utils.Utils;

@Repository
@Transactional
public class ClientRepositoryCustomImpl implements ClientRepositoryCustom{
	private static final Logger logger = LoggerFactory.getLogger(ClientRepositoryCustomImpl.class.getSimpleName());

	@Autowired
	private  EntityManager entityManager;
	
	@Autowired
    private PropertiesMsg propertiesMsg;
	
	@Autowired
    private PropertiesSql propertiesSql;

	@Override
	public List<seguros.ti.gestionar.semilla.dto.ClientInsuranceDto> findByIdAndRate(Long id, Double rate) {
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
		String sqlQuery = SqlReader.readSql(propertiesSql.getFind_clients_insurances_by_id_and_rate_filepath());	
		TypedQuery<seguros.ti.gestionar.semilla.dto.ClientInsuranceDto> typedQuery = entityManager.createQuery(sqlQuery, seguros.ti.gestionar.semilla.dto.ClientInsuranceDto.class);
		typedQuery.setParameter("id",id);
		typedQuery.setParameter("rate",rate);
		List<seguros.ti.gestionar.semilla.dto.ClientInsuranceDto> clientsInsurancesDtos = typedQuery.getResultList();
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return clientsInsurancesDtos;
	}
	
	@Override
	public Client findWithInsurancesById(Long id) {
		EntityGraph<Client> graph = entityManager.createEntityGraph(Client.class);
		graph.addAttributeNodes("insurances");
		Map<String,Object> hints = new HashMap<String,Object>();
		hints.put("javax.persistence.loadgraph", graph);
		Client client = entityManager.find(Client.class, id, hints);
		return client;
	}
	
	@Override
	public List<seguros.ti.gestionar.semilla.dto.ClientDto> findAllSingleDto() {
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
		String sqlQuery = SqlReader.readSql(propertiesSql.getFind_clients_single_filepath());
		TypedQuery<seguros.ti.gestionar.semilla.dto.ClientDto> typedQuery = entityManager.createQuery(sqlQuery, seguros.ti.gestionar.semilla.dto.ClientDto.class);
		List<seguros.ti.gestionar.semilla.dto.ClientDto> clientsDto = typedQuery.getResultList();
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return clientsDto;
	}
	
	
}
