package practica10;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 * Aplicación de reloj digital concurrente que actualiza la hora
 * en un JTextField de forma segura (usando SwingUtilities.invokeLater).
 */
public class Reloj extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(Reloj.class.getName());
    
    // Instancia del hilo de reloj
    private final Timing hilo; 
    
    // Formato de hora estándar
    private final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Creates new form Reloj
     */
    public Reloj() {
        initComponents();
        this.setLocationRelativeTo(null);
        tfFechaHora.setEditable(false);
        tfFechaHora.setText(LocalTime.now().format(FORMATO));
        
        // El hilo se crea una vez y corre en el fondo.
        hilo = new Timing();
        // Iniciar el hilo inmediatamente, el control (display) lo manejan los botones.
        hilo.start();
        
        // Aseguramos que el botón Iniciar esté deshabilitado inicialmente si el reloj empieza corriendo
        btnIniciar.setEnabled(false);
        btnDetener.setEnabled(true);
    }

    // --- Métodos de Acción ---

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {
        hilo.displayOn();
        btnIniciar.setEnabled(false);
        btnDetener.setEnabled(true);
    }

    private void btnDetenerActionPerformed(java.awt.event.ActionEvent evt) {
        hilo.displayOff();
        btnIniciar.setEnabled(true);
        btnDetener.setEnabled(false);
        // Opcional: Mostrar un mensaje estático cuando se detiene
        tfFechaHora.setText("Reloj Pausado."); 
    }

    // --- Código de Componentes Generado por NetBeans ---

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfFechaHora = new javax.swing.JTextField();
        btnIniciar = new javax.swing.JButton();
        btnDetener = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reloj Concurrente");

        tfFechaHora.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnIniciar.setText("Iniciar");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        btnDetener.setText("Detener");
        btnDetener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetenerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnIniciar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDetener))
                    .addComponent(tfFechaHora, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(tfFechaHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.create