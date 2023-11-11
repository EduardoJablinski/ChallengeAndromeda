package br.com.portoseguro.andromeda;

import java.util.Scanner;
import br.com.portoseguro.andromeda.dominio.Modal;
import br.com.portoseguro.andromeda.dominio.Usuario;
import br.com.portoseguro.andromeda.dominio.Veiculo;
import br.com.portoseguro.andromeda.infra.dao.UsuarioDAO;
import br.com.portoseguro.andromeda.infra.dao.VeiculoDAO;

import java.util.ArrayList;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Random random = new Random();
	
		System.out.println("----------------------------------------------------------------------");
		System.out.println("Bem vindo ao Sistema Andrômeda, o serviço de suporte virtual da Porto!");
		System.out.println("----------------------------------------------------------------------\n");
	    boolean finalizar = false;
	    boolean logado = false;
	    boolean logadoModal = false;
	    String usuarioLogado = "";     
	    Veiculo veiculo = new Veiculo(1,"", 1, 1, "", "", "", "");
	    
	
        while (!finalizar) {
       	 if (!logado) {
       		 System.out.println("Selecione a opção desejada:\n1 - Cadastro\n2 - Login\n3 - Solicitar um Guincho\n0 - Sair");
				
				int resposta = scanner.nextInt();
				scanner.nextLine(); 
			
	            switch (resposta) {
		            case 1:
		            	System.out.println("\nOlá, aqui você pode adicionar uma nova conta!");
						String senhaPf = null, nomePf = null, enderecoPf = null, cpfCnpj = null, emailPf = null, telefonePf = null;
						System.out.println("Digite o seu CPF ou CNPJ (Apenas Números):");
						cpfCnpj = scanner.nextLine();
						System.out.println("Digite o seu primeiro e último nome:");
						nomePf = scanner.nextLine();
						System.out.println("Informe o seu endereço atual:");
						enderecoPf = scanner.nextLine();
						System.out.println("Informe o seu email:");
						emailPf = scanner.nextLine();
						System.out.println("Informe o seu telefone:");
						telefonePf = scanner.nextLine();
						Usuario usuario = new Usuario(nomePf, enderecoPf, cpfCnpj, emailPf, telefonePf);
						UsuarioDAO  usuarioDao = new UsuarioDAO();
						System.out.println("\nUsuário cadastrado com sucesso!");
						usuarioDao.adicionar(usuario);
						break;

		            case 2: {
                       System.out.println("\nDigite o CPF ou CNPJ:");
                       String nomeLogin = scanner.nextLine();
                       UsuarioDAO  usuarioLogin = new UsuarioDAO();
                       logado = usuarioLogin.realizarLogin(nomeLogin);
                       usuarioLogado = nomeLogin;
                       break; } 
		            
		            case 3: {
		            	
		            	System.out.println("\nPara continuarmos com a solicitação, digite o seu CPF ou CNPJ:");
		            	String nomeLogin = scanner.nextLine();
		            	System.out.println("\nDigite o número da apólice:");
		            	int numeroApolice = scanner.nextInt();
                        scanner.nextLine();
		            	VeiculoDAO  usuarioLogin = new VeiculoDAO();
		            	logadoModal = usuarioLogin.realizarLoginGuincho(nomeLogin, numeroApolice);
		            		if (logadoModal) {
		            			System.out.println("\nPara continuarmos com a solicitação, digite o endereço da ocorrência:");
		            			String enderecoOcorrencia = scanner.nextLine();
		                        System.out.println("\nSelecione o tipo de ocorrência: ");
		                        System.out.println("1 - Acidente de trânsito");
		                        System.out.println("2 - Falha operacional");
		                        int tipoOcorrencia = 0;
		                        boolean categoriaOcorrenciaTrue = false;
		                        while (!categoriaOcorrenciaTrue) {
		                            System.out.print("Digite o número correspondente: ");
		                            tipoOcorrencia = scanner.nextInt();
		                            scanner.nextLine();
		                            if (tipoOcorrencia >= 1 && tipoOcorrencia <= 2) {
		                            	categoriaOcorrenciaTrue = true;
		                            } else {
		                                System.out.println("Por favor, digite um número de 1 a 2.");
		                            }
		                        }
		                        System.out.println("\nO veículo está em local de difícil acesso?");
		                        System.out.println("1 - Sim");
		                        System.out.println("2 - Não");
		                        int acessoVeiculo = 0;
		                        boolean categoriaAcessoTrue = false;
		                        while (!categoriaAcessoTrue) {
		                            System.out.print("Digite o número correspondente: ");
		                            acessoVeiculo = scanner.nextInt();
		                            scanner.nextLine();
		                            if (acessoVeiculo >= 1 && acessoVeiculo <= 2) {
		                            	categoriaAcessoTrue = true;
		                            } else {
		                                System.out.println("Por favor, digite um número de 1 a 2.");
		                            }
		                        }
		                        System.out.println("\nO veículo está carregando carga pesada?");
		                        System.out.println("1 - Sim");
		                        System.out.println("2 - Não");
		                        int cargaVeiculo = 0;
		                        boolean categoriaCargaTrue = false;
		                        while (!categoriaCargaTrue) {
		                            System.out.print("Digite o número correspondente: ");
		                            cargaVeiculo = scanner.nextInt();
		                            scanner.nextLine();
		                            if (cargaVeiculo >= 1 && cargaVeiculo <= 2) {
		                            	categoriaCargaTrue = true;
		                            } else {
		                                System.out.println("Por favor, digite um número de 1 a 2.");
		                            }
		                        }
		            			System.out.println("\nDigite um telefone para contato:");
		            			String telefoneContato = scanner.nextLine();
		            			System.out.println("\nInsira uma breve descrição do ocorrido:");
		            			String descricao = scanner.nextLine();
		            			Modal modal = new Modal(nomeLogin, numeroApolice, enderecoOcorrencia, tipoOcorrencia, telefoneContato, descricao, acessoVeiculo, cargaVeiculo);
		            			System.out.println("\nPor favor, verifique as informações:\n");
		                        modal.tipoGuincho(modal);
		            			System.out.println("\nDados Confirmados?");
	                            System.out.println("1 - Sim");
	                            System.out.println("2 - Não");
		            			int confirmacaoDados = scanner.nextInt();
		        				scanner.nextLine(); 
	                            if (confirmacaoDados >= 1 && confirmacaoDados <= 2) {
	                        	    if (confirmacaoDados == 1) {
	                        	    	System.out.println("\nDados Confirmados. O guincho será enviado para o endereço indicado, demais atualizações serão enviadas para o telefone cadastrado. Obrigado pelo contato!\n");
	                        	    	break;
	                        	    } else {
	                        	    	System.out.println("\nDados não confirmados pelo usuário. Realize a solicitação novamente.\n");
	                        	    	break;
	                        	    }
	                            } else {
	                                System.out.println("Por favor, digite um número de 1 a 2.");
	                            }
	                        }
		            		else {
		            	break; } }
		            
		            
		            case 0:
		                finalizar = true;
		                break;
		            default:
		                System.out.println("\nOpção inválida. Tente novamente.\n");
		                break;

	            }} else {
	        	
	                System.out.println("\nSelecione uma opção:");
	                System.out.println("1 - Realizar cadastro de um veículo no Sistema");
	                System.out.println("2 - Verificar veículos cadastrados");
	                System.out.println("3 - Fazer logout");
	                System.out.println("0 - Sair");
	
	                int opcao = scanner.nextInt();
	                scanner.nextLine(); 
	                
	                switch (opcao) {
	                case 1:
	                    boolean cadastrarOutroVeiculo = true;
	                    while (cadastrarOutroVeiculo) {
	                        System.out.println("\nPara continuarmos, precisamos de algumas informações sobre o veículo:");
	                        System.out.println("Qual a placa do Veículo?");
	                        String placaVeiculo = scanner.nextLine();
	                        System.out.println("Qual o ano do Veículo?");
	                        int anoVeiculo = scanner.nextInt();
	                        scanner.nextLine();
	                        System.out.println("Qual a condição do Veículo?");
	                        String condicaoVeiculo = scanner.nextLine();
	                        System.out.println("Qual o peso aproximado do Veiculo? (Em kg.Apenas números)");
	                        int pesoVeiculo = scanner.nextInt();
	                        scanner.nextLine();
	                        System.out.println("Qual a cor do Veiculo?");
	                        String corVeiculo = scanner.nextLine();
	                        System.out.println("Qual o tipo de Combustível do Veiculo?");
	                        String combustivelVeiculo = scanner.nextLine();

	                        VeiculoDAO veiculoDao = new VeiculoDAO();
	                        Long numeroApolice = VeiculoDAO.obterProximoNumeroApolice();
	                        System.out.println("Número da Apólice: " + numeroApolice);
	                        veiculo.setNumeroApolice(numeroApolice);  // Adiciona o número da apólice ao veículo
	                        veiculo = new Veiculo(numeroApolice, condicaoVeiculo, anoVeiculo, pesoVeiculo, corVeiculo, combustivelVeiculo, usuarioLogado, placaVeiculo);

	                        veiculoDao.adicionar(veiculo, usuarioLogado);  // Adiciona o segundo parâmetro (usuarioLogado)
	                        
	                        boolean opcaoValida = false;
	                        while (!opcaoValida) {
	                            System.out.println("\nDeseja cadastrar outro veículo?");
	                            System.out.println("1 - Sim");
	                            System.out.println("2 - Não");
	                            int opcaoCadastrarOutro = scanner.nextInt();
	                            scanner.nextLine();

	                            if (opcaoCadastrarOutro == 2) {
	                            	cadastrarOutroVeiculo = false;
	                                opcaoValida = true;
	                            } else if (opcaoCadastrarOutro == 1) {
	                                opcaoValida = true;
	                            } else {
	                                System.out.println("\nOpção inválida. Digite 1 para Sim ou 2 para Não.\n");
	                            }
	                        }
	                    }
	                    break;

		                    
		                case 2:
		                    VeiculoDAO veiculoDAO = new VeiculoDAO();
		                    ArrayList<Veiculo> veiculosDoUsuario = veiculoDAO.listarTodos(usuarioLogado);

		                    if (veiculosDoUsuario.isEmpty()) {
		                        System.out.println("\nVocê não possui veículos cadastrados.\n");
		                    } else {
		                        System.out.println("\nVeículos cadastrados no seu nome:");
		                        for (Veiculo v : veiculosDoUsuario) {
		                            System.out.println("Número da Apólice: " + v.getNumeroApolice());
		                            System.out.println("Modelo do Veículo: " + v.getModeloVeiculo());
		                            System.out.println("Ano: " + v.getAnoVeiculo());
		                            System.out.println("Cor: " + v.getCorVeiculo());
		                            System.out.println("Combustível: " + v.getCombustivelVeiculo());
		                            System.out.println("Peso: " + v.getPesoVeiculo());
		                            System.out.println("----------------------------");
		                        }
		                    }
		                    break;

		                    
	                    case 3:
	                        logado = false;
	                        usuarioLogado = "";
	                        break;

	                        
	                    case 0:
	                        finalizar = true;
	                        break;
	                        
	                    default:
	                        System.out.println("\nOpção inválida. Tente novamente.\n");
	                        break;            
}}}
        scanner.close();
}}
