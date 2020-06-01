package seguros.ti.gestionar.semilla.dbsqlserver.repositories;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import seguros.ti.gestionar.semilla.configuration.PropertiesMsg;
import seguros.ti.gestionar.semilla.configuration.PropertiesSql;
import seguros.ti.gestionar.semilla.dbsqlserver.dto.PersonDto;
import seguros.ti.gestionar.semilla.utils.SqlReader;
import seguros.ti.gestionar.semilla.utils.Utils;

@Repository
@Transactional
public class PersonRepositoryCustomImpl implements PersonRepositoryCustom{
	private static final Logger logger = LoggerFactory.getLogger(PersonRepositoryCustomImpl.class.getSimpleName());

	@Autowired
	@Qualifier("sqlserverEntityManagerFactory")
	private  EntityManager entityManager;
	
	@Autowired
    private PropertiesMsg propertiesMsg;
	
	@Autowired
    private PropertiesSql propertiesSql;

	
	@Override
	public List<PersonDto> findAllSingleDto() {
		
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_iniciando()));
		String sqlQuery = SqlReader.readSql(propertiesSql.getFind_persons_single_filepath());
		TypedQuery<PersonDto> typedQuery = entityManager.createQuery(sqlQuery, PersonDto.class);
		List<PersonDto> personsDto = typedQuery.getResultList();
		logger.info(Utils.encodeString(propertiesMsg.getLogger_info_finalizando()));
		return personsDto;
	}
	
	
}
