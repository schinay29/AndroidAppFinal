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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activity_register extends AppCompatActivity {
    private EditText txtNombre;
    private EditText txtEmail;
    private EditText txtContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtNombre = findViewById(R.id.registroNombre);
        txtEmail = findViewById(R.id.registroEmail);
        txtContraseña = findViewById(R.id.registroContraseña);
    }

    public void signUp(View view){
        //variable para gestionar firebaseAuth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String email = txtEmail.getText().toString();
        String password = txtContraseña.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(activity_register.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(activity_register.this, activity_home.class));
                }
            }
        });





    }

}