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


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import xyz.hannah.hannahapp.ClasesAyuda.Coche;
import xyz.hannah.hannahapp.ClasesAyuda.Frenos;
import xyz.hannah.hannahapp.ClasesAyuda.Neumatico;

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
        Neumatico neumatico = new Neumatico();
        neumatico.setModelo("modelo1");
        //neumatico.setUltFechaCambio(Date.valueOf("2015-03-31"));
        Coche coche = new Coche();
        coche.setModelo("vrtstsgvr");
        coche.setNeumatico(neumatico);

        Frenos frenos = new Frenos();
        frenos.setModelo("modelooo");

        coche.setFrenos(frenos);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.child("Usuario").child(user.getUid()).child("Coches").child("3").setValue(coche);


        Toast.makeText(this, "Coche Agregado", Toast.LENGTH_SHORT).show();




    }

}