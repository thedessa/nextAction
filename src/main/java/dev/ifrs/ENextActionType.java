package dev.ifrs;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ENextActionType {
    INBOX(1),
    NEXT_ACTION(2);

    private static final Map<Integer, ENextActionType> ENUM_MAP = Stream.of(ENextActionType.values())
            .collect(Collectors.toMap(stream -> stream.identifier, Function.identity()));

    private final int identifier;

    ENextActionType(final int identifier) {
        this.identifier = identifier;
    }

    @JsonCreator
    public static ENextActionType fromId(final int value) {
        final ENextActionType result = ENUM_MAP.get(value);
        if (result == null) {
            throw new IllegalArgumentException("identifier " + value + " has no corresponding value");
        }
        return result;
    }

    @JsonValue
    public int getIdentifier() {
        return identifier;
    }
}
