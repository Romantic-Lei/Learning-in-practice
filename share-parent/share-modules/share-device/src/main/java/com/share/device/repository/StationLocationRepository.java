package com.share.device.repository;

import com.share.device.domain.StationLocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationLocationRepository extends MongoRepository<StationLocation, String> {

    StationLocation getByStationId(Long stationId);

    void deleteByStationId(Long stationId);
}