package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.entity.Alert;
import com.aldisued.iot.monitoring.entity.Sensor;
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

  private static final String ALERTS_TOPIC = "alerts";

  public AlertService(AlertRepository alertRepository, SensorRepository sensorRepository,
      KafkaTemplate<String, AlertDto> kafkaTemplate) {
    this.alertRepository = alertRepository;
    this.sensorRepository = sensorRepository;
    this.kafkaTemplate = kafkaTemplate;
  }

  @Transactional
  public Alert saveAlert(AlertDto alertDto) {
    Sensor sensor = sensorRepository.findById(alertDto.sensorId())
            .orElseThrow(() -> new EntityNotFoundException("Sensor not found with ID: " + alertDto.sensorId()));

    Alert alert = alertRepository.save(new Alert(alertDto.message(), alertDto.timestamp(), sensor));

    kafkaTemplate.send(ALERTS_TOPIC, AlertDto.from(alert));
    return alert;
  }

  @Transactional(readOnly = true)
  public AlertDto findLastAlertBySensorId(UUID sensorId) {
    return alertRepository.findFirstBySensorIdOrderByTimestampDesc(sensorId)
            .map(AlertDto::from)
            .orElseThrow(() -> new EntityNotFoundException(
                    "No alerts found for sensor with id: " + sensorId));
  }
}
