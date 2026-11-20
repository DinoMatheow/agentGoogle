
package com.assints.agent.service;

import reactor.core.publisher.Flux;

public interface AssistantService {
    String chat(String prompt, String model);

    Flux<String> chatStream(String prompt, String model);

    String consultRoute(String origin, String destination, String model);
    String explainIncident(String incident, String model);
    String generateIncidentReport(String incident, String model);
    String consultation(String incident, String model);

     
}