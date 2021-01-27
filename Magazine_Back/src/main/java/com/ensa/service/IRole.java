package com.ensa.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.ensa.entities.Role;

@Component
public interface IRole {
	
	public Role save(Role objet);
	public Role getById(Long id);
	public List<Role> getAll();
	@Transactional
	void deleteAll();
	void deleteById(Long id);

}
