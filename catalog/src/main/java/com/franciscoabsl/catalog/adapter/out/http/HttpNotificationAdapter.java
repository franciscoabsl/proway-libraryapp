package com.franciscoabsl.catalog.adapter.out.http;

import com.franciscoabsl.catalog.domain.Livro;
import com.franciscoabsl.catalog.port.out.NotificationService;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpNotificationAdapter implements NotificationService {

    private final String notificationUrl;

    public HttpNotificationAdapter(String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

    @Override
    public boolean notifyNewBook(Livro livro) {
        try {
            URL url = new URL(notificationUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            String json = String.format(
                    "{\"id\":\"%s\",\"titulo\":\"%s\",\"autor\":\"%s\",\"anoPublicacao\":%d}",
                    livro.getId(), livro.getTitulo(), livro.getAutor(), livro.getAnoPublicacao()
            );

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            return responseCode >= 200 && responseCode < 300;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}