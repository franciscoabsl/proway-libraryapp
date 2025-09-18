package com.franciscoabsl.catalog.application.usecases;

import com.franciscoabsl.catalog.domain.Livro;
import com.franciscoabsl.catalog.port.out.BookRepository;

import java.util.Optional;
import java.util.UUID;

public class BuscarLivroPorIdUseCase {

    private final BookRepository bookRepository;

    public BuscarLivroPorIdUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Livro> execute(UUID id) {
        return bookRepository.findById(id);
    }
}