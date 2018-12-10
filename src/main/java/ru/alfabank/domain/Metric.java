package ru.alfabank.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Metric {

    private final Id id;
    private final Number mean;
}
