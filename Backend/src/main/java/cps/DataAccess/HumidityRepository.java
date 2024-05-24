package cps.DataAccess;

import cps.DataAccess.Models.Humidity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumidityRepository extends JpaRepository<Humidity,Long> {
}
