package Chat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrian Rivera Cortes
 */
public class ClienteRemoto {
    public static void main(String args[]){
        
        PrintWriter escritor;
        BufferedReader lector;
        boolean continuar = true;
        OyenteDeServidor hiloOyente;
        
        try {
            Scanner entradaLocal = new Scanner(System.in);
            
            // Dirección IP y Puerto del servidor. IP cambiada a 127.0.0.1
            Socket canalCliente = new Socket("127.0.0.1",4444);
            
            lector = new BufferedReader(new InputStreamReader(canalCliente.getInputStream()));
            escritor = new PrintWriter(canalCliente.getOutputStream(),true);
            
            hiloOyente = new OyenteDeServidor(lector);
            hiloOyente.start();
            
            String textoUsuario = "";
            
            while(continuar){
                textoUsuario = entradaLocal.nextLine();
                escritor.println(textoUsuario);
                
                if (textoUsuario.equals("SALIR.")){
                    hiloOyente.detenerEscucha();
                    continuar = false;
                }
            }
            
            canalCliente.close();
            
        } catch (IOException excepcion) {
            Logger.getLogger(ClienteRemoto.class.getName()).log(Level.SEVERE, "Error de conexión o IO", excepcion);
        }
        
    }
}

class OyenteDeServidor extends Thread{
    
    private BufferedReader entradaServidor;
    private boolean activo = true;
    
    public OyenteDeServidor(BufferedReader _entradaServidor){
        entradaServidor = _entradaServidor;
    }
    
    public void run(){
        while(activo){
            try {
                String mensajeServidor = entradaServidor.readLine();
                if (mensajeServidor == null) {
                    System.out.println("El servidor cerró la conexión.");
                    activo = false;
                    break;
                }
                System.out.println(mensajeServidor);
                
            } catch (IOException ex) {
                System.out.println("Flujo de lectura interrumpido o conexión cerrada.");
            }
        }
    }

    void detenerEscucha(){
        activo = false;
    }
}
