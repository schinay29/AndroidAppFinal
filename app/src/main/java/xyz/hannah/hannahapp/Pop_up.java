package xyz.hannah.hannahapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import xyz.hannah.hannahapp.ClasesAyuda.Coche;
import xyz.hannah.hannahapp.ClasesAyuda.PartOfCar;
import xyz.hannah.hannahapp.ClasesAyuda.PlantillaPartOfCar;

public class Pop_up extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ImageView mImagen, mImgEdit;
    private TextView mNombre, mText;
    private EditText mModelo, mUltVez;
    private PlantillaPartOfCar partOfCar;


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

        // obtengo el objeto coche enviado desde la actividad anterior
        partOfCar = (PlantillaPartOfCar) getIntent().getSerializableExtra("PlantillaPartOfCar");

        if(partOfCar.getIdImagen() == R.mipmap.ic_coche0_foreground){
            mText.setText("Kilometros");
            mUltVez.setEnabled(true);
            mImgEdit.setVisibility(View.INVISIBLE);
        }else{
            mText.setText("última revisión");
            mImgEdit.setVisibility(View.VISIBLE);
        }
        mImagen.setImageResource(partOfCar.getIdImagen());
        mNombre.setText(partOfCar.getNombre());
        mModelo.setText(partOfCar.getModelo());
        mUltVez.setText(partOfCar.getUltFechaCambio());

    }

    public void activarCampos(View view) {
        mModelo.setEnabled(true);
        mUltVez.setEnabled(true);
    }

    public void closeLayout(View view) {
        finish();
    }

    public void aceptar(View view) {

        String txt_notificacion = "Aún no toca revisión";

        if(mText.getText().toString() == "Kilometros"){
            if(!mUltVez.getText().toString().isEmpty()) {
                int kilometros = Integer.parseInt(mUltVez.getText().toString());

                if (kilometros >= 10000 && kilometros <= 12000) {
                    //revisar neumáticos y aceite del motor
                    txt_notificacion = "revisar neumáticos y aceite del motor\n";

                } else if (kilometros >= 20000 && kilometros <= 22000) {
                    // aceite, el filtro de aceite y los filtros de aire y habitáculo, y revisar las pastillas de freno, el estado de la batería
                    txt_notificacion = "cambiar aceite, el filtro de aceite y los filtros de aire y habitáculo, y revisar las pastillas de freno y el estado de la batería\n";

                } else if (kilometros >= 30000 && kilometros <= 32000) {
                    //cambio de las pastillas de frenos
                    //revisión de los discos, amortiguadores y manguitos, una evaluación del sistema del aire acondicionado
                    txt_notificacion = "cambiar las pastillas de frenos y revisión de los discos, amortiguadores y manguitos\n";

                } else if (kilometros >= 50000 && kilometros <= 52000) {
                    //Toca cambiar el aceite, los filtros de aceite o combustible, las pastillas de freno,
                    // el líquido de frenos y del embrague, las bujías de encendido, correa de distribución, el refrigerante del radiador...
                    txt_notificacion = "cambiar el aceite, los filtros de aceite o combustible, las pastillas de freno, el líquido de frenos y del embrague, las bujías de encendido, correa de distribución, el refrigerante del radiador...\n";
                }

            }

            Toast.makeText(this, "Información actualizada", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    CharSequence name = "Notificacion";
                    NotificationChannel notificationChannel = new NotificationChannel("NOTIFICACION", name, NotificationManager.IMPORTANCE_HIGH);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(notificationChannel);

                }

            Intent intent = new Intent(Pop_up.this, activity_home.class);
            PendingIntent pi = PendingIntent.getActivity(Pop_up.this, 0, intent, 0);

                NotificationCompat.Builder mBuilder;

                mBuilder = new NotificationCompat.Builder(getApplicationContext(), "NOTIFICACION")
                        .setSmallIcon(R.mipmap.logo_app)
                        .setContentTitle("Recuerda...")
                        .setContentText(txt_notificacion)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(txt_notificacion))
                        .setContentIntent(pi)
                        .setAutoCancel(true)
                        .setVibrate(new long[]{Notification.DEFAULT_VIBRATE})
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setPriority(NotificationCompat.PRIORITY_MAX);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
                notificationManagerCompat.notify(0, mBuilder.build());

                finish();





        }
    }
}