package com.franciscoabsl.catalog.application.usecases;

import com.franciscoabsl.catalog.domain.Livro;
import com.franciscoabsl.catalog.port.in.CadastrarLivroPortIn;
import com.franciscoabsl.catalog.port.out.BookRepository;
import com.franciscoabsl.catalog.port.out.LoggerPort;
import com.franciscoabsl.catalog.port.out.NotificationService;

import java.util.UUID;

public class CadastrarLivroUseCase implements CadastrarLivroPortIn {

    private final BookRepository bookRepository;
    private final NotificationService notificationService;
    private final LoggerPort logger;

    public CadastrarLivroUseCase(BookRepository bookRepository,
                                 NotificationService notificationService,
                                 LoggerPort logger) {
        this.bookRepository = bookRepository;
        this.notificationService = notificationService;
        this.logger = logger;
    }

    public Livro execute(String titulo, String autor, int anoPublicacao) {
        // Cria o livro com UUID
        Livro livro = new Livro(UUID.randomUUID(), titulo, autor, anoPublicacao);

        // Salva no repositório
        Livro salvo = bookRepository.save(livro);

        // Tenta notificar
        boolean notificou = notificationService.notifyNewBook(salvo);
        if (!notificou) {
            System.out.println("\nLivro cadastrado, mas o sistema de notificação está indisponível.");
            logger.log("\nLivro cadastrado, mas notificação falhou: " + salvo.getTitulo());
        } else {
            System.out.println("\nLivro cadastrado e notificação enviada com sucesso!");
            logger.log("\nLivro cadastrado e notificação enviada: " + salvo.getTitulo());
        }

        return salvo;
    }
}