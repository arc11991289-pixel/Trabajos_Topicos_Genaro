package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cliente diseñado para probar el Servidor Multihilo. 
 * Sigue el protocolo: Lee "Hola" -> Responde -> Lee "Adios" -> Cierra.
 */
public class ClienteMultihilo {

    private static final Logger logger = Logger.getLogger(ClienteMultihilo.class.getName());

    // NOTA: Usa '127.0.0.1' (localhost) si el cliente y el servidor corren en la misma máquina.
    // Si el servidor corre en otra máquina, usa la IP real de esa máquina.
    private static final String SERVER_IP = "127.0.0.1"; 
    private static final int PORT = 3000;
    
    // Identificador para distinguir las instancias del cliente en la salida de consola
    private final String clientID;

    public ClienteMultihilo(String id) {
        this.clientID = id;
    }

    public void run() {
        Socket conexion = null;
        DataInputStream in = null;
        DataOutputStream out = null;

        try {
            // 1. Establecer conexión con el servidor
            conexion = new Socket(SERVER_IP, PORT);
            System.out.println("✅ Cliente " + clientID + " conectado a " + SERVER_IP + ":" + PORT);

            // 2. Inicializar flujos de entrada y salida
            in = new DataInputStream(conexion.getInputStream());
            out = new DataOutputStream(conexion.getOutputStream());

            // --- Protocolo de Comunicación (Implementa el hilo Conexion) ---
            
            // 3. PASO 1 (Server -> Client): Lee el saludo "Hola"
            String saludoServidor = in.readUTF();
            System.out.println(" [" + clientID + "] Servidor dice: " + saludoServidor); 

            // 4. PASO 2 (Client -> Server): Envía una respuesta
            String respuestaCliente = "Soy el Cliente " + clientID + " y mi respuesta es positiva.";
            out.writeUTF(respuestaCliente);
            System.out.println(" [" + clientID + "] Enviado: " + respuestaCliente);

            // 5. PASO 3 (Server -> Client): Lee la despedida "Adios"
            String despedidaServidor = in.readUTF();
            System.out.println(" [" + clientID + "] Servidor dice: " + despedidaServidor); 

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error de conexión/comunicación para Cliente " + clientID, ex);
        } finally {
            // 6. Cerrar recursos
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (conexion != null) conexion.close();
                System.out.println("🛑 Cliente " + clientID + " ha cerrado la conexión.");
            } catch (IOException e) {
                logger.log(Level.WARNING, "Error al cerrar recursos del Cliente " + clientID, e);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("\n--- Lanzando múltiples clientes de prueba (concurrente) ---");
        
        // Ejecutar tres clientes en hilos separados para demostrar la capacidad multihilo del servidor
        new Thread(() -> new ClienteMultihilo("A").run()).start();
        new Thread(() -> new ClienteMultihilo("B").run()).start();
        new Thread(() -> new ClienteMultihilo("C").run()).start();
    }
}