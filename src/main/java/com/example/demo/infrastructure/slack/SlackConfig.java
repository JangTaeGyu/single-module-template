package com.example.demo.infrastructure.slack;

import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class SlackConfig {
    private final String slackWebhookUrl;

    public SlackConfig(@Value("${slack.webhook.url}") String slackWebhookUrl) {
        this.slackWebhookUrl = slackWebhookUrl;
    }

    @Bean(name = "slackWebClient")
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(slackWebhookUrl)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // 연결 타임아웃 설정 (5초)
                                .responseTimeout(Duration.ofSeconds(5))             // 응답 타임아웃 설정 (5초)
                ))
                .build();
    }

    @Bean(name = "slackThreadPoolExecutor")
    public Executor slackThreadPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);    // 최소 스레드 수
        executor.setMaxPoolSize(10);    // 최대 스레드 수
        executor.setQueueCapacity(50);  // 대기 큐 크기
        executor.setThreadNamePrefix("SlackNotifier-");
        executor.setThreadGroupName("SlackNotifier-Group");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
