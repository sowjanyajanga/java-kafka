package com.sowjanya.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.ValueMapperWithKey;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sowjanya.kafka.models.KidVaccination;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStreamsConfig {

	
	private KafkaTemplate<String, String> createTemplate() {
	    Map<String, Object> senderProps = senderProps();
	    ProducerFactory<String, String> pf =
	              new DefaultKafkaProducerFactory<String, String>(senderProps);
	    KafkaTemplate<String, String> template = new KafkaTemplate<>(pf);
	    return template;
	}	

	private Map<String, Object> senderProps() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ProducerConfig.RETRIES_CONFIG, 0);
    props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
    props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
    props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    return props;
}
	
	
	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs() {
        return new KafkaStreamsConfiguration(Map.of(
        		StreamsConfig.APPLICATION_ID_CONFIG, "sjProducerStreams",
        		StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
        		StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName(),
        		StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName(),
        		StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class.getName()
        ));
    }
	
	
	@Bean 
    public KStream<Integer, String> kStream(StreamsBuilder kStreamBuilder) throws JsonMappingException, JsonProcessingException{

		KStream<Integer, String> kStream = kStreamBuilder.stream("streaming.unknown.topic");
		kStream
			.mapValues(convertStringToKidVaccinationObj)
//    		.filter((Integer key, KidVaccination kidVaccine) -> kidVaccine != null && kidVaccine.getVaccineName().equalsIgnoreCase("ChickenPox"))
    		.filter((Integer key, KidVaccination kidVaccine) -> { 
    	  		boolean isCheckBoxVaccinated = kidVaccine != null && kidVaccine.getVaccineName().equalsIgnoreCase("ChickenPox");	
    			return isCheckBoxVaccinated;
    		})			
			.mapValues(convertKidVaccinationObjToString)
    		.to("streaming.chickenpox.input.topic");
		
		kStream
			.mapValues(convertStringToKidVaccinationObj)
			.filter((Integer key, KidVaccination kidVaccine) -> kidVaccine != null && kidVaccine.getVaccineName().equalsIgnoreCase("Influenza"))
			.mapValues(convertKidVaccinationObjToString)
			.to("streaming.influenza.topic");		
		
		kStream.print(Printed.toSysOut());
		
		
		return kStream;
	}
	
	
	private ValueMapperWithKey<Integer, String, KidVaccination> convertStringToKidVaccinationObj = (partitionId, jsonStr) -> {
		{ KidVaccination asObject = null;
		try {
			asObject = new ObjectMapper().readValue(jsonStr, KidVaccination.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} return asObject;}		
		
	};
	
	
	private ValueMapperWithKey<Integer, KidVaccination, String> convertKidVaccinationObjToString = (partitionId,asObject) -> {
		{ String asString = null;
		try {
			asString = new ObjectMapper().writeValueAsString(asObject); return asString;
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} return asString;}		
		
	};	
}
