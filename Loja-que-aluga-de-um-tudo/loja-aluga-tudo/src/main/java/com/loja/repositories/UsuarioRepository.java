package com.loja.repositories;

import com.loja.model.Usuario;
import java.util.List;

public interface UsuarioRepository {

    void salvar(Usuario usuario);

    Usuario buscarPorId(String id);

    Usuario buscarPorEmail(String email);

    List<Usuario> listar();

    void deletar(String id);
}
