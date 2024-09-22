package com.example.demo.infrastructure.slack;

import com.example.demo.api.handler.SlackNotifier;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class SlackNotifierImpl implements SlackNotifier {
    private final WebClient webClient;
    private final boolean slackWebhookEnable;

    public SlackNotifierImpl(
            @Qualifier("slackWebClient") WebClient webClient,
            @Value("${slack.webhook.enable}") boolean slackWebhookEnable
    ) {
        this.webClient = webClient;
        this.slackWebhookEnable = slackWebhookEnable;
    }

    @Async("slackThreadPoolExecutor")
    public void sendNotification(HttpServletRequest request, Exception e) {
        if (!slackWebhookEnable) return;

        SlackMessageData slackMessageData = new SlackMessageData(request, e);

        try {
            webClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new TextSendData(slackMessageData.toTemplate()))
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(
                            success -> log.info("Slack notification sent successfully: {}", success),
                            error -> log.error("Failed to send Slack notification: {}", error.getMessage())
                    );
        } catch (Exception ex) {
            log.error("Error occurred while sending Slack notification: {}", ex.getMessage());
        }
    }
}
