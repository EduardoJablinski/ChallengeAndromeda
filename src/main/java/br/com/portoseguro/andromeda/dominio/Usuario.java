package br.com.portoseguro.andromeda.dominio;

public class Usuario {
	
	private Long id;
    private String nome;
    private String endereco;
    private String cpfCnpj;
    private String email;
    private String telefone;


    public Usuario(String nome, String endereco, String cpfCnpj, String email, String telefone) {
        this.nome = nome;
        this.endereco = endereco;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.telefone = telefone;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String toString() {
        return "Nome: " + nome +
               "\nEndereço: " + endereco;
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
	
	public String getEmail() {
		return email;
	}

	public String getTelefone() {
		return telefone;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	
}

