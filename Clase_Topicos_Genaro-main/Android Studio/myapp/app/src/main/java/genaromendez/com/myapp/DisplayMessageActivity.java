package genaromendez.com.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String nombre = intent.getStringExtra(MainActivity.EXTRA_NOM);
        String direccion = intent.getStringExtra(MainActivity.EXTRA_DIR);
        String telefono = intent.getStringExtra(MainActivity.EXTRA_TEL);

        String message = nombre+", "+direccion+", "+telefono;

        TextView textView = (TextView) findViewById(R.id.activity_display_message);
        try {
            textView.setText(message);
        } catch (NullPointerException e){
            textView.setText("No se recibio el parametro");
        }


    }
}
