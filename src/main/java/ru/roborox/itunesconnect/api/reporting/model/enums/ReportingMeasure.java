package ru.roborox.itunesconnect.api.reporting.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ReportingMeasure {
    ROYALTY("Royalty"),
    ROYALTY_UTC("Royalty_utc");

    private final String id;
    private static final Map<String, ReportingMeasure> ID_MAP = Arrays.stream(values()).collect(Collectors.toMap(ReportingMeasure::getId, e -> e));

    ReportingMeasure(String id) {
        this.id = id;
    }

    @JsonCreator
    public static ReportingMeasure fromId(String id) {
        final ReportingMeasure result = ID_MAP.get(id);
        if (result == null) {
            throw new IllegalArgumentException("id " + id + " not supported");
        }
        return result;
    }

    @JsonValue
    public String getId() {
        return id;
    }
}
