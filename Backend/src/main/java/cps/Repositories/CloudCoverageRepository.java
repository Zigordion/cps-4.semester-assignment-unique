package cps.Repositories;

import cps.Repositories.Models.CloudCoverage;
import cps.Repositories.Models.WindSpeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CloudCoverageRepository extends JpaRepository<CloudCoverage,Long> {
}
