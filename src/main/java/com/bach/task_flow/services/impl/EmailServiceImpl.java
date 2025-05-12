package com.bach.task_flow.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmailServiceImpl implements com.bach.task_flow.services.EmailService {

    JavaMailSender mailSender;

    @Override
    public void sendOTP(String mail, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mail);
            message.setFrom("lyduybach900@gmail.com");
            message.setSubject("Verify Email");
            message.setText("Mã OTP của bạn là: " + otp + "\nMã này có hiệu lực trong vòng 5 phút, vui lòng không tiết lộ mã này cho người khác!");
            mailSender.send(message);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
