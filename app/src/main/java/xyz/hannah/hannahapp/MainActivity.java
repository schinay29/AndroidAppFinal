package xyz.hannah.hannahapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import xyz.hannah.hannahapp.ClasesAyuda.Coche;
import xyz.hannah.hannahapp.ClasesAyuda.PlantillaPartOfCar;

public class MainActivity extends AppCompatActivity {
    private EditText cpEmail;
    private EditText cpPassword;
    FirebaseAuth myAuth;
    FirebaseFirestore db;
    String idUsuario;
    String[] nombres;
    Coche coche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cpEmail = findViewById(R.id.cpUser);
        cpPassword = findViewById(R.id.cpPsswd);

        myAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        // array con los nombres de las partes del coche
        nombres = new String[]{"aceite lubricante", "amortiguador", "bateria", "sistema de escape y catalizador", "filtros",
                "correa de distribucion", "frenos", "luces", "ruedas"};

        // si el usuario ya esta logeado, lo redirige a la actividad principal
        if (myAuth.getCurrentUser() != null) {
            idUsuario = myAuth.getCurrentUser().getUid();
            //se situa en el documento de FirebaseStorage
            Log.d("MainActivity", "Accediendo a BBDD " + idUsuario);

            DocumentReference docRef = db.collection(idUsuario).document("Datos Coche");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // obtiene los datos de la BBDD y crea un objeto de tipo coche
                            coche = new Coche(document.getData().get("Matricula").toString(), document.getData().get("Modelo").toString(), Double.parseDouble(document.getData().get("Kilometros").toString()));
                            List<PlantillaPartOfCar> partesCoche = new ArrayList<PlantillaPartOfCar>();

                            Log.d("", "DocumentSnapshot data bbdd: " + document.getData().get("Modelo"));
                            for (String valor : nombres) {
                                try {
                                    // obtiene los datos de las parte de coches añadidas y los añade a la lista de partes de coche
                                    String modelo = document.getData().get("Modelo " + valor).toString();
                                    int id = Integer.parseInt(document.getData().get("Id " + valor).toString());
                                    int idImagen = Integer.parseInt(document.getData().get("Id Imagen " + valor).toString());
                                    String ultVez = document.getData().get("Ult Vez " + valor).toString();

                                    partesCoche.add(new PlantillaPartOfCar(id, idImagen, valor, modelo, ultVez));

                                } catch (Exception e) {
                                    Log.d("MainActivity", "oncreate :" + e);
                                }
                            }
                            // envia los objetos a la actividad principal
                            coche.setPartesDelCoche(partesCoche);
                            Toast.makeText(MainActivity.this, "Usuario actualmente identificado, redirigiendo...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), activity_home.class);
                            Bundle extras = new Bundle();
                            extras.putSerializable("claseCoche", coche);
                            intent.putExtras(extras);
                            startActivity(intent);

                            finish();
                        } else {
                            Log.d("", "No such document bbdd");
                        }
                    } else {
                        Log.d("", "get failed with ", task.getException());
                    }
                }
            });
        }
    }

    /** método que envia al registro
     *
     * @param view
     */
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, activity_register.class);
        startActivity(intent);
    }

    /**
     * método para logearse, comprueba que los datos añadidos sean correctos
     * @param view
     */
    public void signIn(View view) {
        //variable para gestionar firebaseAuth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String email = cpEmail.getText().toString();
        String password = cpPassword.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Usuario Logueado correctamente", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, activity_home.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }


}