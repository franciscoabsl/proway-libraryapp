package com.franciscoabsl.catalog.adapter.in;

import com.franciscoabsl.catalog.application.usecases.CadastrarLivroUseCase;
import com.franciscoabsl.catalog.application.usecases.BuscarLivroPorIdUseCase;
import com.franciscoabsl.catalog.application.usecases.ListarLivrosUseCase;
import com.franciscoabsl.catalog.domain.Livro;

import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class CatalogCLI {

    private final CadastrarLivroUseCase cadastrarLivroUseCase;
    private final BuscarLivroPorIdUseCase buscarLivroPorIdUseCase;
    private final ListarLivrosUseCase listarLivrosUseCase;

    public CatalogCLI(CadastrarLivroUseCase cadastrar,
                      BuscarLivroPorIdUseCase buscar,
                      ListarLivrosUseCase listar) {
        this.cadastrarLivroUseCase = cadastrar;
        this.buscarLivroPorIdUseCase = buscar;
        this.listarLivrosUseCase = listar;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Cadastrar livro");
            System.out.println("2 - Buscar livro por ID");
            System.out.println("3 - Listar todos os livros");
            System.out.println("0 - Sair");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    cadastrar(scanner);
                    break;
                case "2":
                    buscar(scanner);
                    break;
                case "3":
                    listar();
                    break;
                case "0":
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void cadastrar(Scanner scanner) {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Autor: ");
        String autor = scanner.nextLine();

        System.out.print("Ano de publicação: ");
        int ano = Integer.parseInt(scanner.nextLine());

        Livro livro = cadastrarLivroUseCase.execute(titulo, autor, ano);
        System.out.println("Livro cadastrado: \n" + livro);
    }

    private void buscar(Scanner scanner) {
        System.out.print("ID do livro: ");
        String idStr = scanner.nextLine();
        try {
            UUID id = UUID.fromString(idStr);
            Optional<Livro> livro = buscarLivroPorIdUseCase.execute(id);
            livro.ifPresentOrElse(
                    l -> {
                        System.out.println("Livro encontrado: " + l);
                    },
                    () -> {
                        System.out.println("Livro não encontrado.");
                    }
            );
        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido.");
        }
    }

    private void listar() {
        listarLivrosUseCase.execute();
    }
}