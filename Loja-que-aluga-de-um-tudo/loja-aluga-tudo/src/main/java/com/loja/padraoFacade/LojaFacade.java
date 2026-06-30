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
        resolverDependencias();
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
        usuarioBusiness.cadastrar(cliente);
    }

    @Override
    public void cadastrarFuncionario(Funcionario funcionario) {
        if (funcionario == null) throw new RuntimeException("Não é possível cadastrar um funcionário nulo");
        usuarioBusiness.cadastrar(funcionario);
    }

    @Override
    public void cadastrarAdm(Administrador adm) {
        if (adm == null) throw new RuntimeException("Não é possível cadastrar um administrador nulo");
        usuarioBusiness.cadastrar(adm);
    }

    @Override
    public Usuario buscarUsuario(String id) {
        if (id == null || id.trim().isEmpty()) throw new RuntimeException("ID inválido");
        return usuarioBusiness.buscarPorId(id);
    }

    @Override
    public Usuario autenticarUsuario(String email, String senha) {
        if (email == null || senha == null) throw new RuntimeException("Email ou senha não podem ser nulas");
        return usuarioBusiness.autenticar(email, senha);
    }

    @Override
    public Map<String, Usuario> listarUsuario() {
        return usuarioBusiness.listar();
    }

    @Override
    public Map<String, Usuario> listarUsuarioPorPerfil(String perfil) {
        if (perfil == null || perfil.trim().isEmpty()) throw new RuntimeException("Perfil inválido");
        return usuarioBusiness.listarPorPerfil(perfil);
    }

    @Override
    public void atualizarUsuario(String id, Usuario usuario) {
        if (usuario == null) throw new RuntimeException("Não é possível atualizar um usuário nulo");
        usuarioBusiness.atualizar(id, usuario);
    }

    @Override
    public void desativarUsuario(String id) {
        if (id == null || id.trim().isEmpty()) throw new RuntimeException("ID inválido");
        Usuario usuario = usuarioBusiness.buscarPorId(id);
        usuario.setAtivo(false);
        usuarioBusiness.atualizar(id, usuario);
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
        itemBusiness.cadastrar(i);
    }

    @Override
    public Item buscarItem(String id) {
        return itemBusiness.buscar(id);
    }

    @Override
    public Map<String, Item> listarItem() {
        return itemBusiness.listar();
    }

    @Override
    public Map<String, Item> listarItensDisponiveis() {
        return itemBusiness.listarPorStatus("DISPONIVEL");
    }

    @Override
    public Map<String, Item> listarItemPorCategoria(Categoria categoria) {
        return itemBusiness.listarPorCategoria(categoria);
    }

    @Override
    public Map<String, Item> listarItemPorStatus(String status) {
        return itemBusiness.listarPorStatus(status);
    }

    @Override
    public Map<String, Item> listarItemPorFornecedor(Fornecedor fornecedor) {
        return itemBusiness.listarPorFornecedor(fornecedor);
    }

    @Override
    public void atualizarItem(Item item) {
        Item existente = itemBusiness.buscar(item.getId());
        if (!existente.getStatus().equals(item.getStatus())) {
            throw new RuntimeException("Status do item só pode ser alterado através de aluguel ou devolução.");
        }
        itemBusiness.atualizar(item);
    }

    @Override
    public void deletarItem(String id) {
        itemBusiness.deletar(id);
    }

    /* =========================================================================
     * 5. CATEGORIA
     * ========================================================================= */

    @Override
    public void cadastrarCategoria(Categoria c) {
        categoriaBusiness.cadastrar(c);
    }

    @Override
    public Categoria buscarCategoria(String id) {
        return categoriaBusiness.buscar(id);
    }

    @Override
    public void atualizarCategoria(Categoria categoria) {
        categoriaBusiness.atualizar(categoria);
    }

    @Override
    public Map<String, Categoria> listarCategoria() {
        return categoriaBusiness.listar();
    }

    @Override
    public void deletarCategoria(String id) {
        categoriaBusiness.deletar(id);
    }

    /* =========================================================================
     * 6. FORNECEDOR
     * ========================================================================= */

    @Override
    public void cadastrarFornecedor(Fornecedor f) {
        fornecedorBusiness.cadastrar(f);
    }

    @Override
    public Fornecedor buscarFornecedor(String id) {
        return fornecedorBusiness.buscar(id);
    }

    @Override
    public void atualizarFornecedor(Fornecedor fornecedor) {
        fornecedorBusiness.atualizar(fornecedor);
    }

    @Override
    public Map<String, Fornecedor> listarFornecedor() {
        return fornecedorBusiness.listar();
    }

    @Override
    public void deletarFornecedor(String id) {
        fornecedorBusiness.deletar(id);
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

    public void resolverDependencias() {
        for (Item item : itemBusiness.listar().values()) {
            Categoria categoria = categoriaBusiness.buscar(item.getCategoria().getId());
            Fornecedor fornecedor = fornecedorBusiness.buscar(item.getFornecedor().getId());
            item.setCategoria(categoria);
            item.setFornecedor(fornecedor);
            itemBusiness.atualizar(item);
        }

        for (ContratoAluguel contrato : contratoBusiness.listar().values()) {
            Cliente cliente = (Cliente) usuarioBusiness.buscar(contrato.getCliente().getId());
            Item item = itemBusiness.buscar(contrato.getItem().getId());
            contrato.setCliente(cliente);
            contrato.setItem(item);
            contratoBusiness.atualizar(contrato);
        }

        for (Multa multa : multaBusiness.listar().values()) {
            ContratoAluguel contrato = contratoBusiness.buscar(multa.getContrato().getId());
            multa.setContrato(contrato);
            multaBusiness.atualizar(multa);
        }
    }
}