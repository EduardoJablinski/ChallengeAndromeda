package br.com.portoseguro.andromeda.controller;

import br.com.portoseguro.andromeda.dominio.Usuario;
import br.com.portoseguro.andromeda.dominio.Veiculo;
import br.com.portoseguro.andromeda.infra.dao.UsuarioDAO;
import br.com.portoseguro.andromeda.infra.dao.VeiculoDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/usuarios")
public class UsuarioController {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private VeiculoDAO veiculoDAO = new VeiculoDAO();

    @GET
    @Path("/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVeiculosByUsuario(@PathParam("cpf") String cpf) {
        Long idUsuario = usuarioDAO.obterIdUsuarioPorLogin(cpf);

        if (idUsuario != null) {
            List<Veiculo> veiculos = veiculoDAO.listarTodos(idUsuario);

            if (veiculos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuário não possui veículos cadastrados")
                        .build();
            } else {
                List<VeiculoResponse> veiculosResponse = new ArrayList<>();
                for (Veiculo veiculo : veiculos) {
                    VeiculoResponse veiculoResponse = new VeiculoResponse();
                    veiculoResponse.setPlacaVeiculo(veiculo.getPlacaVeiculo());
                    veiculoResponse.setNumeroApolice(veiculo.getNumeroApolice());
                    veiculosResponse.add(veiculoResponse);
                }

                return Response.ok(veiculosResponse).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuário não encontrado")
                    .build();
        }
    }
    
    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarUsuario(UsuarioRequest usuarioRequest) {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            Usuario novoUsuario = new Usuario(
                usuarioRequest.getNome(),
                usuarioRequest.getEndereco(),
                usuarioRequest.getCpfCnpj(),
                usuarioRequest.getEmail(),
                usuarioRequest.getTelefone()
            );

            usuarioDAO.adicionar(novoUsuario);

            return Response.status(Response.Status.CREATED)
                    .entity("Usuário cadastrado com sucesso")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao cadastrar o usuário")
                    .build();
        }
    }


    public static class VeiculoResponse {
        private String placaVeiculo;
        private Long numeroApolice;


        public String getPlacaVeiculo() {
            return placaVeiculo;
        }

        public void setPlacaVeiculo(String placaVeiculo) {
            this.placaVeiculo = placaVeiculo;
        }

        public Long getNumeroApolice() {
            return numeroApolice;
        }

        public void setNumeroApolice(Long numeroApolice) {
            this.numeroApolice = numeroApolice;
        }
    }
    
    public static class UsuarioRequest {
        private String nome;
        private String endereco;
        private String cpfCnpj;
        private String email;
        private String telefone;
        
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
    }

}
