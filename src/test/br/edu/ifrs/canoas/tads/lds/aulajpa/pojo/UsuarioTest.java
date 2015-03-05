package br.edu.ifrs.canoas.tads.lds.aulajpa.pojo;

import javax.persistence.EntityManager;

import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.edu.ifrs.canoas.tads.lds.aulajpa.model.util.EntityManagerUtil;

public class UsuarioTest{
	
	private EntityManager em;
	
	@Before
	public void setup(){
		em = EntityManagerUtil.getEM();
	}
	 

	@Test
	public void test01() {
		/*
		Usuario usuario = new Usuario();
		
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
		em.close();

		Assertions.assertThat(usuario.getId()).isNotNull();
		*/
	}
	
	@Test
	public void test02() {
		/*
		//construtor deve ter email, senha, endereco
		Usuario usuario = new Usuario("email.do@usuario.com", "Senha_Do_Usuario", "Endereco do Usuario");
		
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
		em.close();

		Assertions.assertThat(usuario.getId()).isNotNull();
		Assertions.assertThat(usuario.getEndereco()).as("Endereco do Usuario");
		*/
	}
	



}
