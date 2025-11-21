package practica9;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EjThreads {

    private static final Logger REGISTRO = Logger.getLogger(EjThreads.class.getName());
    
    public final Random random = new Random();
    public int x = 0;
    public final Object lock = new Object(); 

    public static void main(String args[]) {
        EjThreads m = new EjThreads();
        m.runThreads();
    }

    void runThreads() {
        // ... (resto del método runThreads sin cambios)
    }

    /**
     * CLASE ANIDADA: Implementa Runnable y usa el 'lock' del padre EjThreads.
     * Debe estar dentro de EjThreads.java.
     */
    class HiloHijoR implements Runnable {

        private final String nombre;
        private final int delay;
        // ... (resto de la clase HiloHijoR sin cambios)

        @Override
        public void run() {
            // ...
        }

        private void incrementar(int i) {
            // ...
        }
    }
}