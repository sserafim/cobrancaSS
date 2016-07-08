package com.samuexx.cobrancaSS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.samuexx.cobrancaSS.model.Titulo;
import com.samuexx.cobrancaSS.repository.Titulos;

@Service
public class CadastroTituloService {

	@Autowired
	private Titulos titulos;

	public void salvar(Titulo titulo) {
		
		try {
			titulos.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data invalido!");

		}
	}
	
	public void excluir(Long codigo){
		titulos.delete(codigo);
	}
	


}
