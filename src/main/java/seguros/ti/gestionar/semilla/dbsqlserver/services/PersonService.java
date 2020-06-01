package seguros.ti.gestionar.semilla.dbsqlserver.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import seguros.ti.gestionar.semilla.dbsqlserver.dto.PersonDto;
import seguros.ti.gestionar.semilla.dbsqlserver.entities.Person;
import seguros.ti.gestionar.semilla.dbsqlserver.servicesImpl.PersonException;

public interface PersonService {

	public List<PersonDto> getPersons()throws PersonException;

	public PersonDto getPerson(Long id) throws PersonException;
	
	public void savePerson(Person person) throws PersonException;

	public void updatePerson(Person person) throws PersonException;

	public void deletePerson(Long id) throws PersonException;
	
	public Page<PersonDto> getPersonList(Pageable pageable) throws PersonException;
	
	
}
