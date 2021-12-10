package xyz.hannah.hannahapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_profile extends AppCompatActivity {

    private ImageView imagen;
    BottomNavigationView bottomNavigation;
    private DatabaseReference databaseReference;
    private TextView mNombre, mKilometros, mModelo;
    private EditText mEmail, mTelefono, mFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imagen = findViewById(R.id.img_profile);
        mNombre = findViewById(R.id.userName);
        mModelo = findViewById(R.id.cocheModelo);
        mKilometros = findViewById(R.id.cocheKilometro);
        mEmail = findViewById(R.id.cpEmail);
        mTelefono = findViewById(R.id.cpTelefono);
        mFecha = findViewById(R.id.cpFechaNacimiento);
        bottomNavigation = findViewById(R.id.bottom_nav);

        bottomNavigation.setSelectedItemId(R.id.nav_profile);

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        Intent intent = new Intent(activity_profile.this, activity_home.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        break;

                    case R.id.nav_add:
                        //startActivity(new Intent(activity_profile.this, activity_home.class));
                        break;

                    case R.id.nav_profile:

                        break;
                }

                return true;
            }
        });



        cambiarDatos();


    }

    private void cambiarDatos(){
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    mNombre.setText(snapshot.child(user.getUid()).child("nombre").getValue().toString().toUpperCase());
                    mModelo.setText(snapshot.child(user.getUid()).child("Coche").child("modelo").getValue().toString());
                    mKilometros.setText(snapshot.child(user.getUid()).child("Coche").child("kilometros").getValue().toString() + "km");
                    mEmail.setText(user.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void changeImg(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri path = data.getData();
            imagen.setImageURI(path);
        }
    }

    public void editarCampo(View view) {
        int vista = view.getId();

        if(vista == R.id.editFecha){
            mFecha.setEnabled(true);
        }else if(vista == R.id.editTelefono){
            mTelefono.setEnabled(true);
        }

    }
}