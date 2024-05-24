package cps.DataAccess;

import cps.DataAccess.Models.WindSpeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WindSpeedRepository extends JpaRepository<WindSpeed,Long> {
}
