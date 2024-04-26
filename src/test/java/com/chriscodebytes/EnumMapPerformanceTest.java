package com.chriscodebytes;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class EnumMapPerformanceTest {

    private static final int ITERATIONS = 1_000_000;
    private static final int READ_CYCLES = 1;
    private static final StringBuilder csvBuilder = new StringBuilder();
    private static final String CSV_SEPARATOR = "\t";

    @BeforeAll
    public static void setup() {
        csvBuilder.append("Type").append(CSV_SEPARATOR);
        csvBuilder.append("Iterations").append(CSV_SEPARATOR);
        csvBuilder.append("Read Cycles").append(CSV_SEPARATOR);
        csvBuilder.append("Enum Size").append(CSV_SEPARATOR);
        csvBuilder.append("Time /ms").append("\n");
    }

    @AfterAll
    public static void teardown() {
        System.out.println(csvBuilder);
    }

    @Test
    public void testEnumSize3EnumMapPerformance() {

        testMapPerformance(Demo3Type.values(), () -> new EnumMap<>(Demo3Type.class), EnumMap.class.getSimpleName());
    }

    @Test
    public void testEnumSize5EnumMapPerformance() {

        testMapPerformance(Demo5Type.values(), () -> new EnumMap<>(Demo5Type.class), EnumMap.class.getSimpleName());
    }

    @Test
    public void testEnumSize10EnumMapPerformance() {

        testMapPerformance(Demo10Type.values(), () -> new EnumMap<>(Demo10Type.class), EnumMap.class.getSimpleName());
    }

    @Test
    public void testEnumSize20EnumMapPerformance() {

        testMapPerformance(Demo20Type.values(), () -> new EnumMap<>(Demo20Type.class), EnumMap.class.getSimpleName());
    }

    @Test
    public void testEnumSize3HashMapPerformance() {

        testMapPerformance(Demo3Type.values(), HashMap::new, HashMap.class.getSimpleName());
    }

    @Test
    public void testEnumSize5HashMapPerformance() {

        testMapPerformance(Demo5Type.values(), HashMap::new, HashMap.class.getSimpleName());
    }

    @Test
    public void testEnumSize10HashMapPerformance() {

        testMapPerformance(Demo10Type.values(), HashMap::new, HashMap.class.getSimpleName());
    }

    @Test
    public void testEnumSize20HashMapPerformance() {

        testMapPerformance(Demo20Type.values(), HashMap::new, HashMap.class.getSimpleName());
    }

    @Test
    public void testEnumSize3LinkedHashMapPerformance() {

        testMapPerformance(Demo3Type.values(), LinkedHashMap::new, LinkedHashMap.class.getSimpleName());
    }

    @Test
    public void testEnumSize5LinkedHashMapPerformance() {

        testMapPerformance(Demo5Type.values(), LinkedHashMap::new, LinkedHashMap.class.getSimpleName());
    }

    @Test
    public void testEnumSize10LinkedHashMapPerformance() {

        testMapPerformance(Demo10Type.values(), LinkedHashMap::new, LinkedHashMap.class.getSimpleName());
    }

    @Test
    public void testEnumSize20LinkedHashMapPerformance() {

        testMapPerformance(Demo20Type.values(), LinkedHashMap::new, LinkedHashMap.class.getSimpleName());
    }

    private <M extends Map<E, Integer>, E extends Enum<E>> void testMapPerformance(E[] values, Supplier<M> mapSupplier,
                                                                                   String mapName) {
        long total = 0;
        Instant start = Instant.now();
        for (int i = 0; i < ITERATIONS; i++) {
            Map<E, Integer> map = mapSupplier.get();
            for (int j = 0; j < values.length; j++) {
                map.put(values[j], j);
            }
            for (int r = 0; r < READ_CYCLES; r++) {
                for (E value : values) {
                    total += map.get(value);
                }
            }
        }

        assert total > 0;

        Instant end = Instant.now();

        long millis = end.toEpochMilli() - start.toEpochMilli();

        csvBuilder.append(mapName).append(CSV_SEPARATOR);
        csvBuilder.append(ITERATIONS).append(CSV_SEPARATOR);
        csvBuilder.append(READ_CYCLES).append(CSV_SEPARATOR);
        csvBuilder.append(values.length).append(CSV_SEPARATOR);
        csvBuilder.append(millis).append("\n");
    }
}
