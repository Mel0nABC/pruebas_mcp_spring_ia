package dev.mel0n;

import java.util.Scanner;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class AppService {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.build();
    }

    String userPrompt = "Eres un profesional en linux, estás trabajando como MCP y puedes ejecutar cualquier comando con el método linuxTerminal. Puedes ejecutar comandos de una VM con archlinux instalado";

    @Bean
    CommandLineRunner start(ChatClient chatClient, ToolCallbackProvider mcpTools) {
        return args -> {
            demo(chatClient, mcpTools);
        };
    }

    public void demo(ChatClient chatClient, ToolCallbackProvider mcpTools) {

        String prompt = "";

        while (true) {
            Scanner scan = new Scanner(System.in);

            System.out.println("Indica el prompt:");
            prompt = scan.nextLine();

            String response = chatClient
                    .prompt(userPrompt + prompt)
                    .tools(mcpTools)
                    .call()
                    .content();

            System.out.println(response);
        }

    }

}