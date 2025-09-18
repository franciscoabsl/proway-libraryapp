package com.franciscoabsl.catalog.port.in;

import com.franciscoabsl.catalog.domain.Livro;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookUseCasePortIn {

    Livro cadastrar(String titulo, String autor, int anoPublicacao);

    Optional<Livro> buscarPorId(UUID id);

    List<Livro> listarTodos();
}