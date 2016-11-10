package edu.umdearborn.astronomyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.umdearborn.astronomyapp.entity.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, String> {

}
