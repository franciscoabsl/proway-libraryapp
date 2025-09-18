package com.franciscoabsl.notification.port.out;

import com.franciscoabsl.notification.domain.EmailMessage;

public interface EmailSenderPortOut {
    boolean sendEmail(EmailMessage message);
}