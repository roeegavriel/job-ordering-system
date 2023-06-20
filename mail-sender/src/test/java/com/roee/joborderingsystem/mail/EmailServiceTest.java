package com.roee.joborderingsystem.mail;

import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    private EmailService emailService;

    @Test
    @DisplayName("verify sendMail not invokes emailSender.send if not active")
    void verifySendMailNotInvokesEmailSenderSendIfNotActive() {
        emailService = new EmailService(false, "from@mail.com", emailSender);

        emailService.sendSimpleMessage(null, null, null);

        verify(emailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("verify sendMail invokes emailSender.send")
    void verifySendMailInvokesEmailSenderSend() {
        String fromMail = Instancio.create(String.class);
        String to = Instancio.create(String.class);
        String subject = Instancio.create(String.class);
        String text = Instancio.create(String.class);
        emailService = new EmailService(true, fromMail, emailSender);

        emailService.sendSimpleMessage(to, subject, text);

        ArgumentCaptor<SimpleMailMessage> simpleMailMessageArgumentCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(emailSender).send(simpleMailMessageArgumentCaptor.capture());
        SimpleMailMessage simpleMailMessage = simpleMailMessageArgumentCaptor.getValue();
        assertAll(
            () -> assertEquals(fromMail, simpleMailMessage.getFrom()),
            () -> assertEquals(to, simpleMailMessage.getTo()[0]),
            () -> assertEquals(subject, simpleMailMessage.getSubject()),
            () -> assertEquals(text, simpleMailMessage.getText())
        );
    }
}