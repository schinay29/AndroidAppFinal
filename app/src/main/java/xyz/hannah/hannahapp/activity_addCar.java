package xyz.hannah.hannahapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import xyz.hannah.hannahapp.ClasesAyuda.Amortiguador;
import xyz.hannah.hannahapp.ClasesAyuda.Bateria;
import xyz.hannah.hannahapp.ClasesAyuda.Coche;
import xyz.hannah.hannahapp.ClasesAyuda.CorreaDeDistribucion;
import xyz.hannah.hannahapp.ClasesAyuda.Frenos;
import xyz.hannah.hannahapp.ClasesAyuda.Luces;
import xyz.hannah.hannahapp.ClasesAyuda.Neumatico;
import xyz.hannah.hannahapp.ClasesAyuda.SistemaDeEscape;

public class activity_addCar extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText mRueda, mCDistribucion, mBateria, mFrenos, mLuces, mCatalizador, mAmortiguador;
    EditText dRueda, dCDistribucion, dBateria, dFrenos, dLuces, dCatalizador, dAmortiguador;
    String modelo;
    double kilometro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        inicializarFirebase();

        Bundle parametros = this.getIntent().getExtras();
        modelo =  parametros.getString("modelo");
        kilometro =  parametros.getDouble("kilometro");


        //listaDatos();

    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void añadirDatos(View view){
        mRueda = findViewById(R.id.cp_modeloRueda);
        mAmortiguador = findViewById(R.id.cp_modeloAmortiguador);
        mFrenos = findViewById(R.id.cp_modeloFrenos);
        mBateria = findViewById(R.id.cp_modeloBateria);
        mCatalizador = findViewById(R.id.cp_modeloCatalizador);
        mCDistribucion = findViewById(R.id.cp_modelocDistribucion);
        mLuces = findViewById(R.id.cp_modeloLuces);

        dRueda = findViewById(R.id.cp_ultVezRueda);
        dAmortiguador = findViewById(R.id.cp_ultVezAmortiguador);
        dFrenos = findViewById(R.id.cp_ultVezFrenos);
        dBateria = findViewById(R.id.cp_ultVezBateria);
        dCatalizador = findViewById(R.id.cp_ultVezCatalizador);
        dCDistribucion = findViewById(R.id.cp_ultVezcDistribucion);
        dLuces = findViewById(R.id.cp_ultVezLuces);

        Neumatico neumatico = new Neumatico();
        neumatico.setModelo(String.valueOf(mRueda.getText()));
        //neumatico.setUltFechaCambio(Date.valueOf(String.valueOf(dRueda.getText())));

        Amortiguador amortiguador = new Amortiguador();
        amortiguador.setModelo(String.valueOf(mAmortiguador.getText()));
        //amortiguador.setUltFechaCambio();

        Frenos frenos = new Frenos();
        frenos.setModelo(String.valueOf(mFrenos.getText()));
        //frenos.setUltFechaCambio();

        Bateria bateria = new Bateria();
        bateria.setModelo(String.valueOf(mBateria.getText()));

        SistemaDeEscape sisEscape = new SistemaDeEscape();
        sisEscape.setModelo(String.valueOf(mCatalizador.getText()));

        CorreaDeDistribucion cDistribucion = new CorreaDeDistribucion();
        cDistribucion.setModelo(String.valueOf(mCDistribucion.getText()));

        Luces luces = new Luces();
        luces.setModelo(String.valueOf(mLuces.getText()));



        Coche coche = new Coche();
        coche.setModelo(modelo);
        coche.setKilometros(kilometro);
        coche.setNeumatico(neumatico);
        coche.setAmortiguador(amortiguador);
        coche.setFrenos(frenos);
        coche.setBateria(bateria);
        coche.setSistemaDeEscape(sisEscape);
        coche.setCorreaDistribucion(cDistribucion);
        coche.setLuces(luces);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.child("Usuario").child(user.getUid()).child("Coches").child("Coche: " + coche.getModelo()).setValue(coche);


        Toast.makeText(this, "Partes del Coche Agregado", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(activity_addCar.this, activity_home.class));



    }

}