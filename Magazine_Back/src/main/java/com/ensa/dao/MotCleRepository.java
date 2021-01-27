package com.ensa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ensa.entities.MotCle;

@Repository
@Transactional
public interface MotCleRepository extends JpaRepository<MotCle, Long> {
	MotCle getById(Long id);

}
