package com.loja.padraoFacade;

import com.loja.padraoFacade.interfaces.ILojaFacade;
import com.loja.business.interfaces.*;
import com.loja.model.*;

import java.time.LocalDate;
import java.util.Map;

public class LojaFacade implements ILojaFacade{

    private final IUsuarioBusiness usuarioBusiness;
    private final IItemBusiness itemBusiness;
    private final ICategoriaBusiness categoriaBusiness;
    private final IFornecedorBusiness fornecedorBusiness;
    private final IContratoBusiness contratoBusiness;
    private final IMultaBusiness multaBusiness;

    public LojaFacade(IUsuarioBusiness usuarioBusiness, 
                    IItemBusiness itemBusiness,
                    ICategoriaBusiness categoriaBusiness,
                    IFornecedorBusiness fornecedorBusiness,
                    IContratoBusiness contratoBusiness,
                    IMultaBusiness multaBusiness){
        this.usuarioBusiness = usuarioBusiness;
        this.itemBusiness = itemBusiness;
        this.categoriaBusiness = categoriaBusiness;
        this.fornecedorBusiness = fornecedorBusiness;
        this.contratoBusiness = contratoBusiness;
        this.multaBusiness = multaBusiness;
    }
    /* =========================================================================
     * 1. RELATÓRIOS
     * ========================================================================= */

    @Override
    public String gerarRelatorioItensAlugados() {
        return contratoBusiness.gerarRelatorioItensAlugados();
    }

    @Override
    public String gerarRelatorioFaturamento(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null || inicio.isAfter(fim)) {
            throw new RuntimeException("Intervalo de datas inválido para geração de relatório de faturamento.");
        }
        return contratoBusiness.gerarRelatorioFaturamento(inicio, fim);
    }

    /* =========================================================================
     * 2. USUÁRIO (CLIENTE / FUNCIONÁRIO / ADM)
     * ========================================================================= */

    @Override
    public void cadastrarCliente(Cliente cliente) {
        if (cliente == null) throw new RuntimeException("Não é possível cadastrar um cliente nulo.");
        usuarioBusiness.cadastrarCliente(cliente);
    }

    @Override
    public void cadastrarFuncionario(Funcionario funcionario) {
        if (funcionario == null) throw new RuntimeException("Não é possível cadastrar um funcionário nulo.");
        usuarioBusiness.cadastrarFuncionario(funcionario);
    }

    @Override
    public void cadastrarAdm(Administrador adm) {
        if (adm == null) throw new RuntimeException("Não é possível cadastrar um administrador nulo.");
        usuarioBusiness.cadastrarAdm(adm);
    }

    @Override
    public Usuario buscarUsuario(String id) {
        if (id == null || id.trim().isEmpty()) throw new RuntimeException("ID inválido para busca de usuário.");
        return usuarioBusiness.buscarUsuario(id);
    }

    @Override
    public Usuario autenticarUsuario(String email, String senha) {
        if (email == null || senha == null) throw new RuntimeException("Credenciais não podem ser nulas.");
        return usuarioBusiness.autenticarUsuario(email, senha);
    }

    @Override
    public Map<String, Usuario> listarUsuario() {
        return usuarioBusiness.listarUsuario();
    }

    @Override
    public Map<String, Usuario> listarUsuarioPorPerfil(String perfil) {
        if (perfil == null || perfil.trim().isEmpty()) throw new RuntimeException("Perfil de busca inválido.");
        return usuarioBusiness.listarUsuarioPorPerfil(perfil);
    }

    @Override
    public void atualizarUsuario(Usuario usuario) {
        if (usuario == null) throw new RuntimeException("Não é possível atualizar um usuário nulo.");
        usuarioBusiness.atualizarUsuario(usuario);
    }

    @Override
    public void desativarUsuario(String id) {
        if (id == null || id.trim().isEmpty()) throw new RuntimeException("ID inválido para desativação.");
        usuarioBusiness.desativarUsuario(id);
    }

    /* =========================================================================
     * 3. CONTRATO (ALUGUEL / DEVOLUÇÃO)
     * ========================================================================= */

    @Override
    public ContratoAluguel registrarAluguel(String clienteId, String itemId, LocalDate dataRetirada, LocalDate dataPrevDevolucao) {
        return contratoBusiness.registrarAluguel(clienteId, itemId, dataRetirada, dataPrevDevolucao);
    }

    @Override
    public ContratoAluguel buscarContrato(String id) {
        if (id == null || id.trim().isEmpty()) throw new RuntimeException("ID de contrato inválido.");
        return contratoBusiness.buscarContrato(id);
    }

    @Override
    public ContratoAluguel processarDevolucao(String contratoId) {
        if (contratoId == null || contratoId.trim().isEmpty()) throw new RuntimeException("ID inválido para processar devolução.");
        return contratoBusiness.processarDevolucao(contratoId);
    }

    @Override
    public Map<String, ContratoAluguel> listarContratosAtivos() {
        return contratoBusiness.listarContratosAtivos();
    }

    @Override
    public Map<String, ContratoAluguel> consultarHistoricoCliente(String clienteId) {
        if (clienteId == null || clienteId.trim().isEmpty()) throw new RuntimeException("ID de cliente inválido para busca de histórico.");
        return contratoBusiness.consultarHistoricoCliente(clienteId);
    }

    /* =========================================================================
     * 4. ITEM
     * ========================================================================= */

    @Override
    public void cadastrarItem(Item i) {
        if (i == null) throw new RuntimeException("Não é possível cadastrar um item nulo.");
        itemBusiness.cadastrarItem(i);
    }

    @Override
    public Item buscarItem(String id) {
        if (id == null || id.trim().isEmpty()) throw new RuntimeException("ID inválido para busca de item.");
        return itemBusiness.buscarItem(id);
    }

    @Override
    public Map<String, Item> listarItem() {
        return itemBusiness.listarItem();
    }

    @Override
    public Map<String, Item> listarItensDisponiveis() {
        return itemBusiness.listarItensDisponiveis();
    }

    @Override
    public Map<String, Item> listarItemPorCategoria(Categoria categoria) {
        if (categoria == null) throw new RuntimeException("Categoria informada inválida para filtragem.");
        return itemBusiness.listarItemPorCategoria(categoria);
    }

    @Override
    public Map<String, Item> listarItemPorStatus(String status) {
        if (status == null || status.trim().isEmpty()) throw new RuntimeException("Status informado inválido para filtragem.");
        return itemBusiness.listarItemPorStatus(status);
    }

    @Override
    public Map<String, Item> listarItemPorFornecedor(Fornecedor fornecedor) {
        if (fornecedor == null) throw new RuntimeException("Fornecedor informado inválido para filtragem.");
        return itemBusiness.listarItemPorFornecedor(fornecedor);
    }

    @Override
    public void atualizarItem(Item item) {
        if (item == null) throw new RuntimeException("Não é possível atualizar um item nulo.");
        itemBusiness.atualizarItem(item);
    }

    @Override
    public void deletarItem(String id) {
        if (id == null || id.trim().isEmpty()) throw new RuntimeException("ID inválido para deleção de item.");
        itemBusiness.deletarItem(id);
    }

    /* =========================================================================
     * 5. CATEGORIA
     * ========================================================================= */

    @Override
    public void cadastrarCategoria(Categoria c) {
        if (c == null) throw new RuntimeException("Não é possível cadastrar uma categoria nula.");
        categoriaBusiness.cadastrarCategoria(c);
    }

    @Override
    public Categoria buscarCategoria(String id) {
        if (id == null || id.trim().isEmpty()) throw new RuntimeException("ID inválido para busca de categoria.");
        return categoriaBusiness.buscarCategoria(id);
    }

    @Override
    public void atualizarCategoria(Categoria categoria) {
        if (categoria == null) throw new RuntimeException("Não é possível atualizar uma categoria nula.");
        categoriaBusiness.atualizarCategoria(categoria);
    }

    @Override
    public Map<String, Categoria> listarCategoria() {
        return categoriaBusiness.listarCategoria();
    }

    @Override
    public void deletarCategoria(String id) {
        if (id == null || id.trim().isEmpty()) throw new RuntimeException("ID inválido para exclusão de categoria.");
        categoriaBusiness.deletarCategoria(id);
    }

    /* =========================================================================
     * 6. FORNECEDOR
     * ========================================================================= */

    @Override
    public void cadastrarFornecedor(Fornecedor f) {
        if (f == null) throw new RuntimeException("Não é possível cadastrar um fornecedor nulo.");
        fornecedorBusiness.cadastrarFornecedor(f);
    }

    @Override
    public Fornecedor buscarFornecedor(String id) {
        if (id == null || id.trim().isEmpty()) throw new RuntimeException("ID inválido para busca de fornecedor.");
        return fornecedorBusiness.buscarFornecedor(id);
    }

    @Override
    public void atualizarFornecedor(Fornecedor fornecedor) {
        if (fornecedor == null) throw new RuntimeException("Não é possível atualizar um fornecedor nulo.");
        fornecedorBusiness.atualizarFornecedor(fornecedor);
    }

    @Override
    public Map<String, Fornecedor> listarFornecedor() {
        return fornecedorBusiness.listarFornecedor();
    }

    @Override
    public void deletarFornecedor(String id) {
        if (id == null || id.trim().isEmpty()) throw new RuntimeException("ID inválido para exclusão de fornecedor.");
        fornecedorBusiness.deletarFornecedor(id);
    }

    /* =========================================================================
     * 7. MULTA
     * ========================================================================= */

    @Override
    public void aplicarMulta(ContratoAluguel contrato) {
        if (contrato == null) throw new RuntimeException("Não é possível aplicar multa sobre um contrato nulo.");
        multaBusiness.aplicar(contrato); // Mapeado no Business como "aplicar"
    }

    @Override
    public void quitarMulta(String multaId) {
        if (multaId == null || multaId.trim().isEmpty()) throw new RuntimeException("ID inválido para quitação de multa.");
        multaBusiness.quitar(multaId);   // Mapeado no Business como "quitar"
    }

    @Override
    public boolean possuiMultaPendente(String clienteId) {
        if (clienteId == null || clienteId.trim().isEmpty()) return false;
        return multaBusiness.possuiMultaPendente(clienteId);
    }

    @Override
    public Map<String, Multa> listarMultaPorCliente(String clienteId) {
        if (clienteId == null || clienteId.trim().isEmpty()) throw new RuntimeException("ID de cliente inválido.");
        return multaBusiness.listarPorCliente(clienteId); // Mapeado no Business como "listarPorCliente"
    }

    @Override
    public Map<String, Multa> listarMulta() {
        return multaBusiness.listar(); // Mapeado no Business como "listar"
    }

    @Override
    public void deletarMulta(String id) {
        if (id == null || id.trim().isEmpty()) throw new RuntimeException("ID inválido para deleção de multa.");
        multaBusiness.deletarMulta(id); // Mapeado no Business como "deletarMulta"
    }
}