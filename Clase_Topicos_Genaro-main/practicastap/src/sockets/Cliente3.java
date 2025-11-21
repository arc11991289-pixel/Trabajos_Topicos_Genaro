package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server that accepts one client connection and engages in a continuous 
 * read/write chat loop. The conversation is initiated by the server 
 * sending the first message.
 */
public class Servidor3 {

    private static final int PUERTO = 3000;
    private static final Logger logger = Logger.getLogger(Servidor3.class.getName());

    public void run() {
        ServerSocket serverSocket = null;
        Socket conexionCliente = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        Scanner scanner = new Scanner(System.in);
        String mensajeRecibido = "";
        String respuesta = "";

        try {
            // 1. Create the ServerSocket
            serverSocket = new ServerSocket(PUERTO);
            System.out.println("🚀 Servidor de Conversación iniciado. Escuchando en el puerto " + PUERTO + "...");

            // 2. Wait for and accept the connection (blocking)
            conexionCliente = serverSocket.accept();
            System.out.println("✅ Cliente conectado desde: " + conexionCliente.getInetAddress().getHostAddress());

            // 3. Configure streams
            in = new DataInputStream(conexionCliente.getInputStream());
            out = new DataOutputStream(conexionCliente.getOutputStream());
            
            // --- INITIATE CONVERSATION (UNBLOCKS THE CLIENT) ---
            
            // Send the first message so the client's loop (which starts with in.readUTF()) can execute.
            String greeting = "Hola Cliente, ¿en qué puedo ayudarte?";
            out.writeUTF(greeting);
            System.out.println(" [Envío Inicial] Servidor: " + greeting);
            
            // 4. Main conversation loop
            // The loop continues as long as the last received message doesn't start with "adios"
            while (!mensajeRecibido.startsWith("adios")) {
                
                // 5. Server waits for and reads the client's message (Blocking)
                mensajeRecibido = in.readUTF();
                System.out.println("Cliente: " + mensajeRecibido);
                
                // Check if the client wants to end the conversation
                if (mensajeRecibido.startsWith("adios")) {
                    break;
                }
                
                // 6. Server prepares and sends a response
                System.out.print("Tú (Servidor): ");
                respuesta = scanner.nextLine();
                out.writeUTF(respuesta);
                
                // Check if the server wants to end the conversation
                if (respuesta.startsWith("adios")) {
                    break; 
                }
            }
            
            System.out.println("👋 Fin de la conversación.");

        } catch (IOException ex) {
            // This usually catches connection resets or broken pipes when the client closes unexpectedly
            logger.log(Level.SEVERE, "Error de conexión o durante la conversación. Posiblemente cliente desconectado.", ex);
        } finally {
            // 7. Close resources
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (conexionCliente != null) conexionCliente.close();
                if (serverSocket != null) serverSocket.close();
                if (scanner != null) scanner.close();
                System.out.println("🛑 Servidor detenido.");
            } catch (IOException e) {
                 logger.log(Level.WARNING, "Error al cerrar recursos", e);
            }
        }
    }

    public static void main(String[] args) {
        Servidor3 servidor = new Servidor3();
        servidor.run();
    }
}