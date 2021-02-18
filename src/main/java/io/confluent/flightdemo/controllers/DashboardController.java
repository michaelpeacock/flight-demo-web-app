package io.confluent.flightdemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import io.confluent.flightdemo.models.DashboardModel;

@Controller
public class DashboardController {
	@Autowired
    private SimpMessagingTemplate template;

	@KafkaListener(topics = "dashboard-data", containerFactory = "dashboardKafkaListenerContainerFactory")
	public void consumeDashboardData(DashboardModel dashboard) {
		System.out.println("received dashboard data");
		this.template.convertAndSend("/topic/dashboard-data", dashboard);
	}

}
