package com.aldisued.iot.monitoring.service;


import java.util.ArrayList;
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
    if (data == null || data.isEmpty()) {
      throw new IllegalArgumentException("Data array cannot be empty.");
    }
    if (windowSize <= 0 || windowSize > data.size()) {
      throw new IllegalArgumentException(
              "Window size has to be greater than 0 and cannot be greater than the number of values");
    }

    List<Double> averages = new ArrayList<>(data.size() - windowSize + 1);
    double sum = 0;
    for (int i = 0; i < windowSize; i++) {
      sum += data.get(i);
    }
    averages.add(sum / windowSize);

    for (int right = windowSize; right < data.size(); right++) {
      sum += data.get(right);
      sum -= data.get(right - windowSize);
      averages.add(sum / windowSize);
    }

    return averages;
  }

}
