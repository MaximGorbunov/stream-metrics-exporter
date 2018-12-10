# Stream applicationMetrics exporter
Prometheus exporter для вычитывания метрик приложений с кафка топика и трансляции к синтаксису читаемому prometheus

## Settings
Основные конфигурации:

| Property name | Description                                                                                       |           
| --------------| -------------------------------------------------------------------------------------------------:|
| KAFKA_NODES   | Ноды Kafka(spring.cloud.stream.kafka.binder.brokers)                                              |
| TOPIC         | Топик, с которого будут вычитываться метрики                                                      |
| TTL           | Время жизни метрик, необходимо для того чтобы чистить мусор, в случае если api перестает работать | 


- в config server добавляем:
```
spring:
  cloud:
    stream:
      bindings:
        metrics:
          destination: metrics
          producer:
            headerMode: none
      kafka.binder:
        brokers: mqhost1:9092, mqhost2:9092, mqhost3:9092
      metrics:
        schedule-interval: 10s
        properties: "HOST"
```