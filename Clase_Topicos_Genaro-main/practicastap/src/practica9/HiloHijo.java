package practica9;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CLASE EXTERNA: Extiende Thread.
 * Debe estar en su propio archivo HiloHijoT.java.
 */
class HiloHijoT extends Thread {

    private static final Logger REGISTRO = Logger.getLogger(HiloHijoT.class.getName());
    private final String nombre;
    private final int delay;
    private final EjThreads main; // Referencia al objeto principal

    public HiloHijoT(String _nombre, EjThreads _main) {
        // ... (código del constructor sin cambios)
    }

    @Override
    public void run() {
        // ... (código del run sin cambios)
    }

    private void incrementar(int i) {
        // ... (código del incrementar sin cambios)
    }
}
