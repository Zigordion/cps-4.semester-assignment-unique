package cps.Repositories;

import cps.Repositories.Models.Humidity;
import cps.Repositories.Models.WindSpeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumidityRepository extends JpaRepository<Humidity,Long> {
}
