package practica5;

import javax.swing.ComboBoxModel;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControlEspejo extends javax.swing.JFrame {
    
    private static final Logger REGISTRO = Logger.getLogger(ControlEspejo.class.getName());

    ComboBoxModel<String> modeloCombo;
    
    public ControlEspejo() {
        initComponents();
        // Compartir el modelo de datos entre los componentes originales y los espejos
        modeloCombo = this.comboListaOriginal.getModel();
        this.comboListaEspejo.setModel(modeloCombo);
        
        // Compartir el modelo (valor) del Spinner
        this.spinnerValorEspejo.setModel(this.spinnerValorOriginal.getModel());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        grupoRadioBotones = new javax.swing.ButtonGroup();
        grupoRadioBotones2 = new javax.swing.ButtonGroup();
        labelPrincipal = new javax.swing.JLabel();
        labelSecundario = new javax.swing.JLabel();
        rbA1 = new javax.swing.JRadioButton();
        rbA2 = new javax.swing.JRadioButton();
        rbA3 = new javax.swing.JRadioButton();
        ckA4 = new javax.swing.JCheckBox();
        ckA5 = new javax.swing.JCheckBox();
        ckA6 = new javax.swing.JCheckBox();
        campoTextoOriginal = new javax.swing.JTextField();
        comboListaOriginal = new javax.swing.JComboBox<>();
        spinnerValorOriginal = new javax.swing.JSpinner();
        rbA_1 = new javax.swing.JRadioButton();
        rbA_2 = new javax.swing.JRadioButton();
        rbA_3 = new javax.swing.JRadioButton();
        ckA_4 = new javax.swing.JCheckBox();
        ckA_5 = new javax.swing.JCheckBox();
        ckA_6 = new javax.swing.JCheckBox();
        campoTextoEspejo = new javax.swing.JTextField();
        comboListaEspejo = new javax.swing.JComboBox<>();
        spinnerValorEspejo = new javax.swing.JSpinner();
        separador = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Control de Componentes");

        labelPrincipal.setText("Control Maestro");

        labelSecundario.setText("Vista Espejo");

        grupoRadioBotones.add(rbA1);
        rbA1.setSelected(true);
        rbA1.setText("Alternativa A");
        rbA1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sincronizarRadioBotonA1(evt);
            }
        });

        grupoRadioBotones.add(rbA2);
        rbA2.setText("Alternativa B");
        rbA2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sincronizarRadioBotonA2(evt);
            }
        });

        grupoRadioBotones.add(rbA3);
        rbA3.setText("Alternativa C");
        rbA3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sincronizarRadioBotonA3(evt);
            }
        });

        ckA4.setText("Estado 4");
        ckA4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sincronizarCheckA4(evt);
            }
        });

        ckA5.setText("Estado 5");
        ckA5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sincronizarCheckA5(evt);
            }
        });

        ckA6.setText("Estado 6");
        ckA6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sincronizarCheckA6(evt);
            }
        });

        campoTextoOriginal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                manejarTextoYEnter(evt);
            }
        });

        comboListaOriginal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                // Sincronización del ComboBox no es necesaria ya que comparten el mismo modelo.
            }
        });
        
        spinnerValorOriginal.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                // Sincronización del Spinner no es necesaria ya que comparten el mismo modelo.
            }
        });

        grupoRadioBotones2.add(rbA_1);
        rbA_1.setSelected(true);
        rbA_1.setText("Alternativa A");
        rbA_1.setEnabled(false);

        grupoRadioBotones2.add(rbA_2);
        rbA_2.setText("Alternativa B");
        rbA_2.setEnabled(false);

        grupoRadioBotones2.add(rbA_3);
        rbA_3.setText("Alternativa C");
        rbA_3.setEnabled(false);

        ckA_4.setText("Estado 4");
        ckA_4.setEnabled(false);

        ckA_5.setText("Estado 5");
        ckA_5.setEnabled(false);

        ckA_6.setText("Estado 6");
        ckA_6.setEnabled(false);

        campoTextoEspejo.setEnabled(false);

        comboListaEspejo.setEnabled(false);

        spinnerValorEspejo.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(labelPrincipal))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(labelSecundario))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rbA_3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ckA_6))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(rbA_2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ckA_5))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(rbA_1)
                                        .addGap(20, 20, 20)
                                        .addComponent(ckA_4)))
                                .addGap(35, 35, 35)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(campoTextoEspejo)
                                    .addComponent(comboListaEspejo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(spinnerValorEspejo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rbA3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ckA6))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(rbA2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ckA5))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(rbA1)
                                        .addGap(20, 20, 20)
                                        .addComponent(ckA4)))
                                .addGap(35, 35, 35)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(campoTextoOriginal)
                                    .addComponent(comboListaOriginal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(spinnerValorOriginal, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(separador, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(labelPrincipal)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbA1)
                    .addComponent(ckA4)
                    .addComponent(campoTextoOriginal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbA2)
                    .addComponent(ckA5)
                    .addComponent(comboListaOriginal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbA3)
                    .addComponent(ckA6)
                    .addComponent(spinnerValorOriginal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(separador, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(labelSecundario)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbA_1)
                    .addComponent(ckA_4)
                    .addComponent(campoTextoEspejo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbA_2)
                    .addComponent(ckA_5)
                    .addComponent(comboListaEspejo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbA_3)
                    .addComponent(ckA_6)
                    .addComponent(spinnerValorEspejo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>

    /**
     * Sincroniza el estado de los Radio Buttons (Maestro -> Espejo).
     */
    private void sincronizarRadioBotonA1(java.awt.event.ActionEvent evt) {
        this.rbA_1.setSelected(this.rbA1.isSelected());
    }

    private void sincronizarRadioBotonA2(java.awt.event.ActionEvent evt) {
        this.rbA_2.setSelected(this.rbA2.isSelected());
    }

    private void sincronizarRadioBotonA3(java.awt.event.ActionEvent evt) {
        this.rbA_3.setSelected(this.rbA3.isSelected());
    }

    /**
     * Sincroniza el estado de los Check Boxes (Maestro -> Espejo).
     */
    private void sincronizarCheckA4(java.awt.event.ActionEvent evt) {
        this.ckA_4.setSelected(this.ckA4.isSelected());
    }

    private void sincronizarCheckA5(java.awt.event.ActionEvent evt) {
        this.ckA_5.setSelected(this.ckA5.isSelected());
    }

    private void sincronizarCheckA6(java.awt.event.ActionEvent evt) {
        this.ckA_6.setSelected(this.ckA6.isSelected());
    }

    /**
     * Maneja la entrada de texto y la acción al presionar Enter.
     * Sincroniza el texto. Si se presiona ENTER, añade el texto al ComboBox.
     */
    private void manejarTextoYEnter(java.awt.event.KeyEvent evt) {
        // Sincroniza el contenido del campo de texto
        this.campoTextoEspejo.setText(this.campoTextoOriginal.getText());
        
        // Si se presiona ENTER, añade el contenido del campo de texto a la lista
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String nuevoItem = this.campoTextoOriginal.getText();
            if (!nuevoItem.trim().isEmpty()) {
                this.comboListaOriginal.addItem(nuevoItem);
                this.campoTextoOriginal.setText(""); // Opcional: limpiar después de añadir
            }
        }
    }

    // El método tfTextoXKeyTyped fue eliminado ya que solo contenía código de depuración.

    public static void main(String args[]) {
        try {
            // Se cambió "Nimbus" a "Metal" para reflejar el cambio en el XML anterior, y se corrigió el manejo de excepciones.
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            REGISTRO.log(Level.SEVERE, "Error al configurar el Look and Feel", ex);
        }

        java.awt.EventQueue.invokeLater(() -> new ControlEspejo().setVisible(true));
    }

    // Variables declaration - do not modify
    private javax.swing.ButtonGroup grupoRadioBotones;
    private javax.swing.ButtonGroup grupoRadioBotones2;
    private javax.swing.JTextField campoTextoOriginal;
    private javax.swing.JTextField campoTextoEspejo;
    private javax.swing.JCheckBox ckA4;
    private javax.swing.JCheckBox ckA_4;
    private javax.swing.JCheckBox ckA5;
    private javax.swing.JCheckBox ckA_5;
    private javax.swing.JCheckBox ckA6;
    private javax.swing.JCheckBox ckA_6;
    private javax.swing.JComboBox<String> comboListaOriginal;
    private javax.swing.JComboBox<String> comboListaEspejo;
    private javax.swing.JLabel labelPrincipal;
    private javax.swing.JLabel labelSecundario;
    private javax.swing.JRadioButton rbA1;
    private javax.swing.JRadioButton rbA_1;
    private javax.swing.JRadioButton rbA2;
    private javax.swing.JRadioButton rbA_2;
    private javax.swing.JRadioButton rbA3;
    private javax.swing.JRadioButton rbA_3;
    private javax.swing.JSeparator separador;
    private javax.swing.JSpinner spinnerValorOriginal;
    private javax.swing.JSpinner spinnerValorEspejo;
    // End of variables declaration
}