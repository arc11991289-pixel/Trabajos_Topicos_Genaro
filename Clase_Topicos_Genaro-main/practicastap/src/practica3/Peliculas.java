package practica3;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class CatalogoSeries extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(CatalogoSeries.class.getName());

    public CatalogoSeries() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {

        labelTitulo = new javax.swing.JLabel();
        labelColeccion = new javax.swing.JLabel();
        campoEntrada = new javax.swing.JTextField();
        btnInsertar = new javax.swing.JButton();
        comboSeries = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        labelTitulo.setText("Ingresa el nombre de la serie");

        labelColeccion.setText("Mis Series");

        btnInsertar.setText("Insertar");
        btnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manejarBotonInsertar(evt);
            }
        });

        javax.swing.GroupLayout diseñoMarco = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(diseñoMarco);
        diseñoMarco.setHorizontalGroup(
            diseñoMarco.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(diseñoMarco.createSequentialGroup()
                .addGroup(diseñoMarco.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(diseñoMarco.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(diseñoMarco.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTitulo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addGroup(diseñoMarco.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelColeccion)
                            .addComponent(comboSeries, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(diseñoMarco.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(btnInsertar)))
                .addGap(45, 45, 45))
        );
        diseñoMarco.setVerticalGroup(
            diseñoMarco.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(diseñoMarco.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(diseñoMarco.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTitulo)
                    .addComponent(labelColeccion))
                .addGap(20, 20, 20)
                .addGroup(diseñoMarco.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboSeries, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(btnInsertar)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }

    private void manejarBotonInsertar(java.awt.event.ActionEvent evt) {
        
        String nuevoItem = this.campoEntrada.getText().trim();
        
        if (nuevoItem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo de texto no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return; 
        }

        DefaultComboBoxModel<String> modelo = (DefaultComboBoxModel<String>)this.comboSeries.getModel();

        for(int i = 0; i < modelo.getSize(); i++){
            String itemActual = modelo.getElementAt(i);
            if (nuevoItem.equalsIgnoreCase(itemActual)){
                JOptionPane.showMessageDialog(this, "El elemento ya existe en la lista.", "Validación", JOptionPane.INFORMATION_MESSAGE);
                this.campoEntrada.setText("");
                return;
            }
        }
        
        modelo.addElement(nuevoItem);
        this.campoEntrada.setText("");
        
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            LOG.log(java.util.logging.Level.WARNING, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new CatalogoSeries().setVisible(true));
    }

    private javax.swing.JButton btnInsertar;
    private javax.swing.JTextField campoEntrada;
    private javax.swing.JComboBox<String> comboSeries;
    private javax.swing.JLabel labelColeccion;
    private javax.swing.JLabel labelTitulo;
}