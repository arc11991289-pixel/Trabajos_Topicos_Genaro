package com.genaromendez.rfidapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tabla_inventario")
public class RegistroInventario {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String numeroArticulo;
    public String descripcion;
    public int cantidad;
    public String rfidHex;

    // Constructor vacío requerido por Room
    public RegistroInventario() {}

    // Constructor para facilitar la creación
    public RegistroInventario(String numeroArticulo, String descripcion, int cantidad, String rfidHex) {
        this.numeroArticulo = numeroArticulo;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.rfidHex = rfidHex;
    }

    // Getters y Setters son opcionales si los campos son públicos,
    // pero es buena práctica generarlos si los necesitas.
}
