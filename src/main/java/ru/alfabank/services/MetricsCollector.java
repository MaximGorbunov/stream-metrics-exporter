package ru.alfabank.services;

import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static ru.alfabank.utils.MetricsFormatter.getFormattedMetric;

import io.prometheus.client.Collector;
import io.prometheus.client.Collector.MetricFamilySamples.Sample;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;
import ru.alfabank.domain.ApplicationMetrics;
import ru.alfabank.domain.Metric;

@Service
@Slf4j
@EnableBinding(Sink.class)
public class MetricsCollector extends Collector implements Collector.Describable {

    public static final String EMPTY = "";
    private final PassiveExpiringMap<String, ApplicationMetrics> metricsHashMap;

    public MetricsCollector(@Value("${metrics.ttl}") int ttl) {
        this.metricsHashMap = new PassiveExpiringMap<>(ttl);
    }

    @StreamListener(Sink.INPUT)
    public void retrieve(ApplicationMetrics metric) {
        log.info("fetched metrics from topic");
        metricsHashMap.put(metric.getId(), metric);
    }

    @Override
    public List<MetricFamilySamples> collect() {
        return metricsHashMap.values().stream()
                .flatMap(this::toSamples)
                .collect(Collectors.toList());
    }

    private Stream<MetricFamilySamples> toSamples(ApplicationMetrics applicationMetrics) {
        return applicationMetrics.getMetrics()
                .stream()
                .map(toSample(applicationMetrics));
    }

    private Function<Metric, MetricFamilySamples> toSample(ApplicationMetrics applicationMetrics) {
        return (metric) -> {
            String name = Collector.sanitizeMetricName(metric.getId().getName());
            List<Sample> metricList = singletonList(getFormattedMetric(metric, applicationMetrics.getName(), applicationMetrics.getHost()));
            String description = ofNullable(metric.getId().getDescription()).orElse(EMPTY);
            description += ", base unit: " + metric.getId().getBaseUnit();
            return new MetricFamilySamples(name, Type.GAUGE, description, metricList);
        };
    }

    @Override
    public List<MetricFamilySamples> describe() {
        return new ArrayList<>();
    }
}
