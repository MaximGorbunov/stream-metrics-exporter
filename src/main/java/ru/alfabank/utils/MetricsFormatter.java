package ru.alfabank.utils;

import io.prometheus.client.Collector;
import io.prometheus.client.Collector.MetricFamilySamples;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import ru.alfabank.domain.Labels;
import ru.alfabank.domain.Metric;

public class MetricsFormatter {
    public static final String APPLICATION_LABEL = "application";
    public static final String HOST_LABEL = "host";

    public static MetricFamilySamples.Sample getFormattedMetric(Metric metric, String apiName, String host) {
        Labels labels = fillLabels(metric, apiName, host);
        return new MetricFamilySamples.Sample(metric.getId().getName(), labels.getKeys(), labels.getValues(), metric.getMean().doubleValue());
    }

    private static Labels fillLabels(Metric metric, String apiName, String host) {
        Map<String, String> labelsMap = new HashMap<>();
        labelsMap.put(APPLICATION_LABEL, apiName);
        labelsMap.put(HOST_LABEL, host);
        String metricName = metric.getId().getName();
        metric.getId().setName(Collector.sanitizeMetricName(metricName));
        metric.getId().getTags().forEach(tag -> labelsMap.put(tag.getKey(),tag.getValue()));
        return Labels.builder()
                .keys(new ArrayList<>(labelsMap.keySet()))
                .values(new ArrayList<>(labelsMap.values()))
                .build();
    }
}
