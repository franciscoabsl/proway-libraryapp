package com.franciscoabsl.catalog.application.usecases;

import com.franciscoabsl.catalog.domain.Livro;
import com.franciscoabsl.catalog.port.in.ListarLivrosPortIn;
import com.franciscoabsl.catalog.port.out.BookRepository;

import java.util.List;

public class ListarLivrosUseCase  implements ListarLivrosPortIn {

    private final BookRepository bookRepository;

    public ListarLivrosUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Livro> execute() {
        List<Livro> livros = bookRepository.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return livros;
        }

        System.out.println("=== Lista de Livros ===");
        for (int i = 0; i < livros.size(); i++) {
            Livro livro = livros.get(i);
            System.out.printf("%d. %s%n   Autor: %s%n   Ano: %d%n%n",
                    i + 1, livro.getTitulo(), livro.getAutor(), livro.getAnoPublicacao());
        }

        return livros;
    }
}