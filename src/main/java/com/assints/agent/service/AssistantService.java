
package com.assints.agent.service;

import reactor.core.publisher.Flux;

public interface AssistantService {
    String chat(String prompt, String model);

    Flux<String> chatStreamo(String prompt, String model);
     
}