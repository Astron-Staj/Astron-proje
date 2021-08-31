package com.project.astron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.astron.model.User;


@Repository
public interface UserDataRepository extends JpaRepository<User, Long> {

	
}
