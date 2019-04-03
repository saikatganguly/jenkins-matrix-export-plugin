package app.kafka;

public interface KafkaConstants {
    public static String KAFKA_BROKERS = "172.21.0.1:9092";
    public static String CLIENT_ID="jenkins-matrix-exporter";
    public static String XML_TOPIC_NAME="jenkins-raw-data";
    public static String LOG_TOPIC_NAME="jenkins-log-data";
}
