package app.kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Date;

public class KafkaSender {

    private static Producer<Long, String> producer = ProducerCreator.createProducer();

    public static void sendXml(String content){
        ProducerRecord record =new ProducerRecord(KafkaConstants.XML_TOPIC_NAME , content);
        System.out.println("==========================Sending XML===============================================");
        producer.send(record);
    }
    public  static void sendlogs(String content){
        ProducerRecord record =new ProducerRecord(KafkaConstants.LOG_TOPIC_NAME , content);
        System.out.println("==========================Sending LOGS===============================================");
        producer.send(record);
    }
}
