package xyz.hannah.hannahapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import xyz.hannah.hannahapp.ClasesAyuda.Coche;

public class activity_addCar extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        inicializarFirebase();

        //listaDatos();

    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void añadirDatos(View view){
        Coche coche = new Coche();
        coche.setModelo("vrtstsgvr");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference.child("Usuario").child(user.getUid()).child("Coche").setValue(coche);
        Toast.makeText(this, "Coche Agregado", Toast.LENGTH_SHORT).show();




    }

}