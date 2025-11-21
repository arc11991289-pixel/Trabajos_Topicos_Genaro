package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cliente diseñado para chatear con Servidor3 (Broadcast). 
 * Utiliza hilos separados para enviar y recibir mensajes simultáneamente.
 */
public class ClienteChatBroadcast {

    private static final Logger logger = Logger.getLogger(ClienteChatBroadcast.class.getName());
    private static final String SERVER_IP = "127.0.0.1"; // Usar la IP real si no es local
    private static final int PORT = 3000;
    private final String clientName;
    private Socket socket;

    public ClienteChatBroadcast(String name) {
        this.clientName = name;
    }

    public void run() {
        try {
            // 1. Conexión al servidor
            socket = new Socket(SERVER_IP, PORT);
            System.out.println("✅ " + clientName + " conectado. Escribe '.adios' para salir.");

            // 2. Crear flujos de datos
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // 3. Lanzar hilos de comunicación
            Thread listenerThread = new Thread(new Listener(in, clientName));
            Thread senderThread = new Thread(new Sender(out, clientName));

            listenerThread.start();
            senderThread.start();

            // Esperar a que el hilo de envío termine (cuando el usuario escribe ".adios")
            senderThread.join(); 

        } catch (IOException | InterruptedException ex) {
            logger.log(Level.SEVERE, "Error en la conexión del cliente " + clientName, ex);
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
                System.out.println("🛑 " + clientName + " ha finalizado la sesión.");
            } catch (IOException e) {
                logger.log(Level.WARNING, "Error al cerrar el socket", e);
            }
        }
    }

    public static void main(String[] args) {
        // Ejecutar múltiples clientes concurrentemente para probar el broadcast del servidor.
        System.out.println("\n--- Lanzando tres clientes de Chat ---");
        
        new Thread(() -> new ClienteChatBroadcast("Alice").run()).start();
        new Thread(() -> new ClienteChatBroadcast("Bob").run()).start();
        new Thread(() -> new ClienteChatBroadcast("Charlie").run()).start();
    }
}

// --- HILO ENCARGADO DE ENVIAR MENSAJES (SENDER) ---
class Sender implements Runnable {
    private final DataOutputStream out;
    private final String clientName;
    private final Scanner scanner;

    public Sender(DataOutputStream out, String clientName) {
        this.out = out;
        this.clientName = clientName;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        try {
            String message = "";
            while (!message.startsWith(".adios")) {
                System.out.print("(" + clientName + "): ");
                message = scanner.nextLine();
                
                // Formatear el mensaje antes de enviarlo
                String formattedMessage = "[" + clientName + "]: " + message;
                out.writeUTF(formattedMessage);
            }
        } catch (IOException ex) {
            // Se lanza una excepción si el socket se cierra de golpe (p. ej., el servidor se cae)
            System.out.println("\n[ERROR de envío] La conexión fue interrumpida.");
        } finally {
            // Cerrar el scanner y finalizar el hilo
            if (scanner != null) scanner.close();
        }
    }
}

// --- HILO ENCARGADO DE RECIBIR MENSAJES (LISTENER) ---
class Listener implements Runnable {
    private final DataInputStream in;
    private final String clientName;

    public Listener(DataInputStream in, String clientName) {
        this.in = in;
        this.clientName = clientName;
    }

    @Override
    public void run() {
        try {
            String receivedMessage;
            while (true) {
                // Leer mensaje (Bloqueante)
                receivedMessage = in.readUTF(); 
                
                // Mostrar mensaje recibido por broadcast
                // (Usamos System.err para que no se mezcle con el prompt de System.out.print)
                System.err.println("\n[BROADCAST] " + receivedMessage);
                
                // El servidor no envía ".adios" directamente, pero el protocolo del cliente
                // en este caso requiere un manejo más sofisticado para forzar el cierre
                // si se detecta un mensaje de "adios" proveniente de otro cliente.
                // Sin embargo, el cierre natural ocurrirá cuando el servidor cierre el socket
                // si el mensaje que llegó al servidor fue ".adios".
            }
        } catch (IOException ex) {
            // Esta excepción es normal y esperada cuando el socket se cierra
            // (p. ej., cuando el hilo Sender termina y cierra la conexión)
            // o si hay un error de conexión.
            // System.out.println(" [" + clientName + "] Hilo de escucha finalizado.");
        }
    }
}