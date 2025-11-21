package practica8;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Visor y editor de datos de la encuesta en un JTable, con actualización
 * automática a la base de datos al editar una celda.
 */
public class EncuestaTbl extends javax.swing.JFrame {

    private static final Logger REGISTRO = Logger.getLogger(EncuestaTbl.class.getName());

    // Constantes de conexión a la BD
    private final String URL = "jdbc:mysql://localhost:3306/encuesta";
    private final String USER = "encuesta_user";
    private final String PASSWORD = "encuesta_pass";

    private Connection conexion;
    private DefaultTableModel tblModelo; // Renombrado para claridad

    // Bandera para evitar que la actualización de la tabla desencadene otra actualización
    private boolean tablaEscuchando = true; 

    /**
     * Creates new form EncuestaTbl
     */
    public EncuestaTbl() {
        initComponents();
        this.setLocationRelativeTo(null);

        // Inicializar el modelo
        tblModelo = (DefaultTableModel) tblResp.getModel();

        this.conectarBaseDeDatos();
        this.cargarDatosTabla(); // Cargar datos iniciales
        this.configurarListenerTabla(); // Configurar el listener DESPUÉS de cargar datos
    }

    /**
     * Configura el TableModelListener para capturar eventos de edición.
     */
    private void configurarListenerTabla() {
        tblModelo.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // Solo actuamos ante actualizaciones y si la tabla está "escuchando"
                if (e.getType() == TableModelEvent.UPDATE && tablaEscuchando) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    
                    REGISTRO.log(Level.INFO, "Celda actualizada en renglón: {0}, columna: {1}", new Object[]{row, column});

                    // 1. Obtener todos los datos de la fila (incluyendo el ID)
                    try {
                        int id = (int) tblModelo.getValueAt(row, 0);
                        String sSO = (String) tblModelo.getValueAt(row, 1);
                        String sPrg = (String) tblModelo.getValueAt(row, 2);
                        String sDis = (String) tblModelo.getValueAt(row, 3);
                        String sAdm = (String) tblModelo.getValueAt(row, 4);
                        // Se valida el tipo de dato antes de castear
                        int iHrs = Integer.parseInt(tblModelo.getValueAt(row, 5).toString());

                        // 2. Realizar la actualización en la base de datos de forma segura
                        actualizarRegistroBD(id, sSO, sPrg, sDis, sAdm, iHrs);
                        
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(EncuestaTbl.this, 
                                "Error de formato de número. Asegúrate de que Horas sea un número entero.", 
                                "Error de Datos", JOptionPane.ERROR_MESSAGE);
                        REGISTRO.log(Level.WARNING, "Error al convertir a entero en la columna Horas.", ex);
                        // Se recarga la tabla para revertir el valor no válido en la GUI
                        cargarDatosTabla(); 
                    } catch (ClassCastException ex) {
                         JOptionPane.showMessageDialog(EncuestaTbl.this, 
                                "Error de tipo de dato. Verifica los valores de las columnas.", 
                                "Error de Datos", JOptionPane.ERROR_MESSAGE);
                        REGISTRO.log(Level.WARNING, "Error de casteo al obtener valores de la tabla.", ex);
                        cargarDatosTabla(); 
                    }
                }
            }
        });
    }

    /**
     * Actualiza un registro en la base de datos usando PreparedStatement.
     * @param id El ID del registro a actualizar.
     * @param sSO Sistema Operativo.
     * @param sPrg Programación.
     * @param sDis Diseño.
     * @param sAdm Administración.
     * @param iHrs Horas.
     */
    private void actualizarRegistroBD(int id, String sSO, String sPrg, String sDis, String sAdm, int iHrs) {
        if (this.conexion == null) return;
        
        // Sentencia SQL segura utilizando placeholders (?)
        String sentUpd = "UPDATE respuestas SET sSisOper=?, cProgra=?, cDiseno=?, cAdmon=?, iHoras=? WHERE idRespuesta=?";

        try (PreparedStatement pstmt = this.conexion.prepareStatement(sentUpd)) {
            // Asignación de parámetros
            pstmt.setString(1, sSO);
            pstmt.setString(2, sPrg);
            pstmt.setString(3, sDis);
            pstmt.setString(4, sAdm);
            pstmt.setInt(5, iHrs);
            pstmt.setInt(6, id); // WHERE idRespuesta = id
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                 REGISTRO.log(Level.INFO, "Registro ID {0} actualizado en BD.", id);
                 // No recargamos la tabla aquí para evitar el loop, la recargamos solo en el botón de Refresh si es necesario
                 JOptionPane.showMessageDialog(this, "Registro actualizado en la BD.", "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(this, "Advertencia: No se encontró el ID para actualizar.", "Error BD", JOptionPane.WARNING_MESSAGE);
            }
            
        } catch (SQLException sqle) {
            REGISTRO.log(Level.SEVERE, "Error al actualizar registro ID " + id + " en BD.", sqle);
            JOptionPane.showMessageDialog(this, 
                    "Error SQL al actualizar: " + sqle.getMessage(), 
                    "Error de Actualización", JOptionPane.ERROR_MESSAGE);
            // Si hay un error, recargamos para restaurar el valor original en la GUI
            cargarDatosTabla();
        }
    }


    /**
     * Conecta a la base de datos MySQL.
     */
    private void conectarBaseDeDatos() {
        try {
            this.conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            REGISTRO.log(Level.INFO, "Conexión a MySQL establecida con éxito!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error en conexión con la BD: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            REGISTRO.log(Level.SEVERE, "Error al conectar a MySQL", e);
            this.conexion = null;
        }
    }

    /**
     * Carga todos los registros de la tabla 'respuestas' en el JTable.
     * Renombrado de doUpdate() a cargarDatosTabla().
     */
    private void cargarDatosTabla() {
        if (this.conexion == null) {
            JOptionPane.showMessageDialog(this, "No hay conexión activa con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Desactivamos el listener temporalmente para que el setRowCount(0) y addRow no activen el evento UPDATE
        tablaEscuchando = false; 

        tblModelo.setRowCount(0); // Limpia las filas existentes

        String sQuery = "SELECT idRespuesta, sSisOper, cProgra, cDiseno, cAdmon, iHoras FROM respuestas";

        // Uso de try-with-resources para asegurar el cierre de Statement y ResultSet
        try (Statement stmt = this.conexion.createStatement();
             ResultSet rset = stmt.executeQuery(sQuery)) 
        {
            while (rset.next()) {
                Object objArray[] = new Object[6];
                objArray[0] = rset.getInt("idRespuesta"); // Usar el nombre real de la columna ID
                objArray[1] = rset.getString("sSisOper");
                objArray[2] = rset.getString("cProgra");
                objArray[3] = rset.getString("cDiseno");
                objArray[4] = rset.getString("cAdmon");
                objArray[5] = rset.getInt("iHoras");

                tblModelo.addRow(objArray);
            }
        } catch (SQLException e) {
            REGISTRO.log(Level.SEVERE, "Error en consulta de datos.", e);
            JOptionPane.showMessageDialog(this, "Error en consulta: " + e.getMessage(), "Error de Consulta", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Reactivamos el listener
            tablaEscuchando = true;
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblResp = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Editor de Respuestas (DB)");

        tblResp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Sist. Op.", "Prog.", "Diseño", "Admon.", "Horas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblResp);

        btnRefresh.setText("Refrescar");
        btnRefresh.setActionCommand("btnRefreshAction");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRefresh)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(btnRefresh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        this.cargarDatosTabla();
    }//GEN-LAST:event_btnRefreshActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            REGISTRO.log(Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new EncuestaTbl().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblResp;
    // End of variables declaration//GEN-END:variables
}