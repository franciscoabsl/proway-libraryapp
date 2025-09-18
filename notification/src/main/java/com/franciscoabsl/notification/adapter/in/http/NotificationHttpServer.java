package com.franciscoabsl.notification.adapter.in.http;

import com.franciscoabsl.notification.application.usecases.SendNotificationUseCase;
import com.franciscoabsl.notification.domain.EmailMessage;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;


public class NotificationHttpServer {

    private final SendNotificationUseCase sendNotificationUseCase;
    private final Gson gson = new Gson();
    private HttpServer httpServer;

    public NotificationHttpServer(SendNotificationUseCase useCase) {
        this.sendNotificationUseCase = useCase;
    }

    public void start(int port) throws Exception {
        httpServer = HttpServer.create(new InetSocketAddress(port), 0); // <-- usa o atributo
        httpServer.createContext("/notify", this::handleNotify);
        httpServer.setExecutor(null);
        httpServer.start();
        System.out.println("Notification service running on port " + port);
    }

    public void stop() {
        if (httpServer != null) {
            httpServer.stop(0); // 0 significa parar imediatamente
            System.out.println("Servidor HTTP parado, porta liberada.");
        }
    }

    private void handleNotify(HttpExchange exchange) {
        try {
            if (!"POST".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                return;
            }

            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            LivroDto livroDto = gson.fromJson(body, LivroDto.class);

            EmailMessage message = new EmailMessage(
                    "livraria@dominio.com",
                    "Novo livro cadastrado: " + livroDto.getTitulo(),
                    "O livro '" + livroDto.getTitulo() + "' do autor " + livroDto.getAutor() +
                            " foi cadastrado em " + livroDto.getAnoPublicacao()
            );

            boolean success = sendNotificationUseCase.execute(message);

            String response = success ? "OK" : "FAIL";
            exchange.sendResponseHeaders(success ? 200 : 500, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                exchange.sendResponseHeaders(500, 0);
            } catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    static class LivroDto {
        private String id;
        private String titulo;
        private String autor;
        private int anoPublicacao;

        public String getId() { return id; }
        public String getTitulo() { return titulo; }
        public String getAutor() { return autor; }
        public int getAnoPublicacao() { return anoPublicacao; }
    }
}