package seguros.ti.gestionar.semilla.dbh2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import seguros.ti.gestionar.semilla.dbh2.entities.Client;

public interface  ClientRepository extends JpaRepository<Client, Long> , ClientRepositoryCustom{
	
	
}
