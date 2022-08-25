package com.facebookclone.service.email;

import com.facebookclone.utils.EmailDetails;
import org.springframework.mail.MailException;

import javax.mail.MessagingException;

public interface EmailService {

    String sendEmail(EmailDetails details) throws MailException;
    String sendEmailWithAttachment(EmailDetails details) throws MailException, MessagingException;

}
