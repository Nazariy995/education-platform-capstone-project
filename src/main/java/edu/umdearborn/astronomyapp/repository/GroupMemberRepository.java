package edu.umdearborn.astronomyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.umdearborn.astronomyapp.entity.GroupMember;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, String> {

}
