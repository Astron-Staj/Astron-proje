package com.project.astron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.astron.model.Authority;


@Repository
public interface AuthorityDataRepository  extends JpaRepository<Authority, Long>{

	Authority findByName(String name);
//	Authority update(Authority auth);
}
