package genaromendez.com.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_NOM = "com.genaromendez.MyApp.NOM";
    public final static String EXTRA_DIR = "com.genaromendez.MyApp.DIR";
    public final static String EXTRA_TEL = "com.genaromendez.MyApp.TEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnAbrirActivityFoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cuando se invoca otra actividad desde una clase anonima,
                // utilizar v.getContext(), para obtener el origen desde
                // el cual se invocará a la siguiente Actividad
                Intent intent = new Intent(v.getContext(),FotoActivity.class);
                startActivity(intent);

            }
        });

        findViewById(R.id.btnAbrirActivityMapa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cuando se invoca otra actividad desde una clase anonima,
                // utilizar v.getContext(), para obtener el origen desde
                // el cual se invocará a la siguiente Actividad
                Intent intent = new Intent(v.getContext(),MapsActivity.class);
                startActivity(intent);

            }
        });

    }

    public void onClickBotonAccion(View view){
        Intent intent = new Intent(this,DisplayMessageActivity.class);

        EditText editNom = (EditText) findViewById(R.id.editNom);
        String nombre = editNom.getText().toString();

        EditText editDir = (EditText) findViewById(R.id.editDir);
        String direcc = editDir.getText().toString();

        EditText editTel = (EditText) findViewById(R.id.editTel);
        String telefono = editTel.getText().toString();

        intent.putExtra(EXTRA_NOM,nombre);
        intent.putExtra(EXTRA_DIR,direcc);
        intent.putExtra(EXTRA_TEL,telefono);

        startActivity(intent);

    }
}
