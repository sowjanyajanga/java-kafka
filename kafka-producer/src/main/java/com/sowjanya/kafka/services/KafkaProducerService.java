package com.sowjanya.kafka.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sowjanya.kafka.models.KidVaccination;


public interface KafkaProducerService {
	
	public String produceMessage(List<KidVaccination> vaccinations);

}
