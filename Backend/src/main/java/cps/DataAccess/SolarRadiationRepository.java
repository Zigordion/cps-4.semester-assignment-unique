package cps.DataAccess;

import cps.DataAccess.Models.SolarRadiation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolarRadiationRepository extends JpaRepository<SolarRadiation,Long> {
}
