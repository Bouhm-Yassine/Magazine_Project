package com.ensa.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ensa.entities.Role;
import com.ensa.service.IRole;

@RestController
public class RoleRestService {

	@Autowired
	IRole roleMetier;
	
//	@PostMapping("/roles")
//	public Role saveRole(@RequestBody Role obj) {
//		
//		return roleMetier.save(obj);
//
//	}
//	
//	@GetMapping("/roles")
//	public List<Role> getRoles(@PathVariable Long id){
//		return roleMetier.getAll();
//	}
//	
//	@DeleteMapping("/roles/{id}")
//	public void deleteRole(@PathVariable Long id){
//		roleMetier.deleteById(id);
//	}
//	
//	@PutMapping("/roles/{id}")
//	public Role updateRole(@PathVariable Long id,@RequestBody Role obj){
//		
//		obj.setId(id);
//		return roleMetier.save(obj);
//	}
//
//	@GetMapping("/roles/{id}")
//	public Role getRole(@PathVariable Long id){
//		return roleMetier.getById(id);
//	}
	
}
