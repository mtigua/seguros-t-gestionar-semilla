package seguros.ti.gestionar.semilla.dbh2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import seguros.ti.gestionar.semilla.dbh2.entities.Insurance;

public interface  InsuranceRepository extends JpaRepository<Insurance, Long>, InsuranceRepositoryCustom{

}
