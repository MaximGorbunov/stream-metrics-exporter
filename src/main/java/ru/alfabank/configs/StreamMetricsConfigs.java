package ru.alfabank.configs;

import io.prometheus.client.CollectorRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.alfabank.services.MetricsCollector;

@Configuration
public class StreamMetricsConfigs {

    @Bean
    public CollectorRegistry collectorRegistry(MetricsCollector metricsCollector) {
        CollectorRegistry collectorRegistry = new CollectorRegistry(false);
        collectorRegistry.register(metricsCollector);
        return collectorRegistry;
    }
}

