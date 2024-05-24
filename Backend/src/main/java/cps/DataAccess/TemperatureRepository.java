package cps.DataAccess;

import cps.DataAccess.Models.Temperature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperatureRepository extends JpaRepository<Temperature,Long> {
}
