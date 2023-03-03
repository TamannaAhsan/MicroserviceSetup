package com.tamanna.customerserver.repository.geos;

import com.tamanna.customerserver.entity.geo.Geo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoRepository extends JpaRepository<Geo, Long> {
}
