package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cliente que se conecta al servidor en la IP 10.24.8.247 y puerto 3000
 * para un intercambio básico de mensajes.
 */
public class Cliente {
    
    private static final Logger logger = Logger.getLogger(Cliente.class.getName());
    
    Socket conexion;
    DataInputStream in;
    DataOutputStream out;
    
    void run(){
        
        try {
            // Conexión al servidor usando la IP 10.24.8.247 y puerto 3000
            conexion = new Socket("10.24.8.247", 3000);
            
            // Inicializar flujos de datos
            in = new DataInputStream(conexion.getInputStream());
            out = new DataOutputStream(conexion.getOutputStream());
            
            System.out.println("✅ Conectado al servidor 10.24.8.247:3000");

            // 1. (Servidor -> Cliente): Leer el primer mensaje del servidor.
            String respuesta = in.readUTF();
            System.out.println(" [Recibido] Servidor: " + respuesta);
            
            // 2. (Cliente -> Servidor): Enviar el mensaje de respuesta.
            out.writeUTF("Buenos días");
            System.out.println(" [Enviado] Cliente: Buenos días");
            
            // 3. (Servidor -> Cliente): Leer el segundo mensaje del servidor.
            respuesta = in.readUTF();
            System.out.println(" [Recibido] Servidor: " + respuesta);
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error de conexión o comunicación. Asegúrese de que el servidor está activo en 10.24.8.247:3000", ex);
        } finally {
            // Cerrar la conexión y los flujos
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (conexion != null) conexion.close();
                System.out.println("🛑 Conexión cerrada.");
            } catch (IOException e) {
                 logger.log(Level.WARNING, "Error al cerrar recursos", e);
            }
        }
    }
    
    public static void main(String[] args){
        Cliente cliente = new Cliente();
        cliente.run();
    }
}