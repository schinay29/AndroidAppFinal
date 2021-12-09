package xyz.hannah.hannahapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class activity_home extends AppCompatActivity {

    private static final String EXTRA_IMAGE = "";
    private TextView nombre;
    private ImageView imagen;
    private LinearLayout linearLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




    }

    public void add(View view){

        startActivity(new Intent(activity_home.this, activity_profile.class));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showDetails(View view) {

        imagen = findViewById(view.getId());
        linearLayout = (LinearLayout)imagen.getParent();
        linearLayout.getChildAt(1);
        //imagen.getSourceLayoutResId();
        //imagen.getDrawable();
        //Bitmap bitmap=imagen.getDrawingCache();;

        nombre = findViewById(linearLayout.getChildAt(1).getId());

        nombre.getText().toString();

        imagen.setTag("image1");

        int resId = getResources().getIdentifier(String.valueOf(imagen.getTag()), "drawable", getPackageName());



        //linearLayout = findViewById(view.getId());
        Toast.makeText(activity_home.this, imagen.getTag() + "---------" + nombre.getText().toString() ,Toast.LENGTH_SHORT).show();
        int idImagen = 0;


        switch (view.getId()){
            case R.id.img_luces:
                idImagen = R.mipmap.ic_luces;

                break;
            case R.id.img_aceite:
                idImagen = R.mipmap.ic_aceite_lubricante;
                break;
            case R.id.img_bateria:
                idImagen = R.mipmap.ic_bateria;

                break;
            case R.id.img_catalizador:
                idImagen = R.mipmap.ic_catalizador;

                break;
            case R.id.img_amortiguador:
                idImagen = R.mipmap.ic_amortiguador;

                break;
            case R.id.img_correa_distribucion:
                idImagen = R.mipmap.ic_correoa_de_distribucion;

                break;
            case R.id.img_filtro:
                idImagen = R.mipmap.ic_filtro;

                break;
            case R.id.img_rueda:
                idImagen = R.mipmap.ic_rueda;

                break;
            case R.id.img_frenos:
                idImagen = R.mipmap.ic_frenos;

                break;
        }




        Intent intent = new Intent(activity_home.this,Pop_up.class);

        Bundle extras = new Bundle();

        intent.putExtra("nombre", nombre.getText().toString());

        extras.putInt("imagen", idImagen);

        intent.putExtras(extras);

        startActivity(intent);

        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cuadro de dialogo creado").setTitle("Cuadro de dialogo");
        AlertDialog dialog = builder.create();
        */

    }
}