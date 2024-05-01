package cps.Repositories;

import cps.Repositories.Models.SolarRadiation;
import cps.Repositories.Models.WindSpeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolarRadiationRepository extends JpaRepository<SolarRadiation,Long> {
}
