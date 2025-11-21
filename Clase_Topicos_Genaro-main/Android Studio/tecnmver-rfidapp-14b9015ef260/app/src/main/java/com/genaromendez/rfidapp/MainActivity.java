package com.genaromendez.rfidapp;

import android.Manifest;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import java.io.OutputStream;
import java.util.List;

import android.app.AlertDialog;
import java.util.Set;
import java.util.ArrayList;
import android.widget.ArrayAdapter; // Si quisiéramos un adaptador custom, pero usaremos el nativo del dialogo
import android.content.DialogInterface;

public class MainActivity extends AppCompatActivity {

    // UI Components
    private TextView lblStatus, txtRfidDisplay;
    private EditText etArticulo, etDesc, etCant;
    private Button btnConnect, btnGuardar;
    private Button btnExportar; // Nueva variable

    // Bluetooth Components
    private static final UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothManager bluetoothManager;
    private BluetoothSocket btSocket = null;
    private boolean isConnected = false;

    // Database
    private AppDatabase database;

    // Handler para UI
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar DB
        database = AppDatabase.getDatabase(this);

        // Inicializar UI Views
        lblStatus = findViewById(R.id.lblStatus);
        txtRfidDisplay = findViewById(R.id.txtRfidDisplay);
        etArticulo = findViewById(R.id.etArticulo);
        etDesc = findViewById(R.id.etDesc);
        etCant = findViewById(R.id.etCant);
        btnConnect = findViewById(R.id.btnConnect);
        btnGuardar = findViewById(R.id.btnGuardar);

        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        // En el método onCreate...

        btnConnect.setOnClickListener(v -> {
            // Lógica ANTIGUA (Borrar o comentar):
            // String deviceAddress = "00:11:22:33:44:55";
            // connectToReader(deviceAddress);

            // Lógica NUEVA:
            mostrarListaDispositivos();
        });

        btnGuardar.setOnClickListener(v -> guardarRegistro());

        // Bind del nuevo botón
        btnExportar = findViewById(R.id.btnExportar);

        // Listener para el botón Exportar
        btnExportar.setOnClickListener(v -> iniciarExportacion());

    }

    // Método para mostrar el diálogo de selección
    private void mostrarListaDispositivos() {
        // 1. Verificar si el Bluetooth está encendido
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "Por favor, enciende el Bluetooth", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. Verificar Permisos (Necesario para Android 12+)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // Si no hay permiso, lo pedimos (esto es simplificado, en prod debes manejar el callback)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
            return; // Salimos y esperamos que el usuario acepte para la proxima
        }

        // 3. Obtener dispositivos emparejados
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // Preparamos dos listas: una para mostrar nombres y otra para guardar las MACs
            final String[] namesDisplay = new String[pairedDevices.size()];
            final String[] macAddresses = new String[pairedDevices.size()];

            int index = 0;
            for (BluetoothDevice device : pairedDevices) {
                namesDisplay[index] = device.getName() + "\n" + device.getAddress();
                macAddresses[index] = device.getAddress();
                index++;
            }

            // 4. Construir y mostrar el Alerta (Dialog)
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Selecciona tu Lector RFID");

            builder.setItems(namesDisplay, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 'which' es el índice del elemento clickeado
                    String macSeleccionada = macAddresses[which];

                    // Llamamos a tu función de conexión existente
                    lblStatus.setText("Conectando a " + macSeleccionada + "...");
                    connectToReader(macSeleccionada);
                }
            });

            // Botón de cancelar
            builder.setNegativeButton("Cancelar", null);

            builder.show();

        } else {
            Toast.makeText(this, "No hay dispositivos emparejados. Ve a Ajustes Bluetooth y vincula el lector primero.", Toast.LENGTH_LONG).show();
        }
    }

    private void iniciarExportacion() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/csv"); // Tipo MIME
        intent.putExtra(Intent.EXTRA_TITLE, "inventario_rfid.csv"); // Nombre por defecto

        // Lanzamos la petición
        exportarLauncher.launch(intent);
    }

    private void generarArchivoCSV(Uri uri) {
        // Ejecutamos en segundo plano porque consultaremos la BD y escribiremos disco
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                // A. Obtener datos de la BD
                List<RegistroInventario> lista = database.inventarioDao().obtenerTodos();

                // B. Construir el contenido CSV
                StringBuilder csvBuilder = new StringBuilder();

                // Encabezados de columnas
                csvBuilder.append("ID,Numero Articulo,Descripcion,Cantidad,Codigo RFID (Hex)\n");

                for (RegistroInventario reg : lista) {
                    // Importante: Escapar comas en la descripción para no romper el CSV
                    String descLimpia = reg.descripcion.replace(",", " ");

                    csvBuilder.append(reg.id).append(",");
                    csvBuilder.append(reg.numeroArticulo).append(",");
                    csvBuilder.append(descLimpia).append(",");
                    csvBuilder.append(reg.cantidad).append(",");
                    csvBuilder.append(reg.rfidHex).append("\n");
                }

                // C. Escribir en el URI seleccionado por el usuario
                try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
                    if (outputStream != null) {
                        outputStream.write(csvBuilder.toString().getBytes());
                    }
                }

                // D. Notificar éxito en UI
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "Exportación exitosa", Toast.LENGTH_LONG).show()
                );

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "Error al exportar: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    private void connectToReader(String address) {
        if (bluetoothAdapter == null) return;

        // Verificar permisos (básico)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // Manejar solicitud de permisos aquí si es Android 12+
            // return;
        }

        new Thread(() -> {
            try {
                BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
                btSocket = device.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
                btSocket.connect();

                isConnected = true;

                // Actualizar UI en el hilo principal
                runOnUiThread(() -> lblStatus.setText("Conectado"));

                // Iniciar escucha
                beginListenForData();

            } catch (IOException e) {
                isConnected = false;
                runOnUiThread(() -> {
                    lblStatus.setText("Error Conexión");
                    Toast.makeText(MainActivity.this, "Error conectando al socket", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

    private void beginListenForData() {
        final InputStream inputStream;
        try {
            inputStream = btSocket.getInputStream();
        } catch (IOException e) {
            return;
        }

        byte[] buffer = new byte[1024];
        int bytes;

        while (isConnected) {
            try {
                bytes = inputStream.read(buffer);
                if (bytes > 0) {
                    String readMessage = new String(buffer, 0, bytes);

                    // Procesar en el hilo principal
                    handler.post(() -> {
                        procesarLecturaRFID(readMessage);
                    });
                }
            } catch (IOException e) {
                isConnected = false;
                break;
            }
        }
    }

    private void procesarLecturaRFID(String data) {
        // Limpieza de caracteres basura (saltos de línea)
        String codigoLimpio = data.trim();
        if (!codigoLimpio.isEmpty()) {
            txtRfidDisplay.setText(codigoLimpio);
        }
    }

    private void guardarRegistro() {
        String art = etArticulo.getText().toString();
        String desc = etDesc.getText().toString();
        String cantStr = etCant.getText().toString();
        String rfid = txtRfidDisplay.getText().toString();

        if (art.isEmpty() || cantStr.isEmpty()) {
            Toast.makeText(this, "Complete los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        int cantidad = Integer.parseInt(cantStr);

        // Crear objeto
        RegistroInventario nuevoRegistro = new RegistroInventario(art, desc, cantidad, rfid);

        // Ejecutar inserción en Hilo Secundario (Database Executor)
        AppDatabase.databaseWriteExecutor.execute(() -> {
            database.inventarioDao().insertarRegistro(nuevoRegistro);

            // Volver a UI para confirmar
            runOnUiThread(() -> {
                Toast.makeText(MainActivity.this, "Registro Guardado!", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            });
        });
    }

    private void limpiarCampos() {
        etArticulo.setText("");
        etDesc.setText("");
        etCant.setText("");
        txtRfidDisplay.setText("Esperando lectura...");
        etArticulo.requestFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (btSocket != null) btSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final ActivityResultLauncher<Intent> exportarLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    if (uri != null) {
                        generarArchivoCSV(uri);
                    }
                }
            }
    );
}