package seguros.ti.gestionar.semilla.dbsqlserver.servicesImpl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import seguros.ti.gestionar.semilla.dbsqlserver.dto.PersonDto;
import seguros.ti.gestionar.semilla.dbsqlserver.entities.Person;
import seguros.ti.gestionar.semilla.dbsqlserver.repositories.PersonRepository;
import seguros.ti.gestionar.semilla.dbsqlserver.services.PersonService;

import org.springframework.transaction.annotation.Transactional;
import java.util.function.Function;

@Service
public class PersonServiceImpl implements PersonService{
	
	private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class.getSimpleName());

	@Autowired
	private PersonRepository personRepository;	
	
	@Override
	@Transactional
	public List<PersonDto> getPersons() throws PersonException{
		try {
			List<PersonDto> persons = personRepository.findAllSingleDto();
			return persons;
			
		}catch(Exception e) {
			logger.error(e.getMessage());
			throw new PersonException();
		}
	}

	@Override
	@Transactional
	public PersonDto getPerson(Long id) throws PersonException {
		try {			
			Optional<Person> person = personRepository.findById(id);
			if(!person.isPresent()){
				return null;
			}
			else{
				PersonDto clientDto = new PersonDto();
				Person clientObj = person.get();
				clientDto.setId(clientObj.getId());
				clientDto.setRut(clientObj.getRut());
				clientDto.setFirstName(clientObj.getFirstName());
				clientDto.setLastName(clientObj.getLastName());
				return clientDto;
			}
		}
		catch(Exception e) {
			throw new PersonException();
		}
	}
	
	
	@Override
	public void savePerson(Person person) throws PersonException {
		try {			
			personRepository.save(person);
			
		}catch(Exception e) {			
			logger.error(e.getMessage());
			throw new PersonException();
		}
	}

	@Override
	public void updatePerson(Person person) throws PersonException {
		try {
			Optional<Person> clientBd= personRepository.findById(person.getId());
			if(clientBd.isPresent()){
				personRepository.save(person);
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			throw new PersonException();
		}
	}

	@Override
	public void deletePerson(Long id) throws PersonException {
		try {
			personRepository.deleteById(id);
			
		}catch(Exception e) {
			logger.error(e.getMessage());
			throw new PersonException();
		}
	}
	
	@Override
	@Transactional
	public Page<PersonDto> getPersonList(Pageable pageable) throws PersonException{
		try {
			Page<Person> pagePerson = personRepository.findAll(pageable);
			Page<PersonDto> pagePersontDto = pagePerson.map(new Function<Person, PersonDto>() {
			    @Override
			    public PersonDto apply(Person person) {
			    	PersonDto clientDto = new PersonDto();
			        clientDto.setId(person.getId());
			        clientDto.setFirstName(person.getFirstName());
			        clientDto.setLastName(person.getLastName());
			        clientDto.setRut(person.getRut());
			        return clientDto;
			    }
			});
			return pagePersontDto; 
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			throw new PersonException();
		}
	}
	


	
}
