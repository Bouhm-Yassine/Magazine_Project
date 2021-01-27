package com.ensa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ensa.dao.RoleRepository;
import com.ensa.entities.Role;
import com.ensa.service.IRole;

@Service
public class RoleService implements IRole {

	@Autowired
	RoleRepository roleRepo;
	
	@Override
	public Role save(Role objet) {
		// TODO Auto-generated method stub
		return roleRepo.save(objet);
	}

	@Override
	public Role getById(Long id) {
		// TODO Auto-generated method stub
		return roleRepo.getById(id);
	}

	@Override
	public List<Role> getAll() {
		// TODO Auto-generated method stub
		return roleRepo.findAll();
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		roleRepo.deleteAll();

	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		roleRepo.deleteById(id);
	}

}
