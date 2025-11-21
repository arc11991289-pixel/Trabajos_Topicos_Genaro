package com.genaromendez.rfidapp;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {RegistroInventario.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract InventarioDao inventarioDao();

    private static volatile AppDatabase INSTANCE;

    // Executor para realizar operaciones de BD en segundo plano (Fundamental en Java)
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "inventario_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
