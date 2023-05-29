package flightimporter;

import models.SkyOneAirlinesFlightData;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.formats.json.JsonDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.InputStream;
import java.util.Properties;

public class FlightImporterJob {
    private static final String TOPIC_SKYONE = "skyone";
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties consumerConfig = new Properties();
        try (InputStream stream = FlightImporterJob.class.getClassLoader().getResourceAsStream("consumer.properties")) {
            consumerConfig.load(stream);
        }

        KafkaSource<SkyOneAirlinesFlightData> kafkaSource = KafkaSource
                .<SkyOneAirlinesFlightData>builder() // need to explicitly give String type here
                .setProperties(consumerConfig)
                .setTopics(TOPIC_SKYONE)
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new JsonDeserializationSchema<>(SkyOneAirlinesFlightData.class))
                .build();

        DataStream<SkyOneAirlinesFlightData> skyoneInput = env.fromSource(kafkaSource,
                WatermarkStrategy.noWatermarks(),
                "skyone_source");

        skyoneInput.print();

        env.execute();
    }
}