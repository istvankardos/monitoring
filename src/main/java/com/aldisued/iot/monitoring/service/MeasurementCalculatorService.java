package com.aldisued.iot.monitoring.service;


import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MeasurementCalculatorService {

  public List<Double> filterByAverageDeviation(List<Double> values, Double deviation) {
    if (deviation == null || deviation < 0.0 || deviation > 1.0) {
      throw new IllegalArgumentException("Deviation is must be between 0.0 and 1.0. It was: " + deviation);
    }

    if (values == null || values.isEmpty()) {
      return List.of();
    }

    double average = values.stream()
            .mapToDouble(Double::doubleValue)
            .average()
            .orElseThrow();

    double range = Math.abs(average * deviation);
    double rangeLower = average - range;
    double rangeUpper = average + range;

    return values.stream()
            .filter(value -> value >= rangeLower && value <= rangeUpper)
            .toList();
  }

  public List<Double> getMovingAverage(List<Double> data, int windowSize) {
    // TODO: Task 10
    return List.of();
  }

}
