package xyz.hannah.hannahapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class activity_home extends AppCompatActivity {

    private TextView nombre;
    private ImageView imagen;
    private LinearLayout linearLayout;
    private DatabaseReference databaseReference;
    BottomNavigationView bottomNavigation;
    private String texto, modelo, ultVez;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigation = findViewById(R.id.bottom_nav);

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        break;

                    case R.id.nav_add:
                        //startActivity(new Intent(activity_home.this, activity_home.class));
                        break;

                    case R.id.nav_profile:
                        Intent intent = new Intent(activity_home.this, activity_profile.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });


    }

    public void add(View view){

        startActivity(new Intent(activity_home.this, activity_profile.class));

    }


    private String obtenerModelo(String nombreParte){
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    texto = snapshot.child(user.getUid()).child("Coche").child(nombreParte).child("modelo").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return texto;
    }


    private String obtenerUltVez(String nombreParte){
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    texto = snapshot.child(user.getUid()).child("Coche").child(nombreParte).child("ultFechaCambio").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return texto;
    }

    public void showDetails(View view) {

        imagen = findViewById(view.getId());
        linearLayout = (LinearLayout)imagen.getParent();
        nombre = findViewById(linearLayout.getChildAt(1).getId());
        nombre.getText().toString();

        int idImagen = 0;

        switch (view.getId()){
            case R.id.img_luces:
                idImagen = R.mipmap.ic_luces;
                modelo = obtenerModelo("luces");
                //ultVez = obtenerUltVez("luces");
                break;

            case R.id.img_aceite:
                idImagen = R.mipmap.ic_aceite_lubricante;
                modelo = obtenerModelo("aceite");
                //ultVez = obtenerUltVez("aceite");
                break;

            case R.id.img_bateria:
                idImagen = R.mipmap.ic_bateria;
                modelo = obtenerModelo("bateria");
                //ultVez = obtenerUltVez("bateria");
                break;

            case R.id.img_catalizador:
                idImagen = R.mipmap.ic_catalizador;
                modelo = obtenerModelo("sistemaDeEscape");
                //ultVez = obtenerUltVez("sistemaDeEscape");
                break;

            case R.id.img_amortiguador:
                idImagen = R.mipmap.ic_amortiguador;
                modelo = obtenerModelo("amortiguador");
                //ultVez = obtenerUltVez("amortiguador");
                break;

            case R.id.img_correa_distribucion:
                idImagen = R.mipmap.ic_correoa_de_distribucion;
                modelo = obtenerModelo("correaDistribucion");
                //ultVez = obtenerUltVez("correaDistribucion");
                break;

            case R.id.img_filtro:
                idImagen = R.mipmap.ic_filtro;
                modelo = obtenerModelo("filtro");
                //ultVez = obtenerUltVez("filtro");
                break;

            case R.id.img_rueda:
                idImagen = R.mipmap.ic_rueda;
                modelo = obtenerModelo("neumatico");
                //ultVez = obtenerUltVez("neumatico");
                break;

            case R.id.img_frenos:
                idImagen = R.mipmap.ic_frenos;
                modelo = obtenerModelo("frenos");
                //ultVez = obtenerUltVez("frenos");
                break;
        }

        Intent intent = new Intent(activity_home.this,Pop_up.class);

        Bundle extras = new Bundle();
        extras.putInt("imagen", idImagen);
        extras.putString("nombre", nombre.getText().toString());
        extras.putString("modelo", modelo);
        //extras.putString("ultVez", ultVez);

        intent.putExtras(extras);
        startActivity(intent);


    }
}