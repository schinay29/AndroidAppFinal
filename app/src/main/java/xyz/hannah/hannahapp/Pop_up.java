package xyz.hannah.hannahapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import xyz.hannah.hannahapp.ClasesAyuda.Coche;

public class Pop_up extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ImageView mImagen;
    private TextView mNombre;
    private EditText mModelo, mUltVez;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        mImagen = findViewById(R.id.img_icon);
        mNombre = findViewById(R.id.txtNombre);
        mModelo = findViewById(R.id.cp_modelo);
        mUltVez = findViewById(R.id.cp_ultVez);
        obtenerDatos();


        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.85), (int) (alto * 0.5));

    }

    private void obtenerDatos(){
        Intent intent = getIntent();

        Bundle parametros = this.getIntent().getExtras();
        int imagen =  parametros.getInt("imagen");
        String nombre = parametros.getString("nombre");
        String modelo = parametros.getString("modelo");
        //String ultVez = parametros.getString("ultVez");

        mImagen.setImageResource(imagen);
        mNombre.setText(nombre);
        mModelo.setText(modelo);
        //mUltVez.setText(ultVez);

    }

    public void activarCampos(View view) {
        mModelo.setEnabled(true);
        mUltVez.setEnabled(true);
    }

    public void closeLayout(View view) {
        finish();
    }
}