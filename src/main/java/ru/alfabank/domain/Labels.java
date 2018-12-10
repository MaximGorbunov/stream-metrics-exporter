package ru.alfabank.domain;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Labels {

    private List<String> keys;
    private List<String> values;
}
