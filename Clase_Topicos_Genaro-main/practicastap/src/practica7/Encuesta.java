package practica7;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Formulario de encuesta que recopila preferencias y guarda los datos
 * en un archivo de texto y una base de datos MySQL.
 */
public class EncuestaPreferencias extends javax.swing.JFrame {
    
    // Usamos el logger de Java.util.logging
    private static final Logger REGISTRO = Logger.getLogger(EncuestaPreferencias.class.getName());
    
    // Constantes de conexión a la BD
    private final String URL = "jdbc:mysql://localhost:3306/encuesta";
    private final String USER = "encuesta_user";
    private final String PASSWORD = "encuesta_pass";
    
    // Objeto de conexión
    private Connection conexion;

    /**
     * Crea una nueva instancia del formulario EncuestaPreferencias.
     */
    public EncuestaPreferencias() {
        initComponents();
        // Inicializa el JLabel de horas con el valor inicial del slider
        this.lbHoras.setText(String.valueOf(this.slHoras.getValue()));
        this.setLocationRelativeTo(null); // Centra la ventana
        this.conectarBaseDeDatos();
    }

    /**
     * Conecta a la base de datos MySQL usando los parámetros definidos.
     */
    private void conectarBaseDeDatos() {
        try {
            // Inicializa la conexión
            this.conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            REGISTRO.log(Level.INFO, "Conexión a MySQL establecida con éxito.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                    "Error en conexión con la BD: " + e.getMessage() + "\nLas respuestas no se guardarán en la base de datos.", 
                    "Error de Conexión", 
                    JOptionPane.ERROR_MESSAGE);
            REGISTRO.log(Level.SEVERE, "Error al conectar a MySQL", e);
            // La aplicación puede seguir corriendo, pero con la conexión nula.
            this.conexion = null; 
        }
    }

    /**
     * Este método es llamado desde el constructor para inicializar el formulario.
     * ADVERTENCIA: No modifique este código. Es generado por el Editor de Formularios.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        rbWin = new javax.swing.JRadioButton();
        rbLnx = new javax.swing.JRadioButton();
        rbMac = new javax.swing.JRadioButton();
        ckPrg = new javax.swing.JCheckBox();
        ckGrf = new javax.swing.JCheckBox();
        ckAdm = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        slHoras = new javax.swing.JSlider();
        btnGuardar = new javax.swing.JButton();
        lbHoras = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Encuesta de Preferencias TI");

        jLabel1.setText("Elige un sistema operativo");

        jLabel2.setText("Elige tu especialidad");

        jLabel3.setText("Horas que le dedicas al trabajo");

        buttonGroup1.add(rbWin);
        rbWin.setSelected(true);
        rbWin.setText("Windows");

        buttonGroup1.add(rbLnx);
        rbLnx.setText("Linux");

        buttonGroup1.add(rbMac);
        rbMac.setText("Mac");

        ckPrg.setText("Programación");

        ckGrf.setText("Diseño Gráfico");

        ckAdm.setText("Administración");

        slHoras.setMaximum(24);
        slHoras.setValue(8);
        slHoras.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slHorasStateChanged(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        lbHoras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbHoras.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rbWin)
                                            .addComponent(rbLnx)
                                            .addComponent(rbMac)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ckGrf)
                                    .addComponent(ckPrg)
                                    .addComponent(ckAdm)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(lbHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(btnGuardar)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(slHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addComponent(rbWin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbLnx)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbMac)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ckPrg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ckGrf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ckAdm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slHoras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbHoras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(btnGuardar)
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void slHorasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slHorasStateChanged
        // Actualiza el label de horas
        this.lbHoras.setText(String.valueOf(this.slHoras.getValue()));
    }//GEN-LAST:event_slHorasStateChanged

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // 1. Recopilar datos
        String sSO = this.obtenerSistemaOperativo();
        String sPrg = this.ckPrg.isSelected() ? "S" : "N";
        String sGrf = this.ckGrf.isSelected() ? "S" : "N";
        String sAdm = this.ckAdm.isSelected() ? "S" : "N";
        int iHrs = this.slHoras.getValue();
        
        // 2. Formatear la cadena para el archivo
        String sRes = String.format("%s,%s,%s,%s,%d\n", sSO, sPrg, sGrf, sAdm, iHrs);
        
        // 3. Guardar en archivo de texto
        this.guardarArchivo(sRes);
        
        // 4. Guardar en la base de datos (solo si la conexión es válida)
        if (this.conexion != null) {
             this.guardarDB(sSO, sPrg, sGrf, sAdm, iHrs);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * Determina el sistema operativo seleccionado.
     * @return El nombre del SO seleccionado.
     */
    private String obtenerSistemaOperativo() {
        if (this.rbLnx.isSelected()) {
            return "Linux";
        }
        if (this.rbMac.isSelected()) {
            return "Mac";
        }
        // Por defecto o si rbWin está seleccionado
        return "Windows";
    }
    
    /**
     * Guarda la cadena de resultados en un archivo de texto llamado 'respuestas.txt'.
     * @param data La cadena a guardar (incluye salto de línea).
     */
    private void guardarArchivo(String data) {
        try (FileWriter w = new FileWriter("respuestas.txt", true)) {
            // El try-with-resources se encarga de cerrar automáticamente el FileWriter
            w.write(data);
            JOptionPane.showMessageDialog(this, "Respuesta guardada en respuestas.txt", "Encuesta", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar en el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            REGISTRO.log(Level.SEVERE, "Error al escribir en el archivo respuestas.txt", e);
        }
    }

    /**
     * Guarda los datos de la encuesta en la tabla 'respuestas' de la base de datos.
     * Utiliza PreparedStatement para prevenir la inyección SQL.
     */
    private void guardarDB(String sSO, String sPrg, String sGrf, String sAdm, int iHrs) {
        String query = "INSERT INTO respuestas (sSisOper, cProgra, cDiseno, cAdmon, iHoras) VALUES (?, ?, ?, ?, ?)";
        
        // Usamos try-with-resources para asegurar que el PreparedStatement se cierre
        try (PreparedStatement stmt = this.conexion.prepareStatement(query)) {
            
            stmt.setString(1, sSO);
            stmt.setString(2, sPrg);
            stmt.setString(3, sGrf);
            stmt.setString(4, sAdm);
            stmt.setInt(5, iHrs);
            
            // Ejecuta la sentencia de inserción
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                 JOptionPane.showMessageDialog(this, "Respuesta guardada en la Base de Datos", "Encuesta", JOptionPane.INFORMATION_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(this, "Error: No se pudo insertar en la BD", "Error de BD", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException ex) {
            REGISTRO.log(Level.SEVERE, "Error al insertar en la BD", ex);
            JOptionPane.showMessageDialog(this, 
                    "Error SQL al guardar en la BD: " + ex.getMessage(), 
                    "Error de BD", 
                    JOptionPane.ERROR_MESSAGE);
        }      
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the System Look and Feel */
        try {
            // Se usa el Look and Feel del sistema para una apariencia nativa
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.UIManager.getSystemLookAndFeelClassName());
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException ex) {
            REGISTRO.log(Level.SEVERE, "Error al configurar el Look and Feel del sistema", ex);
        }
        
        /* Crear y mostrar el formulario */
        java.awt.EventQueue.invokeLater(() -> new EncuestaPreferencias().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox ckAdm;
    private javax.swing.JCheckBox ckGrf;
    private javax.swing.JCheckBox ckPrg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbHoras;
    private javax.swing.JRadioButton rbLnx;
    private javax.swing.JRadioButton rbMac;
    private javax.swing.JRadioButton rbWin;
    private javax.swing.JSlider slHoras;
    // End of variables declaration//GEN-END:variables
}