package com.franciscoabsl.catalog.adapter.in;

import com.franciscoabsl.catalog.domain.Livro;
import com.franciscoabsl.catalog.port.in.BuscarLivroPorIdPortIn;
import com.franciscoabsl.catalog.port.in.CadastrarLivroPortIn;
import com.franciscoabsl.catalog.port.in.ListarLivrosPortIn;

import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class CatalogCLI {

    private final CadastrarLivroPortIn cadastrarLivroPortIn;
    private final BuscarLivroPorIdPortIn buscarLivroPorIdPortIn;
    private final ListarLivrosPortIn listarLivrosPortIn;

    public CatalogCLI(CadastrarLivroPortIn cadastrar,
                      BuscarLivroPorIdPortIn buscar,
                      ListarLivrosPortIn listar) {
        this.cadastrarLivroPortIn = cadastrar;
        this.buscarLivroPorIdPortIn = buscar;
        this.listarLivrosPortIn = listar;
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

        Livro livro = cadastrarLivroPortIn.execute(titulo, autor, ano);
        System.out.println("Livro cadastrado: \n" + livro);
    }

    private void buscar(Scanner scanner) {
        System.out.print("ID do livro: ");
        String idStr = scanner.nextLine();
        try {
            UUID id = UUID.fromString(idStr);
            Optional<Livro> livro = buscarLivroPorIdPortIn.execute(id);
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
        listarLivrosPortIn.execute();
    }
}