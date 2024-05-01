package cps.Repositories;

import cps.Repositories.Models.SunPrTen;
import cps.Repositories.Models.WindSpeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SunMinRepository extends JpaRepository<SunPrTen,Long> {
}
