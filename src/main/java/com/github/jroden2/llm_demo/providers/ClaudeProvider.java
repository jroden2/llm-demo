package com.github.jroden2.llm_demo.providers;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;
import io.github.jroden2.armco.provider.LlmProvider;

public class ClaudeProvider implements LlmProvider {

    private final AnthropicClient client;
    private final Model model;
    private final int maxTokens;

    public ClaudeProvider(String apiKey, Model model, int maxTokens) {
        this.client = AnthropicOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
        this.model = model;
        this.maxTokens = maxTokens;
    }

    @Override
    public String complete(String prompt) {
        var response = client.messages().create(
                MessageCreateParams.builder()
                        .model(model)
                        .maxTokens(maxTokens)
                        .addUserMessage(prompt)
                        .build()
        );
        return response.content().get(0).asText().text();
    }
}
