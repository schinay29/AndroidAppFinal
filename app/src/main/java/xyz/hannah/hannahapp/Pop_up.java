package xyz.hannah.hannahapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
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

import java.io.IOException;

import xyz.hannah.hannahapp.ClasesAyuda.Coche;

public class Pop_up extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ImageView mImagen, mImgEdit;
    private TextView mNombre, mText;
    private EditText mModelo, mUltVez;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        mImagen = findViewById(R.id.img_icon);
        mNombre = findViewById(R.id.txtNombre);
        mModelo = findViewById(R.id.cp_modelo);
        mUltVez = findViewById(R.id.cp_ultVez);
        mText = findViewById(R.id.label_ultVez);
        mImgEdit = findViewById(R.id.imgEdit);
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

        if(imagen == R.mipmap.ic_coche0_foreground){
            mText.setText("Kilometros");
            mUltVez.setEnabled(true);
            mImgEdit.setVisibility(View.INVISIBLE);
        }else{
            mText.setText("última revisión");
            mImgEdit.setVisibility(View.VISIBLE);
        }
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

    public void aceptar(View view) {
        if(mText.getText().toString() == "Kilometros"){
            int kilometros = Integer.parseInt(mUltVez.getText().toString());

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                Toast.makeText(this, "holaaaa", Toast.LENGTH_SHORT).show();
                CharSequence name = "Notificacion";
                NotificationChannel notificationChannel = new NotificationChannel("NOTIFICACION", name, NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);

            }

                NotificationCompat.Builder mBuilder;

                mBuilder = new NotificationCompat.Builder(getApplicationContext(), "NOTIFICACION")
                        .setSmallIcon(R.mipmap.logo_app)
                        .setContentTitle("Aplicación Hannah")
                        .setContentText("Los kilometros se han cambiado")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setVibrate(new long[] {100, 250, 100, 500})
                        .setDefaults(Notification.DEFAULT_SOUND);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
                notificationManagerCompat.notify(0, mBuilder.build());



        }
    }
}