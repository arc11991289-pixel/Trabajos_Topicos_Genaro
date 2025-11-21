package practica6;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Clase principal que demuestra el uso de una barra de menú (JMenuBar) en un JFrame.
 */
public class VentanaConMenu extends javax.swing.JFrame {
    
    private static final Logger REGISTRO = Logger.getLogger(VentanaConMenu.class.getName());
    private static final String LOOK_AND_FEEL_PREFERIDO = "Metal"; // Definimos el L&F preferido.

    /**
     * Crea una nueva instancia del formulario VentanaConMenu.
     */
    public VentanaConMenu() {
        initComponents();
        // Centraliza la ventana en la pantalla
        setLocationRelativeTo(null); 
    }

    /**
     * Este método es llamado desde el constructor para inicializar el formulario.
     * ADVERTENCIA: No modifique este código. Es generado por el Editor de Formularios.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        barraMenuPrincipal = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        itemMenuAbrir = new javax.swing.JMenuItem();
        itemMenuSalir = new javax.swing.JMenuItem();
        menuEdicion = new javax.swing.JMenu();
        itemMenuCortar = new javax.swing.JMenuItem();
        itemMenuCopiar = new javax.swing.JMenuItem();
        itemMenuPegar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ejemplo de Menú");

        menuArchivo.setText("Archivo");

        itemMenuAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.META_DOWN_MASK));
        itemMenuAbrir.setText("Abrir...");
        menuArchivo.add(itemMenuAbrir);

        itemMenuSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.META_DOWN_MASK));
        itemMenuSalir.setText("Salir");
        itemMenuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manejarCierreVentana(evt);
            }
        });
        menuArchivo.add(itemMenuSalir);

        barraMenuPrincipal.add(menuArchivo);

        menuEdicion.setText("Editar");

        itemMenuCortar.setText("Cortar");
        menuEdicion.add(itemMenuCortar);

        itemMenuCopiar.setText("Copiar");
        itemMenuCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuCopiarActionPerformed(evt);
            }
        });
        menuEdicion.add(itemMenuCopiar);

        itemMenuPegar.setText("Pegar");
        menuEdicion.add(itemMenuPegar);

        barraMenuPrincipal.add(menuEdicion);

        setJMenuBar(barraMenuPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 471, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 171, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemMenuCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuCopiarActionPerformed
        // Lógica para la acción Copiar.
    }//GEN-LAST:event_itemMenuCopiarActionPerformed

    /**
     * Maneja la acción de Salir, cerrando la aplicación.
     * @param evt El evento de acción.
     */
    private void manejarCierreVentana(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manejarCierreVentana
        System.exit(0);
    }//GEN-LAST:event_manejarCierreVentana

    /**
     * Método principal para iniciar la aplicación.
     * @param args los argumentos de la línea de comandos
     */
    public static void main(String args[]) {
        try {
            // Intenta aplicar el Look and Feel preferido (Metal, Nimbus, GTK, etc.)
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (LOOK_AND_FEEL_PREFERIDO.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            // Si el L&F preferido no está disponible, se intentará usar el L&F del sistema.
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            REGISTRO.log(Level.SEVERE, "Error al configurar el Look and Feel: " + LOOK_AND_FEEL_PREFERIDO, ex);
        }

        /* Crear y mostrar el formulario en el Event Dispatch Thread */
        java.awt.EventQueue.invokeLater(() -> new VentanaConMenu().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraMenuPrincipal;
    private javax.swing.JMenuItem itemMenuAbrir;
    private javax.swing.JMenuItem itemMenuCopiar;
    private javax.swing.JMenuItem itemMenuCortar;
    private javax.swing.JMenuItem itemMenuPegar;
    private javax.swing.JMenuItem itemMenuSalir;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuEdicion;
    // End of variables declaration//GEN-END:variables
}