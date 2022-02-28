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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private EditText cpEmail;
    private EditText cpPassword;
    FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cpEmail = findViewById(R.id.cpUser);
        cpPassword = findViewById(R.id.cpPsswd);

        myAuth = FirebaseAuth.getInstance();

        // si el usuario ya esta logeado, lo redirige a la actividad principal
        if (myAuth.getCurrentUser() != null) {
            Toast.makeText(MainActivity.this, "Usuario actualmente identificado, redirigiendo...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), activity_seleccionDatos.class);
            startActivity(intent);
            finish();
            //myAuth.sendPasswordResetEmail();
        }


    }

    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, activity_register.class);
        startActivity(intent);
    }

    public void signIn(View view){
        //variable para gestionar firebaseAuth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String email = cpEmail.getText().toString();
        String password = cpPassword.getText().toString();


        if(!email.isEmpty() && !password.isEmpty()){
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Usuario Logueado correctamente", Toast.LENGTH_SHORT).show();

                        Intent intent =new Intent(MainActivity.this, activity_home.class);
                        startActivity(intent);


                    }
                }
            });
        }
    }


}