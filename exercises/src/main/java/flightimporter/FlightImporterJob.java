package flightimporter;

import models.FlightData;
import models.SkyOneAirlinesFlightData;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.formats.json.JsonDeserializationSchema;
import org.apache.flink.formats.json.JsonSerializationSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.Properties;

public class FlightImporterJob {
    private static final String TOPIC_SKYONE = "skyone";
    private static final String TOPIC_FLIGHT = "flightdata";
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties consumerConfig = new Properties();
        try (InputStream stream = FlightImporterJob.class.getClassLoader().getResourceAsStream("consumer.properties")) {
            consumerConfig.load(stream);
        }

        Properties producerConfig = new Properties();
        try (InputStream stream = FlightImporterJob.class.getClassLoader().getResourceAsStream("producer.properties")){
            producerConfig.load(stream);
        }

        KafkaSource<SkyOneAirlinesFlightData> kafkaSource = KafkaSource
                .<SkyOneAirlinesFlightData>builder()
                .setProperties(consumerConfig)
                .setTopics(TOPIC_SKYONE)
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new JsonDeserializationSchema<>(SkyOneAirlinesFlightData.class))
                .build();

        DataStream<SkyOneAirlinesFlightData> skyoneInput = env.fromSource(kafkaSource,
                WatermarkStrategy.noWatermarks(),
                "skyone_source");

        KafkaRecordSerializationSchema<FlightData> kafkaRecordSerializationSchema = KafkaRecordSerializationSchema.<FlightData>builder()
                        .setTopic(TOPIC_FLIGHT)
                        .setValueSerializationSchema(new JsonSerializationSchema<FlightData>(
                                // need the factory mthod because JavaTimeModule is not serializable
                                // it is for ZonedDateTime
                                ()-> {
                                    return new ObjectMapper().registerModule(new JavaTimeModule());
                                }
                        ))
                        .build();
        KafkaSink<FlightData> kafkaSink = KafkaSink.<FlightData>builder()
                        .setKafkaProducerConfig(producerConfig)
                        .setRecordSerializer(kafkaRecordSerializationSchema)
                        .build();

        defineWorkflow(skyoneInput).sinkTo(kafkaSink).name("flightdata_sink");

        env.execute();
    }

    public static DataStream<FlightData> defineWorkflow(DataStream<SkyOneAirlinesFlightData> skyOneSource) {
        return skyOneSource.filter(new FilterFunction<SkyOneAirlinesFlightData>() {
            @Override
            public boolean filter(SkyOneAirlinesFlightData skyOneAirlinesFlightData) throws Exception {
                return skyOneAirlinesFlightData.getFlightArrivalTime().isAfter(ZonedDateTime.now());
            }
        })
                .map(SkyOneAirlinesFlightData::toFlightData);

    }
}