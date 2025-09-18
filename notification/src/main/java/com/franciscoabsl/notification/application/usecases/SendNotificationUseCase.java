package com.franciscoabsl.notification.application.usecases;

import com.franciscoabsl.notification.adapter.out.smtp.SmtpEmailSender;
import com.franciscoabsl.notification.domain.EmailMessage;

public class SendNotificationUseCase {

    private final SmtpEmailSender emailSender;

    public SendNotificationUseCase(SmtpEmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public boolean execute(EmailMessage message) {
        return emailSender.sendEmail(message);
    }
}