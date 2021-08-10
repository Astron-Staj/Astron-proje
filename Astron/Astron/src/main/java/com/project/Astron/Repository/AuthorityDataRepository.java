package com.project.astron.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.astron.model.Authority;


@Repository
public interface AuthorityDataRepository  extends JpaRepository<Authority, Long>{

}
