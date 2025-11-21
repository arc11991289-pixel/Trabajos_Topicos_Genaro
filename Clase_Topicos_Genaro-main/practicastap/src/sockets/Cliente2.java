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
 * Servidor que acepta una conexión de Cliente y mantiene una conversación
 * continua hasta que se envía el comando "adios".
 */
public class Servidor2 {

    private static final int PUERTO = 3000;
    private static final Logger logger = Logger.getLogger(Servidor2.class.getName());

    public void run() {
        ServerSocket serverSocket = null;
        Socket conexionCliente = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        Scanner scanner = new Scanner(System.in);
        String mensajeRecibido = "";
        String respuesta = "";

        try {
            // 1. Crear el ServerSocket
            serverSocket = new ServerSocket(PUERTO);
            System.out.println("🚀 Servidor de Conversación iniciado. Escuchando en el puerto " + PUERTO + "...");

            // 2. Esperar y aceptar la conexión (bloqueante)
            conexionCliente = serverSocket.accept();
            System.out.println("✅ Cliente conectado desde: " + conexionCliente.getInetAddress().getHostAddress());

            // 3. Configurar flujos
            in = new DataInputStream(conexionCliente.getInputStream());
            out = new DataOutputStream(conexionCliente.getOutputStream());
            
            // --- INICIO DE LA CONVERSACIÓN ---
            
            // Iniciar la conversación enviando el primer mensaje (para que el cliente pueda entrar en el bucle 'readUTF')
            System.out.print("Tú (Servidor): ");
            respuesta = scanner.nextLine();
            out.writeUTF(respuesta);
            
            // 4. Bucle principal de conversación
            while (!mensajeRecibido.startsWith("adios") && !respuesta.startsWith("adios")) {
                
                // 5. Servidor espera y lee lo que el cliente escribió (Bloqueante)
                mensajeRecibido = in.readUTF();
                System.out.println("Cliente: " + mensajeRecibido);
                
                // 6. Si el cliente no dijo "adios", el servidor responde
                if (!mensajeRecibido.startsWith("adios")) {
                    System.out.print("Tú (Servidor): ");
                    respuesta = scanner.nextLine();
                    out.writeUTF(respuesta);
                } else {
                    // El cliente dijo adiós. El servidor no necesita responder, pero rompe el bucle.
                    break;
                }
            }
            
            System.out.println("👋 Fin de la conversación.");

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error de conexión o durante la conversación.", ex);
        } finally {
            // 7. Cerrar recursos
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
        Servidor2 servidor = new Servidor2();
        servidor.run();
    }
}