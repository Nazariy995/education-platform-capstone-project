package edu.umdearborn.astronomyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.umdearborn.astronomyapp.entity.ModuleGroup;

@Repository
public interface ModuleGroupRepository extends JpaRepository<ModuleGroup, String> {

}
