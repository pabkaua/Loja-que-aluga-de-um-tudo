package com.loja.repositories;

import com.loja.model.Fornecedor;
import com.loja.repositories.interfaces.IFornecedorRepository;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FornecedorPersistenciaCSV implements IFornecedorRepository {
    
    private String caminhoArquivo;
    private Map<String, Fornecedor> fornecedores;

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public FornecedorPersistenciaCSV(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        this.fornecedores = new HashMap<>();
        this.carregarDados();
    }

    @Override
    public void salvar(Fornecedor fornecedor) {
        fornecedores.put(fornecedor.getId(), fornecedor);
    }

    @Override
    public Fornecedor buscar(String id) {
        return fornecedores.get(id);
    }

    @Override
    public Map<String, Fornecedor> listar() {
        return Collections.unmodifiableMap(this.fornecedores);
    }

    @Override
    public boolean atualizar(Fornecedor fornecedor) {
        if (this.fornecedores.containsKey(fornecedor.getId())) {
            fornecedores.put(fornecedor.getId(), fornecedor);
            return true;
        }
        return false;
    }

    @Override
    public boolean deletar(String id) {
        if (this.fornecedores.containsKey(id)) {
            fornecedores.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public void carregarDados() {

        try(BufferedReader leitor = new BufferedReader(new FileReader(this.caminhoArquivo))) {
            String linha = leitor.readLine();

            if(linha != null && linha.toLowerCase().startsWith("id;nome")){
                linha = leitor.readLine();
            }

            while (linha != null){
                String[] dados = linha.split(";");

                if (dados.length >= 5){
                    String id = dados[0];
                    String nome = dados[1];
                    String cnpj = dados[2];
                    String telefone = dados[3];
                    boolean historico = Boolean.parseBoolean(dados[4]);
                    Fornecedor fornecedor = new Fornecedor(id, nome, cnpj, telefone);
                    fornecedor.setHistorico(historico);

                    this.fornecedores.put(fornecedor.getId(), fornecedor);
                }
                linha = leitor.readLine();
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}