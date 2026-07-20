# Spring AI MCP Example

A simple example demonstrating how to build a **Model Context Protocol (MCP)** server and client using **Spring AI**.

This project is intended as a learning resource for understanding how Spring AI integrates with MCP, how tools are exposed by an MCP server, and how an MCP client discovers and invokes those tools through a Large Language Model (LLM).

## Project Structure

The repository contains two independent Spring Boot applications:

```
spring-ai-mcp-example/
│
├── mcp-server/
│   └── Spring AI MCP Server
│
└── mcp-client/
    └── Spring AI MCP Client
```

### MCP Server

The server exposes one or more tools using the `@McpTool` annotation.

Example:

```java
@McpTool(description = Executes a command in a Linux terminal.)
public String linuxTerminal(String command) {
    ...
}
```

The server is responsible only for publishing tools. It does **not** communicate directly with the LLM.

---

### MCP Client

The client:

* Connects to the MCP server.
* Discovers the available tools.
* Connects to an LLM through Ollama.
* Allows the LLM to decide when a tool should be executed.

Example:

```java
String response = chatClient
        .prompt(userPrompt)
        .tools(mcpTools)
        .call()
        .content();
```

## Technologies

* Java 17+
* Spring Boot
* Spring AI
* Model Context Protocol (MCP)
* Ollama

## Requirements

* Java 17 or newer
* Maven
* Ollama installed and running

Example model:

```
granite4.1:3b
```

## Configuration

### MCP Server

```properties
server.port=8080

spring.ai.mcp.server.protocol=STREAMABLE
```

### MCP Client

```properties
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=granite4.1:3b

spring.ai.mcp.client.streamable-http.connections.local.url=http://localhost:8080
```

## Running the Example

### 1. Start Ollama

```bash
ollama serve
```

### 2. Download a model (if necessary)

```bash
ollama pull granite4.1:3b
```

### 3. Start the MCP Server

```
cd mcp-server
mvn spring-boot:run
```

The server will publish the available tools.

### 4. Start the MCP Client

```
cd mcp-client
mvn spring-boot:run
```

The client will:

1. Connect to the MCP server.
2. Discover the published tools.
3. Send the user prompt to the LLM.
4. Execute tools when requested by the model.
5. Return the final response.

## Learning Goals

This project demonstrates:

* Creating an MCP Server with Spring AI.
* Creating an MCP Client.
* Publishing tools using `@McpTool`.
* Discovering tools with `ToolCallbackProvider`.
* Invoking MCP tools through `ChatClient`.
* Integrating Spring AI with Ollama.

## Notes

This repository is intended for educational purposes and experimentation with Spring AI MCP.

Some example tools (such as executing Linux commands) are designed for local testing only and **must not** be exposed in production environments without proper validation and security controls.

