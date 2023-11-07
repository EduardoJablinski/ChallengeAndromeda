package br.com.portoseguro.andromeda.dominio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

	public class Veiculo {
	
	    private static List<Veiculo> veiculoCadastrado = new ArrayList<>();
	
	    private int numeroApolice;
	    private int tipoVeiculo;
	    private String modeloVeiculo;
	    private String anoVeiculo;
	    private int pesoVeiculo;
	    private String corVeiculo;
	    private String combustivelVeiculo;
	    private String usuario;
	    private String placaVeiculo;
	    
	    public Veiculo(int numeroApolice, int tipoVeiculo, String modeloVeiculo, String anoVeiculo, int pesoVeiculo, String corVeiculo, String combustivelVeiculo, String usuario, String placaVeiculo) {
	    	this.numeroApolice = numeroApolice;
	    	this.tipoVeiculo = tipoVeiculo;
	        this.modeloVeiculo = modeloVeiculo;
	        this.anoVeiculo = anoVeiculo;
	        this.pesoVeiculo = pesoVeiculo;
	        this.corVeiculo = corVeiculo;
	        this.combustivelVeiculo = combustivelVeiculo;
	        this.usuario = usuario;
	        this.placaVeiculo = placaVeiculo;
	    }
	    
		public Veiculo() {
			
		}
	    
	    public void setUsuario(String usuario) {
	        this.usuario = usuario;
	    }
	    
	    public String getUsuario() {
	        return usuario;

	    }
	    
	    public String getPlacaVeiculo() {
	        return placaVeiculo;

	    }
	    
		public void setPlacaVeiculo(String placaVeiculo) {
			this.placaVeiculo = placaVeiculo;
		}
	    
	    public String getModeloVeiculo() {
	        return modeloVeiculo;
	    }
	    
		public void setModeloVeiculo(String modeloVeiculo) {
			this.modeloVeiculo = modeloVeiculo;
		}
	    
	    public int getTipoVeiculo() {
	        return tipoVeiculo;
	    }
	    
		public void setTipoVeiculo(int tipoVeiculo) {
			this.tipoVeiculo = tipoVeiculo;
		}
	    
	    public int getNumeroApolice() {
	        return numeroApolice;
	    }

		public void setNumeroApolice(int numeroApolice) {
			this.numeroApolice = numeroApolice;
		}
	    
	    public String getAnoVeiculo() {
	        return anoVeiculo;
	    }

		public void setAnoVeiculo(String anoVeiculo) {
			this.anoVeiculo = anoVeiculo;
		}
	    
	    public int getPesoVeiculo() {
	        return pesoVeiculo;
	    }
	    
		public void setPesoVeiculo(int pesoVeiculo) {
			this.pesoVeiculo = pesoVeiculo;
		}
	    
	    public String getCorVeiculo() {
	        return corVeiculo;
	    }
	    
		public void setCorVeiculo(String corVeiculo) {
			this.corVeiculo = corVeiculo;
		}
	    
	    public String getCombustivelVeiculo() {
	        return combustivelVeiculo;
	    }
	    
		public void setCombustivelVeiculo(String combustivelVeiculo) {
			this.combustivelVeiculo = combustivelVeiculo;
		}
	    
	    
	    public void cadastrarVeiculo(Veiculo veiculo, String usuario) {
	        veiculo.setUsuario(usuario);
	        veiculoCadastrado.add(veiculo);
	        System.out.println("\nVeículo cadastrado com sucesso!\n");
	    }
	    
	    public void exibirVeiculosDoUsuario(String usuario) {
	        boolean encontrou = false;
	        for (Veiculo veiculo : veiculoCadastrado) {
	            if (veiculo.getUsuario().equals(usuario)) {
	                encontrou = true;
	                System.out.println("\nNúmero da Apólice: " + veiculo.getNumeroApolice());
	                System.out.println("Placa do Veículo: " + veiculo.getPlacaVeiculo());
	                System.out.println("Modelo do Veículo: " + veiculo.getModeloVeiculo());
	                System.out.println("Ano: " + veiculo.getAnoVeiculo());
	                System.out.println("Cor: " + veiculo.getCorVeiculo());
	                System.out.println("Combustível: " + veiculo.getCombustivelVeiculo());
	                System.out.println("Peso: " + veiculo.getPesoVeiculo());
	            }
	        }
	        if (!encontrou) {
	            System.out.println("\nVocê ainda não cadastrou nenhum veículo.\n");
	        }
	    }
}
	
	