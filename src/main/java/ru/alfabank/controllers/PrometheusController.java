package ru.alfabank.controllers;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;
import java.io.StringWriter;
import java.io.Writer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PrometheusController {
    private final CollectorRegistry collectorRegistry;

    @GetMapping(value = "/prometheus", produces = TextFormat.CONTENT_TYPE_004)
    @SneakyThrows
    public String getMetrics() {
        Writer writer = new StringWriter();
        TextFormat.write004(writer, this.collectorRegistry.metricFamilySamples());
        return writer.toString();
    }
}
