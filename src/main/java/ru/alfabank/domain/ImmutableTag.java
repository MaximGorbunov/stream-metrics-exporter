package ru.alfabank.domain;

import io.micrometer.core.instrument.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ImmutableTag implements Tag {
    private String key;
    private String value;
}
