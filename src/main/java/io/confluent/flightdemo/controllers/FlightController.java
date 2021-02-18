package io.confluent.flightdemo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import io.confluent.flightdemo.models.FlightModel;

@Controller
public class FlightController {
	@Autowired
    private SimpMessagingTemplate template;

	private List<FlightModel> flightList = new ArrayList<>();

	@KafkaListener(topics = "flight-data", containerFactory = "flightKafkaListenerContainerFactory")
	public void consumeFlightData(FlightModel flight) {
		
		flightList.add(flight);

		if (flightList.size() > 1000) {
			System.out.println("sending flight data");
			this.template.convertAndSend("/topic/flight-data", flightList);
			flightList.clear();
		}
	}

}
