package com.example.demo.api.handler;

import jakarta.servlet.http.HttpServletRequest;

public interface SlackNotifier {
    void sendNotification(HttpServletRequest request, Exception e);
}
