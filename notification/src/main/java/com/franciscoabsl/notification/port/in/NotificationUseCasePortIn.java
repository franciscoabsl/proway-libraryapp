package com.franciscoabsl.notification.port.in;

import com.franciscoabsl.notification.domain.EmailMessage;

public interface NotificationUseCasePortIn {
    boolean execute(EmailMessage message);
}