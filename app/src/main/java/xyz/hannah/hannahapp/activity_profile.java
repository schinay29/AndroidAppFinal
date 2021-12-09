package xyz.hannah.hannahapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_profile extends AppCompatActivity {

    private ImageView imagen;
    private DatabaseReference databaseReference;
    private TextView mNombre, mKilometros, mModelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imagen = findViewById(R.id.img_profile);
        mNombre = findViewById(R.id.userName);
        mModelo = findViewById(R.id.cocheModelo);
        mKilometros = findViewById(R.id.cocheKilometro);
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
}