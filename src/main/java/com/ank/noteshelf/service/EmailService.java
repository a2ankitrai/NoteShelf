package com.ank.noteshelf.service;

public interface EmailService {

    void sendEmail(String to, String subject, String text, boolean isRichText);
}
