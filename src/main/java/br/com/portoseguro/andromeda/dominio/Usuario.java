package br.com.portoseguro.andromeda.dominio;

public class Usuario {
	
    private String senha;
    private String nome;
    private String endereco;
    private String cpfCnpj;

    public Usuario(String senha, String nome, String endereco, String cpfCnpj) {
        this.senha = senha;
        this.nome = nome;
        this.endereco = endereco;
        this.cpfCnpj = cpfCnpj;
    }
    
    public String toString() {
        return "Nome: " + nome +
               "\nEndereço: " + endereco;
    }

    public String getSenha() {
        return senha;
    }

	public String getNome() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
}

