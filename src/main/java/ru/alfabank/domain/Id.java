package ru.alfabank.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micrometer.core.instrument.Meter.Type;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Id {
    private String name;
    private List<ImmutableTag> tags;
    private Type type;
    @JsonProperty
    private String description;
    private String baseUnit;
}
