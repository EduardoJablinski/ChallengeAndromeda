package br.com.portoseguro.andromeda.service;

import br.com.portoseguro.andromeda.dominio.Correios;
import br.com.portoseguro.andromeda.dominio.Endereco;
import br.com.portoseguro.andromeda.dominio.Mensageiro;
import br.com.portoseguro.andromeda.dominio.Usuario;

public class UsuarioService {

	private Mensageiro mensageiro;
	private Correios correios;
	
	public UsuarioService(Mensageiro mensageiro, Correios correios) {
		this.mensageiro = mensageiro;
		this.correios = correios;
	}
	
	public void adicionar(Usuario usuario) {
		Endereco endereco = correios.buscarEnderecoPor(usuario.getCep());
		usuario.atualizarEndereco(endereco);
		mensageiro.enviarMensagemDeBoasVindasPara(usuario);
	}
}
