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

import java.util.List;

import xyz.hannah.hannahapp.ClasesAyuda.Coche;
import xyz.hannah.hannahapp.ClasesAyuda.PartOfCar;
import xyz.hannah.hannahapp.ClasesAyuda.PlantillaPartOfCar;

public class Pop_up extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ImageView mImagen, mImgEdit;
    private TextView mNombre, mText;
    private EditText mModelo, mUltVez;
    private PlantillaPartOfCar partOfCar;
    Coche coche;


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

    /**
     * método que crea un pop-up personalizado
     * y recoge los datos para ponerlo en la plantilla
     */
    private void obtenerDatos(){
        int idImagen;
        String nombre, modelo, val;

        if(!getIntent().getExtras().isEmpty()) {
            Bundle parametros = this.getIntent().getExtras();
            if (parametros.getString("tipo_popup").equals("añadir")) {
                idImagen = parametros.getInt("imagen");
                nombre = parametros.getString("nombre");
                coche = (Coche) parametros.getSerializable("claseCoche");
                modelo = coche.getModelo();
                val = coche.getKilometros() + "";
                mText.setText("Kilometros");
                mUltVez.setEnabled(true);
                mImgEdit.setVisibility(View.INVISIBLE);

            } else {
                // obtengo el objeto coche enviado desde la actividad anterior
                partOfCar = (PlantillaPartOfCar) getIntent().getSerializableExtra("PlantillaPartOfCar");

                mText.setText("última revisión");
                mImgEdit.setVisibility(View.VISIBLE);

                idImagen = partOfCar.getIdImagen();
                nombre = partOfCar.getNombre();
                modelo = partOfCar.getModelo();
                val = partOfCar.getUltFechaCambio();
            }

            mImagen.setImageResource(idImagen);
            mNombre.setText(nombre);
            mModelo.setText(modelo);
            mUltVez.setText(val);
        }
    }

    /**
     * método que activa los EditText
     * @param view
     */
    public void activarCampos(View view) {
        mModelo.setEnabled(true);
        mUltVez.setEnabled(true);
    }

    /**
     * método que cierra el pop-up
     * @param view
     */
    public void closeLayout(View view) {
        finish();
    }

    /**
     * método que envia una notificación de revision de acuerdo a los valores añadidos en el kilometraje
     * @param view
     */
    public void aceptar(View view) {
        String txt_notificacion = "Aún no toca revisión";

        if(mText.getText().toString() == "Kilometros"){
            if(!mUltVez.getText().toString().isEmpty()) {
                int kilometros = Integer.parseInt(mUltVez.getText().toString());
                // guarda el texto en un String de acuerdo al número de kilometros que tenga el coche
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
            // genera una notificacion con el mensaje guardado anteriormente
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