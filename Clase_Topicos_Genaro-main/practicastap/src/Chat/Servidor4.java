package Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrian Rivera Cortes
 */
public class ServidorChat {

    private ServerSocket servidor;
    private final ArrayList<ManejadorConexion> conexionesActivas = new ArrayList<>();
    private int contadorID = 0;

    /**
     * @param argumentos
     */
    public static void main(String[] argumentos) {
        ServidorChat servidorPrincipal = new ServidorChat();
        servidorPrincipal.iniciarEjecucion();
    }

    public void iniciarEjecucion() {
        try {
            servidor = new ServerSocket(4444);
            System.out.println("Servidor de Chat iniciado en: " + servidor.getLocalSocketAddress());

            while (true) {
                contadorID++;
                Socket nuevoSocket = servidor.accept();
                
                ManejadorConexion manejador = new ManejadorConexion(nuevoSocket, contadorID, this);
                manejador.start();
                
                conexionesActivas.add(manejador);
            }
        } catch (IOException excepcion) {
            Logger.getLogger(ServidorChat.class.getName()).log(Level.SEVERE, "Error al iniciar o ejecutar el servidor", excepcion);
        }
    }

    void difundirMensaje(int idRemitente, String mensaje) {
        DateTimeFormatter formatoTiempo = DateTimeFormatter.ofPattern("HH:mm:ss yyyy/MM/dd");
        LocalDateTime horaActual = LocalDateTime.now();
        String tiempoFormateado = formatoTiempo.format(horaActual);
        
        System.out.println("Mensaje recibido a las " + tiempoFormateado + " del ID " + idRemitente);

        conexionesActivas.forEach((manejador) -> {
            if (idRemitente != manejador.obtenerID()) {
                String mensajeCompleto = "[" + tiempoFormateado + "][ID " + idRemitente + "]: " + mensaje;
                manejador.enviarMensaje(mensajeCompleto);
            }
        });
    }

}

class ManejadorConexion extends Thread {

    private int idCliente = 0;
    private Socket socketCliente;
    private BufferedReader entrada;
    private PrintWriter salida;
    private String datosRecibidos;

    private final ServidorChat servidorPadre;

    ManejadorConexion(Socket s, int id, ServidorChat padre) {
        this.socketCliente = s;
        this.idCliente = id;
        this.servidorPadre = padre;
    }

    @Override
    public void run() {
        boolean seguirComunicacion = true;
        try {
            System.out.println("Conexión aceptada desde la dirección: " + socketCliente.getInetAddress() + " con ID: " + idCliente);

            salida = new PrintWriter(socketCliente.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

            salida.println("¡Conectado! Eres la conexión número " + idCliente);

            while (seguirComunicacion) {
                datosRecibidos = entrada.readLine();
                
                if (datosRecibidos == null) {
                    System.out.println("Cliente con ID " + idCliente + " ha desconectado.");
                    break;
                }

                if (datosRecibidos.equals("SALIR.")) {
                    break;
                }

                servidorPadre.difundirMensaje(idCliente, datosRecibidos);
            }

            socketCliente.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ManejadorConexion.class.getName()).log(Level.SEVERE, "Error en la conexión del cliente con ID " + idCliente, ex);
        }

    }

    public int obtenerID() {
        return idCliente;
    }

    void enviarMensaje(String mensaje) {
        salida.println(mensaje);
    }

}