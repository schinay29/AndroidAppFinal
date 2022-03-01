package xyz.hannah.hannahapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.Map;

import xyz.hannah.hannahapp.ClasesAyuda.AdapterRVInfo;
import xyz.hannah.hannahapp.ClasesAyuda.AdapterRVSelection;
import xyz.hannah.hannahapp.ClasesAyuda.Coche;
import xyz.hannah.hannahapp.ClasesAyuda.PlantillaPartOfCar;

public class activity_addCar extends AppCompatActivity {

    FirebaseAuth myAuth;
    FirebaseFirestore myStore;
    String idUsuario;


    AdapterRVInfo adapter;
    /**
     * declarando variables para vista
     */
    private RecyclerView recyclerView;
    private List<PlantillaPartOfCar> partesCoche;
    private Coche coche;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        inicializarFirebase();

        partesCoche = new ArrayList<PlantillaPartOfCar>();
        Intent intent = getIntent();

        Bundle parametros = this.getIntent().getExtras();

        partesCoche = (List<PlantillaPartOfCar>) parametros.getSerializable("lista");

        // obtengo el objeto coche enviado desde la actividad anterior
        // Coche coche = (Coche) getIntent().getSerializableExtra("claseCoche");
        coche = (Coche) parametros.getSerializable("claseCoche");


        //String ultVez = parametros.getString("ultVez");
        // inicializo el recyclerView
        recyclerView = findViewById(R.id.recyclerViewAddCar);

        adapter = new AdapterRVInfo(this, (List<PlantillaPartOfCar>) partesCoche);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


    }

    private void inicializarFirebase() {
        myAuth = FirebaseAuth.getInstance();
        myStore = FirebaseFirestore.getInstance();
        idUsuario = myAuth.getCurrentUser().getUid();
    }

    public void añadirDatos(View view) {
        String[] modelo = adapter.getModelos();
        String[] ultVez = adapter.getUltVez();

        for (int i =0; i<modelo.length; i++){
            partesCoche.get(i).setModelo(modelo[i]);
            partesCoche.get(i).setUltFechaCambio(ultVez[i]);
            Log.d("MainActivity", "en añadir datos :" + modelo[i]);
            Log.d("MainActivity", "en añadir datos :" + ultVez[i]);
        }

        coche.setPartesDelCoche(partesCoche);

        // añadir datos coche
        //crea la colección y el documento de la BBDD
        DocumentReference docRef = myStore.collection(idUsuario).document("Datos Coche");

        HashMap<String, String> infoCoche = new HashMap<>();
        infoCoche.put("Matricula", coche.getMatricula());
        infoCoche.put("Modelo", coche.getModelo());
        infoCoche.put("Kilometros", String.valueOf(coche.getKilometros()));
        for (PlantillaPartOfCar p: partesCoche) {
            infoCoche.put("Id " + p.getNombre(), String.valueOf(p.getId()));
            infoCoche.put("Id Imagen " + p.getNombre(), String.valueOf(p.getIdImagen()));
            infoCoche.put("Modelo " + p.getNombre(), p.getModelo());
            infoCoche.put("Ult Vez " + p.getNombre(), p.getUltFechaCambio());

        }

        //registro de datos del usuario en FirestoreDatabase
        docRef.set(infoCoche).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(activity_addCar.this, "Coche registrado correctamente.", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(activity_addCar.this, activity_home.class);

                    Bundle extras = new Bundle();
                    extras.putSerializable("claseCoche", coche);
                    intent.putExtras(extras);
                    startActivity(intent);

                }
            }
        });
        // cambiar reglas -> allow read, write: if request.auth != null <-
        //para tener acceso a la BBDD y poder añadir los datos


    }

    public void volver(View view) {
    }
}