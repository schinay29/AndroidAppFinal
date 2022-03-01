package xyz.hannah.hannahapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class activity_register extends AppCompatActivity {
    private EditText txtNombre, txtEmail, txtContraseña, txtConfContraseña;
    FirebaseAuth myAuth;
    FirebaseFirestore myStore;
    String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtNombre = findViewById(R.id.registroNombre);
        txtEmail = findViewById(R.id.registroEmail);
        txtContraseña = findViewById(R.id.registroContraseña);
        txtConfContraseña = findViewById(R.id.registroConfContraseña);

        inicializarFirebase();
    }

    private void inicializarFirebase(){
        myAuth = FirebaseAuth.getInstance();
        myStore = FirebaseFirestore.getInstance();
    }

    public void signUp(View view){

        String nombre = txtNombre.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtContraseña.getText().toString();
        String confContraseña = txtConfContraseña.getText().toString();

        // comprueba si el email está vacio
        if (email.isEmpty()) {
            //error: lanza simbolo rojo a la derecha, indicando que el campo no puede estar vacio.
            txtEmail.setError("Es necesario que introduzca el Email.");
            return;
        }

        //comprueba si la contraseña está vacia
        if (password.isEmpty() ){
            txtContraseña.setError("Es necesario que introduzca una contraseña");
            return;

            //comprueba que la contraseña tenga más de 6 carácteres
        } else if(password.length()<6){
            txtContraseña.setError("La contraseña debe tener más de 6 carácteres");
        }

        //comprueba si el campo de confirmar contraseña está vacia
        if (confContraseña.isEmpty() ){
            txtConfContraseña.setError("Es necesario que introduzca una contraseña");
            return;
            //comprueba que la contraseña sea igual en ambos campos
        } else if(!password.equals(confContraseña)){
            txtConfContraseña.setError("La contraseña debe ser igual");

        }else{
            myAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //Si no ha habido problemas (la tarea de registrar el usuario ha sido exitosa: Feedback y redireccion a la MainActivity
                    if (task.isSuccessful()) {
                        // obteniendo UID de usuario
                        idUsuario = myAuth.getCurrentUser().getUid();

                        //crea la colección y el documento de la BBDD
                        DocumentReference docRef = myStore.collection(idUsuario).document("Datos Personales");

                        HashMap<String, String> infoUsuario = new HashMap<>();
                        infoUsuario.put("Nombre", nombre);
                        infoUsuario.put("Email", email);

                        //registro de datos del usuario en FirestoreDatabase
                        docRef.set(infoUsuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(activity_register.this, "Usuario registrado correctamente.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), activity_carInfo.class);
                                    startActivity(intent);

                                }
                            }
                        });
                        // cambiar reglas -> allow read, write: if request.auth != null <-
                        //para tener acceso a la BBDD y poder añadir los datos

                    } else {
                        Toast.makeText(activity_register.this,"Se ha producido une error en el proceso de registro.", Toast.LENGTH_SHORT ).show();
                        //Toast.makeText(activity_register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            });
        }

    }


}