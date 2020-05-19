package seguros.ti.gestionar.semilla.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import seguros.ti.gestionar.semilla.entities.Insurance;

public interface  InsuranceRepository extends JpaRepository<Insurance, Long>, InsuranceRepositoryCustom{

}
