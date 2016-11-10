package edu.umdearborn.astronomyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.umdearborn.astronomyapp.entity.PageItem;

@Repository
public interface PageItemRepository extends JpaRepository<PageItem, String> {

}
