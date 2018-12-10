package ru.alfabank.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplicationMetrics {

    private static final String HOST = "HOST";
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date createdTime;

    private String name;

    private long interval;

    private Collection<Metric> metrics;

    private Map<String, String> properties;


    public String getId() {
        return name + getHost();
    }

    public String getHost() {
        return properties.get(HOST);
    }
}
