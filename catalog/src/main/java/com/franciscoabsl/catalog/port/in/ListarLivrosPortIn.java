package com.franciscoabsl.catalog.port.in;

import com.franciscoabsl.catalog.domain.Livro;

import java.util.List;

public interface ListarLivrosPortIn {
    List<Livro> execute();
}