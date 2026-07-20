package dev.mel0n.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class McpAppService {

    // public static void main(String[] args) {
    // McpAppService mcpAppService = new McpAppService();
    // try {
    // mcpAppService.linuxTerminal(List.of("ls", "-l"));
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }

    @McpTool(description = "Prueba de mcp")
    public String mcpTest() {
        return "Estamos probando el funcionamiento de un MCP.";
    }

    // @McpTool(description = """
    // Ejecuta un comando en una terminal Linux de la máquina donde se ejecuta este
    // servidor MCP.
    // Recibe el comando y sus argumentos como una lista de cadenas y devuelve la
    // salida estándar del comando.
    // Utiliza esta herramienta cuando necesites inspeccionar el sistema, buscar
    // archivos, consultar procesos o ejecutar utilidades de Linux.
    // """)
    @McpTool(description = " ejecuta comandos bash ")
    public String linuxTerminal(
            @ToolParam(description = "Lista con el ejecutable y sus argumentos. Ejemplo: ['ls','-l','/home']") String command)
            throws Exception {

        System.out.println("##############################################################################");
        System.out.println("CMD: " + command);
        System.out.println("##############################################################################");

        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);

        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {

            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append('\n');
            }

        }

        return output.toString();
    }
}
