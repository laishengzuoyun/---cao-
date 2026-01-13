package com.example.delta;

import com.example.delta.task.CrawlerTask;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DeltaPriceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeltaPriceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(CrawlerTask crawlerTask) {
        return args -> {
            System.out.println("应用启动，立即执行爬虫任务...");
            crawlerTask.crawlPrices();
        };
    }

}