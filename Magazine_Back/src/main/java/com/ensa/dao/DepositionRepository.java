package com.ensa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ensa.entities.Deposition;

@Repository
@Transactional
public interface DepositionRepository extends JpaRepository<Deposition, Long> {
	Deposition getById(Long id);
	
}
