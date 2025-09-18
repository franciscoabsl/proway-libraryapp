package com.franciscoabsl.notification.application.usecases;

import com.franciscoabsl.notification.adapter.out.smtp.SmtpEmailSender;
import com.franciscoabsl.notification.domain.EmailMessage;
import com.franciscoabsl.notification.port.in.NotificationUseCasePortIn;
import com.franciscoabsl.notification.port.out.EmailSenderPortOut;

public class SendNotificationUseCase implements NotificationUseCasePortIn {

    private final EmailSenderPortOut emailSender;

    public SendNotificationUseCase(EmailSenderPortOut emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public boolean execute(EmailMessage message) {
        return emailSender.sendEmail(message);
    }
}
