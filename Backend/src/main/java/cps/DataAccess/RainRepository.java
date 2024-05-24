package cps.DataAccess;

import cps.DataAccess.Models.Rain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RainRepository extends JpaRepository<Rain,Long> {
}
