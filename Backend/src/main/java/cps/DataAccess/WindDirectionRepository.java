package cps.DataAccess;

import cps.DataAccess.Models.WindDirection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WindDirectionRepository extends JpaRepository<WindDirection,Long> {
}
