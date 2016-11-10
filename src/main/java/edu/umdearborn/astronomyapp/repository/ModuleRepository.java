package edu.umdearborn.astronomyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.umdearborn.astronomyapp.entity.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, String> {

}
