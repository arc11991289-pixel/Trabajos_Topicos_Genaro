package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cliente diseñado para chatear con Servidor2. 
 * Maneja la conversación continua y el cierre con "adios".
 */
public class ClienteChat {

    private static final Logger logger = Logger.getLogger(ClienteChat.class.getName());

    // NOTA: Usa '127.0.0.1' (localhost) si el cliente y el servidor corren en la misma máquina.
    private static final String SERVER_IP = "127.0.0.1"; 
    private static final int PORT = 3000;
    
    private final String clientID;

    public ClienteChat(String id) {
        this.clientID = id;
    }

    public void run() {
        Socket conexion = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        Scanner scanner = new Scanner(System.in);
        String mensajeRecibido = "";
        String mensajeAEnviar = "";

        try {
            conexion = new Socket(SERVER_IP, PORT);
            System.out.println("✅ Cliente " + clientID + " conectado a " + SERVER_IP + ":" + PORT);

            in = new DataInputStream(conexion.getInputStream());
            out = new DataOutputStream(conexion.getOutputStream());

            // --- INICIO DE CONVERSACIÓN ---
            
            // 1. Cliente lee el saludo inicial del servidor ("Hola").
            mensajeRecibido = in.readUTF();
            System.out.println(" [" + clientID + "] Servidor: " + mensajeRecibido); 

            // 2. El cliente entra en el bucle principal.
            // Para mantener el turno de la conversación:
            // Cliente: Escribe -> Servidor: Lee -> Servidor: Escribe -> Cliente: Lee
            while (!mensajeAEnviar.startsWith("adios")) {
                
                // Cliente escribe su mensaje
                System.out.print(" [" + clientID + "] Tú: ");
                mensajeAEnviar = scanner.nextLine();
                out.writeUTF(mensajeAEnviar);
                
                if (mensajeAEnviar.startsWith("adios")) break;

                // Cliente lee la respuesta del servidor (Bloqueante)
                mensajeRecibido = in.readUTF();
                System.out.println(" [" + clientID + "] Servidor: " + mensajeRecibido);
                
                if (mensajeRecibido.startsWith("adios")) break;
            }

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error de conexión/comunicación para Cliente " + clientID, ex);
        } finally {
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (conexion != null) conexion.close();
                if (scanner != null) scanner.close();
                System.out.println("🛑 Cliente " + clientID + " ha cerrado la conexión.");
            } catch (IOException e) {
                logger.log(Level.WARNING, "Error al cerrar recursos del Cliente " + clientID, e);
            }
        }
    }

    public static void main(String[] args) {
        // Ejecuta el primer cliente
        new ClienteChat("Alpha").run();
        
        // NO ejecutes múltiples instancias de este cliente si solo usas un terminal, 
        // ya que todos lucharían por el mismo System.in.
        // Si quieres probar la concurrencia, inicia múltiples procesos de consola y ejecuta 
        // una instancia de ClienteChat en cada uno.
    }
}