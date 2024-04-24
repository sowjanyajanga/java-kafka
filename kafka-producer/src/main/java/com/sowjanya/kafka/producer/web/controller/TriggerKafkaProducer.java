package com.sowjanya.kafka.producer.web.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sowjanya.kafka.models.KidVaccination;
import com.sowjanya.kafka.services.KafkaProducerService;

import jakarta.websocket.server.PathParam;

@RestController
public class TriggerKafkaProducer {

	
	@Autowired
	KafkaProducerService kafkaProducerService;
	
	@PostMapping( path="/vaccinatedKids")
	public String vaccinateKids(@RequestBody KidVaccination[] kidVaccines ) {
		
		assert kidVaccines != null;
		
		Arrays.asList(kidVaccines).stream().forEach(eachVaccine -> {
			System.out.println( eachVaccine.toString() );
		});
		
		kafkaProducerService.produceMessage(Arrays.asList(kidVaccines));
		return "Successfully updated";
	}
	
	@PostMapping( path="/vaccinatedKid")
	public String vaccinateKids(@RequestBody KidVaccination oneKidVaccine ) {
		
		assert oneKidVaccine != null;
		
		System.out.println( oneKidVaccine );

		kafkaProducerService.produceMessage(Arrays.asList(oneKidVaccine));
		
		return "Successfully updated";
	}
	

}
