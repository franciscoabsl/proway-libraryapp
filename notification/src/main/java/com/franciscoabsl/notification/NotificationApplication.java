package com.franciscoabsl.notification;

import com.franciscoabsl.notification.adapter.in.http.NotificationHttpServer;
import com.franciscoabsl.notification.adapter.out.smtp.SmtpEmailSender;
import com.franciscoabsl.notification.application.usecases.SendNotificationUseCase;

import java.io.FileWriter;
import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class NotificationApplication {

    public static void main(String[] args) throws Exception {
        // Carregar YAML
        Yaml yaml = new Yaml();
        InputStream input = NotificationApplication.class.getClassLoader().getResourceAsStream("application.yaml");
        Map<String, Object> config = yaml.load(input);

        // Config SMTP
        Map<String, Object> smtpConfig = (Map<String, Object>) config.get("smtp");
        String host = (String) smtpConfig.get("host");
        int port = (Integer) smtpConfig.get("port");
        String username = (String) smtpConfig.get("username");
        String password = (String) smtpConfig.get("password");

        String from = (String) smtpConfig.get("from");
        SmtpEmailSender smtpSender = new SmtpEmailSender(host, port, from);

        // UseCase
        SendNotificationUseCase sendNotificationUseCase = new SendNotificationUseCase(smtpSender);

        // Porta de entrada HTTP
        Map<String, Object> serverConfig = (Map<String, Object>) config.get("server");
        int portHttp = (Integer) serverConfig.get("port");

        NotificationHttpServer server = new NotificationHttpServer(sendNotificationUseCase);
        server.start(portHttp);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Encerrando servidor e liberando porta...");
            server.stop();
        }));
    }
}