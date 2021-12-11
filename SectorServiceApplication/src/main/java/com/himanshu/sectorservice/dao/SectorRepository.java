package com.himanshu.sectorservice.dao;

import com.himanshu.sectorservice.model.Sector;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends MongoRepository<Sector, String>
{
	public Sector findByName(String sectorName);
}
