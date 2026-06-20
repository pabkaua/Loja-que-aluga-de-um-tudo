package com.loja.repositories.interfaces;
import com.loja.model.Item;
import java.util.Map;

public interface ItemRepository {
    public void salvar(Item item);
    public Item buscar(String id);

    // O conjunto chave-valor vai ser String-Objeto, sendo a String o ID para o hashmap
    public Map<String, Item> listar();
    public Map<String, Item> listarPorStatus(String status);
    public Map<String, Item> listarPorCategoria(String categoriaId);
    public Map<String, Item> listarPorFornecedor(String fornecedorId);

    public void atualizar(Item item);
    public boolean deletar(String id);

    public void carregarDados();
    public void salvarDados();
}
