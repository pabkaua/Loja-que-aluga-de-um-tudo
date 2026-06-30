package com.loja.ui;

import com.loja.padraoFacade.interfaces.ILojaFacade;
import com.loja.model.Funcionario;
import com.loja.model.Cliente;
import com.loja.model.ContratoAluguel;
import java.time.LocalDate;
import java.util.Scanner;

public class MenuFuncionario {

    private final ILojaFacade facade;
    private final Funcionario usuarioLogado;
    private final Scanner scanner;

    public MenuFuncionario(ILojaFacade facade, Funcionario usuarioLogado) {
        this.facade = facade;
        this.usuarioLogado = usuarioLogado;
        this.scanner = new Scanner(System.in);
    }

    public void exibir() {
        boolean ativo = true;
        while (ativo) {
            System.out.println("\nPAINEL OPERACIONAL (FUNCIONÁRIO): " + usuarioLogado.getNome().toUpperCase());
            System.out.println("1 - Registrar Novo Aluguel");
            System.out.println("2 - Processar Devolução de Item");
            System.out.println("3 - Cadastrar Novo Cliente");
            System.out.println("4 - Emitir Relatório de Itens Alugados");
            System.out.println("5 - Quitar uma Multa de Cliente");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            String opcao = lerEntrada("");

            if (opcao.equals("1")) {
                registrarAluguel();
            } else if (opcao.equals("2")) {
                processarDevolucao();
            } else if (opcao.equals("3")) {
                cadastrarCliente();
            } else if (opcao.equals("4")) {
                emitirRelatorios();
            } else if (opcao.equals("5")) {
                quitarMulta();
            } else if (opcao.equals("0")) {
                System.out.println("Saindo...");
                ativo = false;
            } else {
                System.out.println("Opção inválida!");
            }
        }
    }

    public void registrarAluguel() {
        System.out.println("\nREGISTRAR NOVO ALUGUEL");
        String clienteId = lerEntrada("ID do Cliente: ");
        String itemId = lerEntrada("ID do Item: ");
        
        // 7 dias de prazo inicial para devolução
        LocalDate dataRetirada = LocalDate.now();
        LocalDate dataPrevistoDevolucao = dataRetirada.plusDays(7);

        try {
            ContratoAluguel contrato = facade.registrarAluguel(clienteId, itemId, dataRetirada, dataPrevistoDevolucao);
            System.out.println("Sucesso! Contrato firmado com o ID: " + contrato.getId());
        } catch (RuntimeException e) {
            System.out.println("Erro ao abrir aluguel: " + e.getMessage());
        }
    }

    public void processarDevolucao() {
        System.out.println("\nPROCESSAR DEVOLUÇÃO");
        String contratoId = lerEntrada("Digite o ID do Contrato de Aluguel: ");
        try {
            ContratoAluguel contrato = facade.processarDevolucao(contratoId);
            System.out.println("Devolução processada com sucesso! Contrato ID: " + contrato.getId() + " finalizado.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao processar encerramento de contrato: " + e.getMessage());
        }
    }

    public void cadastrarCliente() {
        System.out.println("\nCADASTRO DE NOVO CLIENTE");
        String id = lerEntrada("ID: ");
        String nome = lerEntrada("Nome Completo: ");
        String email = lerEntrada("E-mail: ");
        String senha = lerEntrada("Senha de Acesso: ");

        Cliente novoCliente = new Cliente(id, nome, email, senha);
        try {
            facade.cadastrarCliente(novoCliente);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Falha ao salvar cliente: " + e.getMessage());
        }
    }

    public void emitirRelatorios() {
        System.out.println("\nRELATÓRIO DE ITENS ALUGADOS");
        try {
            String relatorio = facade.gerarRelatorioItensAlugados();
            System.out.println(relatorio);
        } catch (RuntimeException e) {
            System.out.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    public void quitarMulta() {
        System.out.println("\nQUITAR MULTA FINANCEIRA");
        String multaId = lerEntrada("Digite o ID da Multa a ser quitada: ");
        try {
            facade.quitarMulta(multaId);
            System.out.println("Sucesso! A multa foi alterada para QUITADA.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao dar baixa na multa: " + e.getMessage());
        }
    }

    public String lerEntrada(String mensagem) {
        if (!mensagem.isEmpty()) {
            System.out.print(mensagem);
        }
        return scanner.nextLine();
    }
}