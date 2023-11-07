package br.com.portoseguro.andromeda.infra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.portoseguro.andromeda.dominio.Veiculo;

public class VeiculoDAO {

	private Connection conexao;
	
	public VeiculoDAO() {
		conexao = new ConnectionFactory().getConnection();
	}
	
	public void adicionar(Veiculo veiculo) {
		String sqlInsert = "INSERT INTO veiculo (cpfCnpj, numeroApolice, tipoVeiculo, modeloVeiculo, anoVeiculo, pesoVeiculo, corVeiculo, combustivelVeiculo, placaVeiculo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement comandoDeInsercao = conexao.prepareStatement(sqlInsert);
			comandoDeInsercao.setString(1, veiculo.getUsuario());
			comandoDeInsercao.setInt(2, veiculo.getNumeroApolice());
			comandoDeInsercao.setInt(3, veiculo.getTipoVeiculo());
			comandoDeInsercao.setString(4, veiculo.getModeloVeiculo());
			comandoDeInsercao.setString(5, veiculo.getAnoVeiculo());
			comandoDeInsercao.setInt(6, veiculo.getPesoVeiculo());
			comandoDeInsercao.setString(7, veiculo.getCorVeiculo());
			comandoDeInsercao.setString(8, veiculo.getCombustivelVeiculo());
			comandoDeInsercao.setString(9, veiculo.getPlacaVeiculo());
			comandoDeInsercao.execute();
			comandoDeInsercao.close();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public ArrayList<Veiculo> listarTodos(String nomeLogin) {
		ArrayList<Veiculo> veiculos = new ArrayList<>();
		try {
			String sqlSelect = "SELECT * FROM veiculo WHERE cpfCnpj = '" + nomeLogin + "'";
			PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
			ResultSet rs = comandoDeSelecao.executeQuery();
			while(rs.next()) {
				Veiculo veiculo = new Veiculo();
				veiculo.setNumeroApolice(rs.getInt("numeroApolice"));
				veiculo.setModeloVeiculo(rs.getString("modeloVeiculo"));
				veiculo.setAnoVeiculo(rs.getString("anoVeiculo"));
				veiculo.setPesoVeiculo(rs.getInt("pesoVeiculo"));
				veiculo.setCorVeiculo(rs.getString("corVeiculo"));
				veiculo.setCombustivelVeiculo(rs.getString("combustivelVeiculo"));
				veiculo.setPlacaVeiculo(rs.getString("placaVeiculo"));
				veiculos.add(veiculo);
			}
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}	
		return veiculos;
	}
	
	public boolean realizarLoginGuincho(String nomeLogin, int numeroApolice) {
		try {
			String sqlSelect = "SELECT * FROM veiculo WHERE cpfCnpj = '" + nomeLogin + "' and numeroApolice = '" + numeroApolice + "';";
			PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
			ResultSet rs = comandoDeSelecao.executeQuery();
			
				if(rs.first())
				{
					System.out.println("\nDados encontrados! Bem-vindo!");
					return true;
			}
				else
				{
					System.out.println("\nDados não encontrados no sistema. Tente novamente.\n");
					return false;
			
		}}catch(SQLException e) {
			throw new RuntimeException(e);
		}	
	}
}
