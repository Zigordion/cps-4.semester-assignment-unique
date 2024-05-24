package cps.DataAccess;

import cps.DataAccess.Models.SunPrTen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SunMinRepository extends JpaRepository<SunPrTen,Long> {
}
