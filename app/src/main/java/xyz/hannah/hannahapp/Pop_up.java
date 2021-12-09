package xyz.hannah.hannahapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    Toast.makeText(Pop_up.this, snapshot.child(user.getUid()).child("Coche").child("modelo").getValue().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        obtenerDatos();



        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.85), (int) (alto * 0.5));

    }

    private void obtenerDatos(){
        Intent intent = getIntent();
        //Bitmap bitmap = (Bitmap) intent.getParcelableExtra("imagen");

        mImagen = findViewById(R.id.img_icon);

        //mImagen.setImageBitmap(bitmap);


        //mImagen.setImageResource(intent.getIntExtra("imagen"));

        Bundle parametros = this.getIntent().getExtras();
        int imagen =  parametros.getInt("imagen");

        mImagen.setImageResource(imagen);

        //Drawable d = mImagen.getDrawable();





    }
}