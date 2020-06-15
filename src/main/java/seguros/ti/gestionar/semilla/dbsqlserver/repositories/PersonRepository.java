package seguros.ti.gestionar.semilla.dbsqlserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import seguros.ti.gestionar.semilla.dbsqlserver.entities.Person;

public interface  PersonRepository extends JpaRepository<Person, Long> , PersonRepositoryCustom{
	
	
}
