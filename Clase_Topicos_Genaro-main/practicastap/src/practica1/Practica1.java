package practica1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Adrian Rivera Cortes
 */
public class VentanaPrincipal {

    /**
     * @param argumentos
     */
    public static void main(String[] argumentos) {
        
        JFrame marco = new JFrame("Aplicación de Prueba");
        
        marco.setSize(750, 550);
        
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        BorderLayout administradorLayout = new BorderLayout();
        JButton botonSalir = new JButton("Finalizar Aplicación");
        
        botonSalir.addActionListener(new EscuchaBoton());
        
        marco.setLayout(administradorLayout);
        marco.add(botonSalir, BorderLayout.SOUTH);
        
        marco.setVisible(true);
            
    }
}

class EscuchaBoton implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent evento) {
        // Detener la ejecución de la máquina virtual de Java (JVM)
        System.exit(0);
    }
}