package br.edu.ifrs.canoas.tads.lds.aulajpa.model.dao;

import java.util.List;

import org.fest.assertions.Assertions;
import org.junit.Test;

//import br.edu.ifrs.canoas.tads.lds.aulajpa.pojo.Usuario;

public class UsuarioDAOTest {

	//UsuarioDAO usuarioDAO = new UsuarioDAO();

	@Test
	public void testSalvaNovoUsuario() {
		/*
		// Cria usuario
		Usuario usuario = new Usuario("email.do@usuario.com", "senha123",
				"endereco");

		// salva no banco
		usuarioDAO.salva(usuario);

		// verifica se salvou
		Assertions.assertThat(usuario.getId()).isNotNull();
		*/

	}

	@Test
	public void testaBuscaTodosUsuarios() {
		/*
		// Cria usuario
		Usuario u1 = new Usuario();
		Usuario u2 = new Usuario();
		Usuario u3 = new Usuario("email", "senha", "endereco");

		usuarioDAO.salva(u1);
		usuarioDAO.salva(u2);
		usuarioDAO.salva(u3);

		List<Usuario> usuarios = usuarioDAO.busca();

		// Deve ter no mínimo 3 usuários no banco
		Assertions.assertThat(usuarios.size() >= 3);
		*/
	}

	@Test
	public void testaUsuarioPorEmail() {
		/*
		// Cria usuario
		usuarioDAO.salva(new Usuario("testaUsuarioPorEmail", "senha",
				"endereco"));

		Usuario usuarioDoBD = usuarioDAO.busca("testaUsuarioPorEmail").get(0);

		Assertions.assertThat(usuarioDoBD.getEmail())
				.as("testaUsuarioPorEmail");
		Assertions.assertThat(usuarioDoBD.getId()).isNotNull();
		/*
	}

	@Test
	public void testaAtualizaUsuario() {
		/*
		Usuario usuario = new Usuario("emailDeAtualizacao", "senha", "endereco");

		// Cria usuario
		usuarioDAO.salva(usuario);

		Assertions.assertThat(usuario.getId()).isNotNull();
		Assertions.assertThat(usuario.getEmail()).as("emailDeAtualizacao");

		usuario.setEmail("agora_mudou_o_email");

		Usuario novoUsuarioRecuperadoDoBanco = usuarioDAO
				.busca(usuario.getId());
		Assertions.assertThat(novoUsuarioRecuperadoDoBanco.getEmail()).as(
				"agora_mudou_o_email");
		*/
	}

	@Test
	public void testaRemoveUsuario() {
		/*
		Usuario usuario = new Usuario("emailDeExclusao", "senha", "endereco");
		usuarioDAO.salva(usuario);

		// verifica se salvou com sucesso
		Assertions.assertThat(usuario.getId()).isNotNull();

		// remove
		usuarioDAO.remove(usuario.getId());

		// remove
		Assertions.assertThat(usuarioDAO.busca(usuario.getId())).isNull(); // VERIFICA
																			// SE
		*/																	// REMOVEU
																			// COM
																			// SUCESSO
	}

}
