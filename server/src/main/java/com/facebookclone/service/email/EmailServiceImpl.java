package com.facebookclone.service.email;

import com.facebookclone.utils.EmailDetails;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;


@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public String sendEmail(EmailDetails details) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("emailsender87@gmail.com");
        mailMessage.setTo(details.getRecipient());
        mailMessage.setText(details.getMsgBody());
        mailMessage.setSubject(details.getSubject());

        javaMailSender.send(mailMessage);
        return "Email successfully sent!";
    }

    @Override
    public String sendEmailWithAttachment(EmailDetails details) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setFrom("emailsender1@gmail.com");
        helper.setTo(details.getRecipient());
        helper.setSubject(details.getSubject());
        helper.setText(details.getMsgBody(), true);
        javaMailSender.send(message);
        return "Email successfully sent. Please register your account first!";
    }

}

