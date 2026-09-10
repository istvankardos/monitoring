package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.entity.Alert;
import com.aldisued.iot.monitoring.repository.AlertRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import java.util.UUID;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlertService {

  private final AlertRepository alertRepository;
  private final SensorRepository sensorRepository;
  private final KafkaTemplate<String, AlertDto> kafkaTemplate;

  public AlertService(AlertRepository alertRepository, SensorRepository sensorRepository,
      KafkaTemplate<String, AlertDto> kafkaTemplate) {
    this.alertRepository = alertRepository;
    this.sensorRepository = sensorRepository;
    this.kafkaTemplate = kafkaTemplate;
  }

  public Alert saveAlert(AlertDto alertDto) {
    // TODO: Task 6
    return null;
  }

  @Transactional(readOnly = true)
  public AlertDto findLastAlertBySensorId(UUID sensorId) {
    return alertRepository.findFirstBySensorIdOrderByTimestampDesc(sensorId)
            .map(AlertDto::from)
            .orElseThrow(() -> new EntityNotFoundException(
                    "No alerts found for sensor with id: " + sensorId));
  }
}
