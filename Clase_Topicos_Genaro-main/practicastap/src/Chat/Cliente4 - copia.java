/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chat;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alumno
 */
public class Cliente4 {
    public static void main(String args[]){
        
        PrintWriter    out;
        BufferedReader in;
        boolean band = true;
        Conexion hilo;
        
        try {
            Scanner consola = new Scanner(System.in);
            Socket cliente = new Socket("10.24.29.160",4444);
            
            in  = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            out = new PrintWriter(cliente.getOutputStream(),true);
            
            hilo = new Conexion(in);
            hilo.start();
            
            String mensaje = "";
            while(band){
                mensaje = consola.nextLine();
                out.println(mensaje);
                
                if (mensaje.equals("ADIOS.")){
                    hilo.salir();
                    band = false;
                }
            }
           
            cliente.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente4.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

class Conexion extends Thread{
    
    private BufferedReader in;
    private boolean band=true;
    
    public Conexion(BufferedReader _in){
        in = _in;
    }
    
    public void run(){
        while(band){
            try {
                System.out.println(in.readLine());
            } catch (IOException ex) {
                System.out.println("Se cerro la conexion");
                //Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void salir(){
        band = false;
    }
}
