package com.dacs.usermodules.utils;

import com.dacs.entitymodules.Customer;
import com.dacs.entitymodules.enumType.MailHelper;
import com.dacs.usermodules.service.SettingService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailUtils {

    public static void sendMail(SettingService settingService, Customer customer, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        String from = getSettingValue(MailHelper.MAIL_FROM, settingService);
        String senderName = getSettingValue(MailHelper.MAIL_SENDER_NAME, settingService);
        String to = customer.getEmail();

        JavaMailSenderImpl mailSender = prepareJavaMailSender(settingService);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(from, senderName);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    public static String getUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String uri = request.getServletPath();
        return url.replace(uri, "");
    }

    private static JavaMailSenderImpl prepareJavaMailSender(SettingService settingService) {
        String host = getSettingValue(MailHelper.MAIL_HOST, settingService);
        Integer port = Integer.parseInt(settingService.getByKey(MailHelper.MAIL_PORT.toString()).getValue());
        String username = getSettingValue(MailHelper.MAIL_USERNAME, settingService);
        String password = getSettingValue(MailHelper.MAIL_PASSWORD, settingService);
        String smtpAuth = getSettingValue(MailHelper.SMTP_AUTH, settingService);
        String smtpSecured = getSettingValue(MailHelper.SMTP_SECURED, settingService);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", smtpAuth);
        properties.setProperty("mail.smtp.starttls.enable", smtpSecured);

        mailSender.setJavaMailProperties(properties);

        return mailSender;
    }

    public static String getSettingValue(MailHelper key, SettingService settingService) {
        return settingService.getByKey(key.toString()).getValue();
    }

}
