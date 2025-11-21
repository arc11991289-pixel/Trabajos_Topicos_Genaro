package practica4;

import java.awt.Color;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestionUtilidades extends javax.swing.JFrame {
    
    private static final Logger REGISTRO = Logger.getLogger(GestionUtilidades.class.getName());

    public GestionUtilidades() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        labelInfo = new javax.swing.JLabel();
        campoRuta = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnElegirColor = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        labelInfo.setText("Presiona el botón para ubicar un fichero");

        btnBuscar.setText("Abrir");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionBuscarArchivo(evt);
            }
        });

        btnElegirColor.setText("Fondo");
        btnElegirColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionSeleccionarColor(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
                        .addComponent(btnElegirColor))
                    .addComponent(campoRuta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelInfo)
                    .addComponent(btnElegirColor))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }

    private void accionBuscarArchivo(java.awt.event.ActionEvent evt) {
        JFileChooser selector = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(
                "Imágenes PNG y BMP", "png", "bmp");
        selector.setFileFilter(filtro);
        int resultado = selector.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            this.campoRuta.setText(selector.getSelectedFile().getPath());
        }
    }

    private void accionSeleccionarColor(java.awt.event.ActionEvent evt) {
        Color colorInicial = new Color(255, 102, 0); 
        Color colorSeleccionado = JColorChooser.showDialog(this, "Seleccione un Color de Fondo", colorInicial);
        if (colorSeleccionado != null) {
            this.campoRuta.setBackground(colorSeleccionado);
        }
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
            REGISTRO.log(Level.WARNING, "Error al configurar L&F", ex);
        }

        java.awt.EventQueue.invokeLater(() -> new GestionUtilidades().setVisible(true));
    }

    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnElegirColor;
    private javax.swing.JTextField campoRuta;
    private javax.swing.JLabel labelInfo;
}