package com.assints.agent;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.CommandLineRunner;

@Component
@RequiredArgsConstructor
public class TestLlmCall  implements CommandLineRunner {

    private final ChatModel chatModel;

    @Override
    public void run(String... args) throws Exception {
        String response = chatModel.call("Que son los dinosarios?");
        System.out.println("Response from LLM: " + response);
    }
    
}
