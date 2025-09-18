package com.franciscoabsl.catalog;

import com.franciscoabsl.catalog.adapter.in.CatalogCLI;
import com.franciscoabsl.catalog.adapter.out.db.DbBookRepository;
import com.franciscoabsl.catalog.adapter.out.http.HttpNotificationAdapter;
import com.franciscoabsl.catalog.adapter.out.logger.FileLogger;
import com.franciscoabsl.catalog.application.usecases.CadastrarLivroUseCase;
import com.franciscoabsl.catalog.application.usecases.BuscarLivroPorIdUseCase;
import com.franciscoabsl.catalog.application.usecases.ListarLivrosUseCase;

import com.franciscoabsl.catalog.domain.Livro;
import com.franciscoabsl.catalog.port.in.BookUseCasePortIn;
import com.franciscoabsl.catalog.port.out.BookRepository;
import com.franciscoabsl.catalog.port.out.LoggerPort;
import com.franciscoabsl.catalog.port.out.NotificationService;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.InputStream;
import java.util.Map;

public class CatalogApplication {

    public static void main(String[] args) throws Exception {
        // Carregar YAML
        Yaml yaml = new Yaml();
        InputStream input = CatalogApplication.class.getClassLoader().getResourceAsStream("application.yaml");
        Map<String, Object> config = yaml.load(input);

        // Configuração DB
        Map<String, Object> dbConfig = (Map<String, Object>) config.get("database");
        String dbUrl = (String) dbConfig.get("url");
        String dbUser = (String) dbConfig.get("username");
        String dbPass = (String) dbConfig.get("password");

        BookRepository bookRepository = new DbBookRepository(dbUrl, dbUser, dbPass);

        // Configuração Logger
        LoggerPort logger = new FileLogger("catalog.log");

        // Configuração Notification
        Map<String, Object> notificationConfig = (Map<String, Object>) config.get("notification");
        String notificationUrl = (String) notificationConfig.get("url");
        NotificationService notificationService = new HttpNotificationAdapter(notificationUrl);

        // UseCases
        CadastrarLivroUseCase cadastrarLivro = new CadastrarLivroUseCase(bookRepository, notificationService, logger);
        BuscarLivroPorIdUseCase buscarLivro = new BuscarLivroPorIdUseCase(bookRepository);
        ListarLivrosUseCase listarLivros = new ListarLivrosUseCase(bookRepository);

        // CLI
        CatalogCLI cli = new CatalogCLI(cadastrarLivro, buscarLivro, listarLivros);
        cli.start();
    }
}