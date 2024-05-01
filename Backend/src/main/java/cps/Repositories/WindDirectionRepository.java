package cps.Repositories;

import cps.Repositories.Models.WindDirection;
import cps.Repositories.Models.WindSpeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WindDirectionRepository extends JpaRepository<WindDirection,Long> {
}
