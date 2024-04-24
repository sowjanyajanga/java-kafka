package com.sowjanya.kafka.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sowjanya.kafka.models.KidVaccination;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

	
	@Autowired
	private KafkaTemplate<Integer, String> createTemplate;
	
	private int counter = 0;
	
	@Override
	public String produceMessage(List<KidVaccination> vaccinations) {
		
		System.out.println("Produce the message");
		
//		createTemplate.setDefaultTopic("streaming.unknown.topic");
		vaccinations.stream().forEach((KidVaccination thisVaccine) -> {
			
			try{
				String thisVaccineAsJson = new ObjectMapper().writeValueAsString(thisVaccine);
				CompletableFuture<SendResult<Integer, String>> future = createTemplate.send("streaming.unknown.topic", thisVaccineAsJson);	
				counter++;
				future.whenComplete((result, ex) -> {
					if(ex != null) {
						ex.printStackTrace();
					} else {
						System.out.println("Successfully processed message " + result );
					}
				});
				
			} catch(JsonProcessingException jsonEx) {
				jsonEx.printStackTrace();
			}
			
			
		});
		
		return "Successfully Produced";
	}

}
	