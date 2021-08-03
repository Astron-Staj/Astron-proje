package com.project.Astron.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Astron.Model.User;


@Repository
public interface UserDataRepository extends JpaRepository<User, Long> {
	
	
	

}
