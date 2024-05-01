package cps.Repositories;

import cps.Repositories.Models.Temperature;
import cps.Repositories.Models.WindSpeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WindSpeedRepository extends JpaRepository<WindSpeed,Long> {
}
