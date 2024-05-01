package cps.Repositories;

import cps.Repositories.Models.Rain;
import cps.Repositories.Models.WindSpeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RainRepository extends JpaRepository<Rain,Long> {
}
