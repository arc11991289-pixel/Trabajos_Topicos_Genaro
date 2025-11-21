package practica1;

import javax.swing.JOptionPane;

public class AppSaludo extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger REGISTRADOR = java.util.logging.Logger.getLogger(AppSaludo.class.getName());

    public AppSaludo() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {

        etiquetaIndicacion = new javax.swing.JLabel();
        campoTextoEntrada = new javax.swing.JTextField();
        botonLanzarSaludo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        etiquetaIndicacion.setText("Ingresa el nombre de la persona");

        botonLanzarSaludo.setText("Enviar Saludo");
        botonLanzarSaludo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manejarBotonSaludo(evt);
            }
        });

        javax.swing.GroupLayout diseño = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(diseño);
        diseño.setHorizontalGroup(
            diseño.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(diseño.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(etiquetaIndicacion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, diseño.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(campoTextoEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(diseño.createSequentialGroup()
                .addGap(135, 135, 135)
                .addComponent(botonLanzarSaludo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        diseño.setVerticalGroup(
            diseño.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(diseño.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(etiquetaIndicacion)
                .addGap(30, 30, 30)
                .addComponent(campoTextoEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(botonLanzarSaludo)
                .addGap(35, 35, 35))
        );

        pack();
    }

    private void manejarBotonSaludo(java.awt.event.ActionEvent evt) {
        String mensaje = "Saludos cordiales, "+campoTextoEntrada.getText()+"!";
        
        JOptionPane.showMessageDialog(this, mensaje,
             "Aviso Importante", JOptionPane.showInternalMessageDialog(this, mensaje));
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
            REGISTRADOR.log(java.util.logging.Level.WARNING, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new AppSaludo().setVisible(true));
    }

    private javax.swing.JButton botonLanzarSaludo;
    private javax.swing.JLabel etiquetaIndicacion;
    private javax.swing.JTextField campoTextoEntrada;
}