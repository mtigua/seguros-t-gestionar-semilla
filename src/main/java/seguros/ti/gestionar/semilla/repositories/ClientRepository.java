package seguros.ti.gestionar.semilla.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import seguros.ti.gestionar.semilla.entities.Client;

public interface  ClientRepository extends JpaRepository<Client, Long> , ClientRepositoryCustom{
	
	
}
