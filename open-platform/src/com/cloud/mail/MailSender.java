package com.cloud.mail;

import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.cloud.platform.SpringUtil;
import com.cloud.platform.StringUtil;

@Component
public class MailSender {
	
	@Value("#{propReader['mail.from.address']}")
	private String mailFrom;

	/**
	 * send text mail
	 * 
	 * @param mailTo
	 * @param title
	 * @param text
	 */
	public void sendTextMail(String mailTo, String title, String text) {
		
		if(StringUtil.isNullOrEmpty(mailFrom, mailTo, title, text)) {
			return;
		}
		
		SimpleMailMessage message = new SimpleMailMessage();  
		
		// set address
		message.setFrom(mailFrom);
		message.setTo(mailTo);
		
		// set content
		message.setSubject(title);
		message.setText(text);
		
		message.setSentDate(new Date());
  
		// get mail sender
        JavaMailSender sender = (JavaMailSender) SpringUtil.getBean("javaMailSender");
        
        sender.send(message);
	}
	
	/**
	 * send html mail
	 * 
	 * @param mailTo
	 * @param title
	 * @param text
	 * @throws Exception 
	 */
	public void sendHtmlMail(String mailTo, String title, String html) throws Exception {
		
		if(StringUtil.isNullOrEmpty(mailFrom, mailTo, title, html)) {
			return;
		}
		
		// get mail sender
        JavaMailSender sender = (JavaMailSender) SpringUtil.getBean("javaMailSender");
		MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");  
        
        // set address
        helper.setFrom(mailFrom);
        helper.setTo(mailTo);
        
        // set content
        helper.setSubject(title);
        helper.setText("<html><head></head><body>" + html + "</body></html>", true);
        
        helper.setSentDate(new Date());
        
        // send
        sender.send(mimeMessage); 
	}
}
