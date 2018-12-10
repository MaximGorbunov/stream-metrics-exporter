package ru.alfabank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StreamMetricsExporter {
    public static void main(String[] args) {
        SpringApplication.run(StreamMetricsExporter.class, args);
    }
}
