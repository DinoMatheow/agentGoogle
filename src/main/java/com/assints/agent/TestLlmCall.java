package com.assints.agent;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;

@Component
@RequiredArgsConstructor
public class TestLlmCall  implements CommandLineRunner {

    private final ChatModel chatModel;

    @Override
    public void run(String... args) throws Exception {
        ChatResponse response = chatModel.call(new Prompt("Que son los dinosarios?"));

        String content = Objects.requireNonNull(response.getResult().getOutput().getText());

        System.out.println("Model " + response.getMetadata().getModel());
        System.out.println("Input tokens: " + response.getMetadata().getUsage().getPromptTokens());
        System.out.println("Output tokens: " + response.getMetadata().getUsage().getCompletionTokens());
        System.out.println("Total tokens: " + response.getMetadata().getUsage().getTotalTokens());
        System.out.println("Finish reason: " + response.getResult().getMetadata().getFinishReason());




        System.out.println("Response from LLM: " + content);
    }
    
}
