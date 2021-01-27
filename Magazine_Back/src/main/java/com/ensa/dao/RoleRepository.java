package com.ensa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ensa.entities.Role;
import com.ensa.entities.Utilisateur;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role getById(Long id);
	public Role findByRole(String role);

}
