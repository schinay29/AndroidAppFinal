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

public class activity_register extends AppCompatActivity {
    private EditText txtNombre;
    private EditText txtEmail;
    private EditText txtContraseña;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inicializarFirebase();

        txtNombre = findViewById(R.id.registroNombre);
        txtEmail = findViewById(R.id.registroEmail);
        txtContraseña = findViewById(R.id.registroContraseña);
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void signUp(View view){
        //variable para gestionar firebaseAuth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String nombre = txtNombre.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtContraseña.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    databaseReference.child("Usuario").child(user.getUid()).child("nombre").setValue(nombre);

                    Toast.makeText(activity_register.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(activity_register.this, activity_carInfo.class));
                }
            }
        });









    }

}