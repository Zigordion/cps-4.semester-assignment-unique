package cps.DataAccess;

import cps.DataAccess.Models.CloudCoverage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CloudCoverageRepository extends JpaRepository<CloudCoverage,Long> {
}
