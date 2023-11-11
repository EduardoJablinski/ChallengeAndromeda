package br.com.portoseguro.andromeda.infra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import br.com.portoseguro.andromeda.dominio.Usuario;

public class UsuarioDAO {

	private Connection conexao;
	
	public UsuarioDAO() {
		conexao = new ConnectionFactory().getConnection();
	}
	
	public void adicionar(Usuario usuario) {
		String sqlInsert = "INSERT INTO TB_ACS_USER (id_user, nome_user, cpf_user, endereco_user, email_user, telefone_user) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement comandoDeInsercao = conexao.prepareStatement(sqlInsert);
			comandoDeInsercao.setLong(1, obterProximoIdUsuario());
			comandoDeInsercao.setString(2, usuario.getNome());
			comandoDeInsercao.setString(3, usuario.getCpfCnpj());
			comandoDeInsercao.setString(4, usuario.getEndereco());
			comandoDeInsercao.setString(5, usuario.getEmail());
			comandoDeInsercao.setString(6, usuario.getTelefone());
			comandoDeInsercao.execute();
			comandoDeInsercao.close();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean realizarLogin(String nomeLogin) {
	    try {
	        String sqlSelect = "SELECT * FROM TB_ACS_USER WHERE cpf_user = ?";
	        PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
	        comandoDeSelecao.setString(1, nomeLogin); // Use PreparedStatement para evitar SQL Injection
	        ResultSet rs = comandoDeSelecao.executeQuery();

	        if (rs.next()) {
	            System.out.println("\nLogin bem-sucedido! Bem-vindo! CPF/CNPJ: " + nomeLogin + ".");
	            return true;
	        } else {
	            System.out.println("\nDados incorretos. Tente novamente.\n");
	            return false;
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}


	
	private Long obterProximoIdUsuario() {
		Long id = null;
		try {
			String sql = "SELECT SEQ_USERS_ID.NEXTVAL FROM DUAL";
			PreparedStatement comandoDeGeracao =
			conexao.prepareStatement(sql);
			ResultSet rs = comandoDeGeracao.executeQuery();
			while(rs.next()) {
				id = rs.getLong(1);
			}
			rs.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return id;
	}

	
	public void close() {
		try {
			if (conexao != null && !conexao.isClosed()) {
				conexao.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Long obterIdUsuarioPorLogin(String nomeLogin) {
	    try {
	        String sqlSelectId = "SELECT id_user FROM TB_ACS_USER WHERE cpf_user = ?";
	        PreparedStatement comandoDeSelecaoId = conexao.prepareStatement(sqlSelectId);
	        comandoDeSelecaoId.setString(1, nomeLogin);
	        ResultSet rs = comandoDeSelecaoId.executeQuery();

	        if (rs.next()) {
	            return rs.getLong("id_user");
	        } else {
	            return null;	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}

}

	



