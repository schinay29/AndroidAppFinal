package xyz.hannah.hannahapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xyz.hannah.hannahapp.ClasesAyuda.AdapterRVInfo;
import xyz.hannah.hannahapp.ClasesAyuda.AdapterRVSelection;
import xyz.hannah.hannahapp.ClasesAyuda.Coche;

public class activity_addCar extends AppCompatActivity {

    FirebaseAuth myAuth;
    FirebaseFirestore myStore;
    String idUsuario;
    EditText mRueda, mCDistribucion, mBateria, mFrenos, mLuces, mCatalizador, mAmortiguador;
    EditText dRueda, dCDistribucion, dBateria, dFrenos, dLuces, dCatalizador, dAmortiguador;
    String modelo;
    double kilometro;

    /**
     * declarando variables para vista
     */
    private RecyclerView recyclerView;
    private String nombrePartOfCar[];
    private List<Integer> idImagen;

    private MaterialCardView cardView;
    private Coche coche;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        inicializarFirebase();

        idImagen = new ArrayList<Integer>();
        Intent intent = getIntent();

        Bundle parametros = this.getIntent().getExtras();

        idImagen = parametros.getIntegerArrayList("lista");

        // obtengo el objeto coche enviado desde la actividad anterior
        // Coche coche = (Coche) getIntent().getSerializableExtra("claseCoche");
        coche = (Coche) parametros.getSerializable("claseCoche");


        //String ultVez = parametros.getString("ultVez");
        // obtengo el array de strings de strings.xml y lo guardo en nombrePartOfCar
        // inicializo el recyclerView
        nombrePartOfCar = getResources().getStringArray(R.array.partes_coche);
        recyclerView = findViewById(R.id.recyclerViewAddCar);

        AdapterRVInfo adapter = new AdapterRVInfo(this, nombrePartOfCar, (ArrayList<Integer>) idImagen);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


    }

    private void inicializarFirebase() {
        myAuth = FirebaseAuth.getInstance();
        myStore = FirebaseFirestore.getInstance();
        idUsuario = myAuth.getCurrentUser().getUid();
    }

    public void añadirDatos(View view) {

        /**
         Neumatico neumatico = new Neumatico();
         neumatico.setModelo(String.valueOf(mRueda.getText()));
         neumatico.setUltFechaCambio(dRueda.getText().toString());

         Amortiguador amortiguador = new Amortiguador();
         amortiguador.setModelo(String.valueOf(mAmortiguador.getText()));
         amortiguador.setUltFechaCambio(dAmortiguador.getText().toString());

         Frenos frenos = new Frenos();
         frenos.setModelo(String.valueOf(mFrenos.getText()));
         frenos.setUltFechaCambio(dFrenos.getText().toString());

         Bateria bateria = new Bateria();
         bateria.setModelo(String.valueOf(mBateria.getText()));
         bateria.setUltFechaCambio(dBateria.getText().toString());

         SistemaDeEscape sisEscape = new SistemaDeEscape();
         sisEscape.setModelo(String.valueOf(mCatalizador.getText()));
         sisEscape.setUltFechaCambio(dCatalizador.getText().toString());

         CorreaDeDistribucion cDistribucion = new CorreaDeDistribucion();
         cDistribucion.setModelo(String.valueOf(mCDistribucion.getText()));
         cDistribucion.setUltFechaCambio(dCDistribucion.getText().toString());

         Luces luces = new Luces();
         luces.setModelo(String.valueOf(mLuces.getText()));
         luces.setUltFechaCambio(dLuces.getText().toString());

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

         //crea la colección y el documento de la BBDD
         DocumentReference docRef = myStore.collection("usuarios").document(idUsuario);

         HashMap<String, String> infoCoche = new HashMap<>();
         infoCoche.put("Modelo", coche.getModelo());
         infoCoche.put("Kilometros", String.valueOf(coche.getKilometros()));
         infoCoche.put("Neumatico", String.valueOf(coche.getNeumatico().getUltFechaCambio()));
         infoCoche.put("Amortiguador", String.valueOf(coche.getAmortiguador().getUltFechaCambio()));
         infoCoche.put("Frenos", String.valueOf(coche.getFrenos().getUltFechaCambio()));
         infoCoche.put("Bateria", String.valueOf(coche.getBateria().getUltFechaCambio()));
         infoCoche.put("Sistema de escape", String.valueOf(coche.getSistemaDeEscape().getUltFechaCambio()));
         infoCoche.put("Correa de distribución", String.valueOf(coche.getCorreaDistribucion().getUltFechaCambio()));
         infoCoche.put("Luces", String.valueOf(coche.getLuces().getUltFechaCambio()));


         //registro de datos del usuario en FirestoreDatabase
         docRef.set(infoCoche).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()){
        Toast.makeText(activity_addCar.this, "Partes del Coche Agregado", Toast.LENGTH_SHORT).show();

        Intent intent =new Intent(activity_addCar.this, activity_home.class);
        startActivity(intent);

        }
        }
        });
         */

        Intent intent =new Intent(activity_addCar.this, activity_home.class);
        Bundle extras = new Bundle();
        extras.putIntegerArrayList("lista", (ArrayList<Integer>) idImagen);
        //extras.putString("ultVez", ultVez);
        extras.putSerializable("claseCoche", coche);
        //intent.putExtras("claseCoche", coche);
        intent.putExtras(extras);
        startActivity(intent);

    }

    public void volver(View view) {
    }
}