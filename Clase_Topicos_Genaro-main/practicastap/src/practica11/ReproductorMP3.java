package practica11;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

// Requiere JavaFX en el proyecto para MP3 (o solo funcionará con WAV/Audio Nativo)
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.MediaPlayer;

/**
 * Interfaz y lógica para un Reproductor de Audio que soporta formatos nativos (WAV)
 * y formatos modernos (MP3) a través de JavaFX Media, usando hilos para el progreso.
 */
public class ReproductorMP3 extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(ReproductorMP3.class.getName());
    
    // El hilo de control de audio se crea una sola vez
    private final AudioPlayer hiloAudio;
    private File archivoActual;

    /**
     * Creates new form ReproductorMP3
     */
    public ReproductorMP3() {
        initComponents();
        this.setLocationRelativeTo(null);
        // Inicializar JavaFX toolkit (necesario para MediaPlayer)
        new JFXPanel(); 
        
        hiloAudio = new AudioPlayer();
        hiloAudio.start(); // Iniciar el hilo de actualización de UI inmediatamente
        
        actualizarEstadoBotones();
        Estado.setText("Estado: Inactivo");
        Tiempo.setText("00:00 / 00:00");
    }

    // --- Métodos de Interfaz (GUI) ---

    private void actualizarEstadoBotones() {
        boolean reproduciendo = hiloAudio.estaReproduciendo();
        boolean pausado = hiloAudio.estaPausado();
        boolean archivoCargado = (archivoActual != null);

        PlayPause.setEnabled(archivoCargado);
        Detener.setEnabled(archivoCargado && (reproduciendo || pausado));
        
        // Actualizar texto del botón: Play/Pause/Play
        if (reproduciendo) {
            PlayPause.setText("⏸️"); // Pausa
        } else {
            PlayPause.setText("▶️"); // Play/Reanudar
        }
    }

    private String formatearTiempo(long segundos) {
        if (segundos < 0) return "00:00";
        long min = segundos / 60;
        long seg = segundos % 60;
        return String.format("%02d:%02d", min, seg);
    }
    
    // --- Manejo de Eventos (Action Performed) ---

    private void PlayPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayPauseActionPerformed
        if (archivoActual == null) return;

        if (hiloAudio.estaReproduciendo()) {
            // Si está reproduciendo -> Pausar
            hiloAudio.pausar();
            Estado.setText("Estado: Pausado");
        } else if (hiloAudio.estaPausado()) {
            // Si está pausado -> Reanudar
            hiloAudio.reanudar();
            Estado.setText("Estado: Reproduciendo");
        } else {
            // Detenido o primera vez -> Reproducir
            hiloAudio.reproducir();
            Estado.setText("Estado: Reproduciendo");
        }
        actualizarEstadoBotones();
    }//GEN-LAST:event_PlayPauseActionPerformed

    private void SeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SeleccionarActionPerformed
        // Usar JFileChooser en lugar de la variable de componente
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar Archivo de Audio (MP3/WAV)");
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos de Audio", "mp3", "wav", "aiff", "m4a"));
        
        int resultado = fileChooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            // Detener el audio anterior si está corriendo
            hiloAudio.detener(); 
            
            archivoActual = fileChooser.getSelectedFile();
            
            // Cargar archivo y manejar posibles errores de formato
            boolean cargaExitosa = hiloAudio.cargarArchivo(archivoActual);
            
            if (cargaExitosa) {
                Archivo.setText("Archivo: " + archivoActual.getName());
                Estado.setText("Estado: Cargado");
                // Asegurar que la barra de progreso esté configurada
                BarraProgreso.setMaximum((int) hiloAudio.getDuracionTotal());
                BarraProgreso.setValue(0);
                Tiempo.setText("00:00 / " + formatearTiempo(hiloAudio.getDuracionTotal()));
            } else {
                archivoActual = null; // Reiniciar
                Archivo.setText("Archivo: Formato no soportado");
                Estado.setText("Estado: Error de formato");
                Tiempo.setText("00:00 / 00:00");
                BarraProgreso.setValue(0);
            }
            actualizarEstadoBotones();
        }
    }//GEN-LAST:event_SeleccionarActionPerformed

    private void DetenerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DetenerActionPerformed
        hiloAudio.detener();
        Estado.setText("Estado: Detenido");
        
        // Reiniciar la visualización
        Tiempo.setText("00:00 / " + formatearTiempo(hiloAudio.getDuracionTotal()));
        BarraProgreso.setValue(0);
        actualizarEstadoBotones();
    }//GEN-LAST:event_DetenerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the System look and feel */
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(Level.SEVERE, "Error al configurar el Look and Feel", ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ReproductorMP3().setVisible(true));
    }
    
    // --- CLASE INTERNA AudioPlayer (Thread) ---
    
    /**
     * Hilo responsable de cargar, controlar la reproducción de audio
     * y actualizar periódicamente la barra de progreso y tiempo de la GUI.
     */
    class AudioPlayer extends Thread {
        
        private Object player; // Puede ser Clip o MediaPlayer
        private boolean pausado = false;
        private long posicionPausa = 0; // Para Clip (en microsegundos)
        private String tipoArchivo;

        /**
         * Intenta cargar el archivo de audio.
         * @return true si la carga fue exitosa, false si falló o el formato no es compatible.
         */
        public boolean cargarArchivo(File archivo) {
            String nombre = archivo.getName().toLowerCase();
            detener(); // Detiene cualquier reproducción anterior

            try {
                // 1. Cargar el recurso adecuado
                if (nombre.endsWith(".wav") || nombre.endsWith(".aiff") || nombre.endsWith(".au")) {
                    tipoArchivo = "AUDIO_NATIVO";
                    cargarAudioNativo(archivo);
                } else if (nombre.endsWith(".mp3") || nombre.endsWith(".m4a") || nombre.endsWith(".aac")) {
                    tipoArchivo = "JAVAFX_MEDIA";
                    cargarJavaFXMedia(archivo);
                } else {
                    tipoArchivo = null;
                    player = null;
                    return false; // Formato no soportado
                }
                
                pausado = false;
                posicionPausa = 0;
                return true;
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error al cargar el archivo de audio.", e);
                // Notificar al usuario a través del EDT
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(ReproductorMP3.this, 
                        "Error al cargar el archivo: " + e.getMessage(), 
                        "Error de Carga", JOptionPane.ERROR_MESSAGE));
                return false;
            }
        }

        private void cargarAudioNativo(File archivo) throws Exception {
            if (player instanceof javax.sound.sampled.Clip) {
                ((javax.sound.sampled.Clip) player).close();
            }
            
            javax.sound.sampled.AudioInputStream audioInputStream = 
                javax.sound.sampled.AudioSystem.getAudioInputStream(archivo);
            javax.sound.sampled.Clip clip = javax.sound.sampled.AudioSystem.getClip();
            clip.open(audioInputStream);
            player = clip;
        }

        private void cargarJavaFXMedia(File archivo) {
            if (player instanceof javafx.scene.media.MediaPlayer) {
                ((javafx.scene.media.MediaPlayer) player).stop();
                ((javafx.scene.media.MediaPlayer) player).dispose(); // Liberar recursos
            }
            
            String uri = archivo.toURI().toString();
            javafx.scene.media.Media media = new javafx.scene.media.Media(uri);
            javafx.scene.media.MediaPlayer mediaPlayer = new javafx.scene.media.MediaPlayer(media);
            player = mediaPlayer;
            
            // Listener para cuando termine
            mediaPlayer.setOnEndOfMedia(() -> {
                // Se debe llamar a SwingUtilities.invokeLater ya que este listener no corre en el EDT
                SwingUtilities.invokeLater(() -> {
                    detener(); // Llamar a detener para reiniciar la posición
                    Estado.setText("Estado: Finalizado");
                    actualizarEstadoBotones();
                });
            });
            // JavaFX Media Player es asíncrono, hay que esperar a que esté READY para obtener la duración
            mediaPlayer.setOnReady(() -> {
                SwingUtilities.invokeLater(() -> {
                    BarraProgreso.setMaximum((int) getDuracionTotal());
                    Tiempo.setText("00:00 / " + formatearTiempo(getDuracionTotal()));
                });
            });
        }
        
        public void reproducir() {
            if (player == null) return;
            
            if (tipoArchivo.equals("AUDIO_NATIVO")) {
                javax.sound.sampled.Clip clip = (javax.sound.sampled.Clip) player;
                clip.setMicrosecondPosition(0);
                clip.start();
            } else if (tipoArchivo.equals("JAVAFX_MEDIA")) {
                javafx.scene.media.MediaPlayer mediaPlayer = (javafx.scene.media.MediaPlayer) player;
                mediaPlayer.stop();
                mediaPlayer.play();
            }
            pausado = false;
            posicionPausa = 0;
        }

        public void pausar() {
            if (player == null) return;
            
            if (tipoArchivo.equals("AUDIO_NATIVO")) {
                javax.sound.sampled.Clip clip = (javax.sound.sampled.Clip) player;
                posicionPausa = clip.getMicrosecondPosition();
                clip.stop();
            } else if (tipoArchivo.equals("JAVAFX_MEDIA")) {
                javafx.scene.media.MediaPlayer mediaPlayer = (javafx.scene.media.MediaPlayer) player;
                mediaPlayer.pause();
            }
            pausado = true;
        }

        public void reanudar() {
            if (player == null) return;
            
            if (tipoArchivo.equals("AUDIO_NATIVO")) {
                javax.sound.sampled.Clip clip = (javax.sound.sampled.Clip) player;
                clip.setMicrosecondPosition(posicionPausa);
                clip.start();
            } else if (tipoArchivo.equals("JAVAFX_MEDIA")) {
                javafx.scene.media.MediaPlayer mediaPlayer = (javafx.scene.media.MediaPlayer) player;
                mediaPlayer.play();
            }
            pausado = false;
        }

        public void detener() {
            if (player == null) return;
            
            if (tipoArchivo != null) {
                if (tipoArchivo.equals("AUDIO_NATIVO")) {
                    javax.sound.sampled.Clip clip = (javax.sound.sampled.Clip) player;
                    clip.stop();
                    clip.setMicrosecondPosition(0);
                } else if (tipoArchivo.equals("JAVAFX_MEDIA")) {
                    javafx.scene.media.MediaPlayer mediaPlayer = (javafx.scene.media.MediaPlayer) player;
                    mediaPlayer.stop();
                    mediaPlayer.seek(javafx.util.Duration.ZERO);
                }
            }
            pausado = false;
            posicionPausa = 0;
        }

        public boolean estaReproduciendo() {
            if (player == null) return false;
            
            if (tipoArchivo != null) {
                if (tipoArchivo.equals("AUDIO_NATIVO")) {
                    javax.sound.sampled.Clip clip = (javax.sound.sampled.Clip) player;
                    return clip.isRunning();
                } else if (tipoArchivo.equals("JAVAFX_MEDIA")) {
                    javafx.scene.media.MediaPlayer mediaPlayer = (javafx.scene.media.MediaPlayer) player;
                    return MediaPlayer.Status.PLAYING.equals(mediaPlayer.getStatus());
                }
            }
            return false;
        }

        public boolean estaPausado() {
            if (player == null) return false;
            
            if (tipoArchivo != null) {
                if (tipoArchivo.equals("AUDIO_NATIVO")) {
                    // Para Audio Nativo, dependemos de nuestra variable booleana 'pausado'
                    return pausado && !estaReproduciendo(); 
                } else if (tipoArchivo.equals("JAVAFX_MEDIA")) {
                    javafx.scene.media.MediaPlayer mediaPlayer = (javafx.scene.media.MediaPlayer) player;
                    return MediaPlayer.Status.PAUSED.equals(mediaPlayer.getStatus());
                }
            }
            return false;
        }

        public long getDuracionTotal() {
            if (player == null) return 0;
            
            if (tipoArchivo != null) {
                if (tipoArchivo.equals("AUDIO_NATIVO")) {
                    javax.sound.sampled.Clip clip = (javax.sound.sampled.Clip) player;
                    // Duración en microsegundos, convertir a segundos
                    return clip.getMicrosecondLength() / 1000000; 
                } else if (tipoArchivo.equals("JAVAFX_MEDIA")) {
                    javafx.scene.media.MediaPlayer mediaPlayer = (javafx.scene.media.MediaPlayer) player;
                    if (mediaPlayer.getTotalDuration() == null) return 0;
                    return (long) (mediaPlayer.getTotalDuration().toSeconds());
                }
            }
            return 0;
        }

        public long getPosicionActual() {
            if (player == null) return 0;
            
            if (tipoArchivo != null) {
                if (tipoArchivo.equals("AUDIO_NATIVO")) {
                    javax.sound.sampled.Clip clip = (javax.sound.sampled.Clip) player;
                    // Posición en microsegundos, convertir a segundos
                    return clip.getMicrosecondPosition() / 1000000; 
                } else if (tipoArchivo.equals("JAVAFX_MEDIA")) {
                    javafx.scene.media.MediaPlayer mediaPlayer = (javafx.scene.media.MediaPlayer) player;
                    if (mediaPlayer.getCurrentTime() == null) return 0;
                    return (long) (mediaPlayer.getCurrentTime().toSeconds());
                }
            }
            return 0;
        }

        @Override
        public void run() {
            // Hilo encargado de actualizar la interfaz de usuario periódicamente
            while (true) {
                try {
                    // Esperar medio segundo (500 ms)
                    Thread.sleep(500); 
                    
                    // Solo actualizar si está en reproducción activa
                    if (estaReproduciendo()) {
                        long posicionActual = getPosicionActual();
                        long duracionTotal = getDuracionTotal();
                        
                        // Actualizar la GUI en el Event Dispatch Thread (EDT)
                        SwingUtilities.invokeLater(() -> {
                            Tiempo.setText(formatearTiempo(posicionActual) + " / " + formatearTiempo(duracionTotal));
                            
                            if (duracionTotal > 0) {
                                int progreso = (int) ((posicionActual * 100) / duracionTotal);
                                BarraProgreso.setValue(progreso);
                            }
                        });
                    }
                } catch (InterruptedException ex) {
                    // El hilo se detiene si se interrumpe
                    logger.log(Level.WARNING, "Hilo de audio interrumpido.", ex);
                    Thread.currentThread().interrupt();
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Error en el hilo de actualización de UI.", ex);
                }
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Archivo;
    private javax.swing.JProgressBar BarraProgreso;
    private javax.swing.JButton Detener;
    private javax.swing.JLabel Estado;
    private javax.swing.JButton PlayPause;
    private javax.swing.JButton Seleccionar;
    private javax.swing.JLabel Tiempo;
    private javax.swing.JFileChooser jFileChooser1;
    // End of variables declaration//GEN-END:variables
}