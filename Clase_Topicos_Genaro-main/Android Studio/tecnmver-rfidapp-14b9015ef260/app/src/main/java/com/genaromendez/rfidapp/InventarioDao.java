package com.genaromendez.rfidapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface InventarioDao {
    @Insert
    void insertarRegistro(RegistroInventario registro);

    @Query("SELECT * FROM tabla_inventario ORDER BY id DESC")
    List<RegistroInventario> obtenerTodos();
}
