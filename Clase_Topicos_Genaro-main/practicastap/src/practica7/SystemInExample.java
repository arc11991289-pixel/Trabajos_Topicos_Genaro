package practica7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Demostración de las formas preferidas de leer entrada de usuario
 * desde la consola (System.in), usando Scanner y BufferedReader.
 */
public class EntradaConsolaEjemplo {
    
    private static final Logger REGISTRO = Logger.getLogger(EntradaConsolaEjemplo.class.getName());

    public static void main(String[] args) {
        
        // --- Método 1: Usando la clase Scanner (más fácil para diferentes tipos de datos) ---
        leerConScanner();
        
        System.out.println("\n------------------------------------------------\n");

        // --- Método 2: Usando BufferedReader (más eficiente para grandes lecturas de texto) ---
        leerConBufferedReader();
    }

    /**
     * Lee entrada del usuario (String y entero) utilizando la clase Scanner.
     */
    public static void leerConScanner() {
        System.out.println("### Usando Scanner ###");
        // Se utiliza try-with-resources para asegurar que el Scanner se cierre correctamente
        try (Scanner scanner = new Scanner(System.in)) {
            
            // Lectura de String
            System.out.print("1. Por favor, ingresa tu nombre: ");
            String nombre = scanner.nextLine();
            System.out.println("Hola, " + nombre + "!");
            
            // Lectura de Entero con manejo de errores
            System.out.print("2. Ingresa tu edad (número entero): ");
            if (scanner.hasNextInt()) {
                int edad = scanner.nextInt();
                System.out.println("Tu edad es: " + edad);
            } else {
                System.out.println("Advertencia: Entrada no válida para la edad (no es un número entero).");
            }
            
        } catch (InputMismatchException ex) {
            REGISTRO.log(Level.WARNING, "Error de tipo de entrada en Scanner.", ex);
        }
    }
    
    /**
     * Lee una línea de texto del usuario utilizando BufferedReader.
     * Es más eficiente para procesar texto, pero requiere manejo de IOException.
     */
    public static void leerConBufferedReader() {
        System.out.println("### Usando BufferedReader ###");
        
        // InputStreamReader convierte bytes a caracteres. BufferedReader almacena en búfer para eficiencia.
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            
            System.out.print("3. Ingresa una frase completa: ");
            String frase = reader.readLine();
            System.out.println("Frase ingresada: " + frase);
            
        } catch (IOException ex) {
            // IOException atrapa errores de lectura/escritura del sistema
            REGISTRO.log(Level.SEVERE, "Error de I/O al leer con BufferedReader", ex);
        }
    }
}