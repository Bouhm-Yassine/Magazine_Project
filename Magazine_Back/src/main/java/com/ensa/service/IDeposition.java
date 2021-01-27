package com.ensa.service;

import java.text.ParseException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import com.ensa.entities.Deposition;
import com.ensa.entities.Intervenant;

@Component
public interface IDeposition {

	public Deposition save(Deposition obj);
	public List<Deposition> saveDeposition(List<Intervenant> coAuteur, Long id, Long idArticle) throws ParseException;
	public Deposition getById(Long id);
	public List<Deposition> getAll();
	@Transactional
	void deleteAll();
	void deleteById(Long id);
}
