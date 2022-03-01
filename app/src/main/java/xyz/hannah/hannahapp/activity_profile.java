package xyz.hannah.hannahapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import xyz.hannah.hannahapp.ClasesAyuda.Coche;

public class activity_profile extends AppCompatActivity {
    // variables para la BBDD
    FirebaseAuth myAuth;
    FirebaseFirestore myStore;
    String idUsuario;
    //para guardar imagenes en Firebase
    StorageReference myStorage;
    private ImageView imagen;
    // menu en la parte baja de la pantalla
    BottomNavigationView bottomNavigation;
    private EditText mEmail, mTelefono, mFecha;
    Coche coche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imagen = findViewById(R.id.img_profile);
        mEmail = findViewById(R.id.cpEmail);
        mTelefono = findViewById(R.id.cpTelefono);
        mFecha = findViewById(R.id.cpFechaNacimiento);
        bottomNavigation = findViewById(R.id.bottom_nav);

        myAuth = FirebaseAuth.getInstance();
        myStore = FirebaseFirestore.getInstance();
        idUsuario = myAuth.getCurrentUser().getUid();
        //instancia el Storage
        myStorage = FirebaseStorage.getInstance().getReference();
        // llama al metodo que carga la imagen guardad en la BBDD
        obtenerImagenEnStorageFierabse();
        // obtiene el objeto de tipo coche pasa por la actividad anterior
        coche = (Coche) getIntent().getSerializableExtra("claseCoche");
        // selecciona por defecto el item de perfil del menu
        bottomNavigation.setSelectedItemId(R.id.nav_profile);
        /**
         * listener al seleccionar los items del menu, permite navegar entre pantallas
         */
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent intent = new Intent(activity_profile.this, activity_home.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        Bundle extras = new Bundle();
                        extras.putSerializable("claseCoche", coche);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        cambiarDatos();
    }

    private void cambiarDatos() {
        // método para cambiar los datos del perfil y agregar a la BBDD
    }

    /**
     * método que permite abrir la galaria para sekeccionar una imagen y cargarla en el perfil
     * @param view
     */
    public void changeImg(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            imagen.setImageURI(path);
            //llamamos al metodo para subir la imagen a Firebase Storage
            subirImagenCloudStorageFirebase(path);
        }
    }

    /**
     * metodo para guardar imagen a Storage Firebase
     * @param imagenUri
     */
    private void subirImagenCloudStorageFirebase(Uri imagenUri) {
        // guarda la imagen
        //StorageReference referenciaFichero = myStorage.child("imagen_perfil.jpg");
        // crea una carpeta de usuario y guarda la imagen dentro
        StorageReference referenciaFichero = myStorage.child("usuarios/" + idUsuario + "/imagen_perfil.jpg");
        referenciaFichero.putFile(imagenUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(activity_profile.this, "Imagen Cargada.", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity_profile.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * método que permite al campo EditText estar editable
     * @param view
     */
    public void editarCampo(View view) {
        int vista = view.getId();
        if (vista == R.id.editFecha) {
            mFecha.setEnabled(true);
        } else if (vista == R.id.editTelefono) {
            mTelefono.setEnabled(true);
        }
    }

    /**
     * método que encuentra la imagén de perfil en la BBDD y lo carga en el activity_profile
     */
    public void obtenerImagenEnStorageFierabse() {
        //se situa el storage en donde se encuentra la imagen
        StorageReference photoReference = myStorage.child("usuarios/" + idUsuario + "/imagen_perfil.jpg");
        final long ONE_MEGABYTE = 1024 * 1024;

        //se obtiene la imagen a traves de  un mapa de bits y se hace sus respectivos listener
        photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                // se carga la imagen en el imagenView
                imagen.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * método para cerrar Sesion
     *
     * @param view
     */
    public void logOut(View view) {
        // método estático para cerrar sesión
        FirebaseAuth.getInstance().signOut();
        // termina la instancia y conexión de FirebaseFirestore
        FirebaseFirestore.getInstance().terminate();

        // redirigimos a la ventana de login
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    /**
     * método que cambia de modo normal a modo noche y viceversa
     * @param view
     */
    public void cambiarModo(View view) {
        Switch sw = (Switch) view;
        if (sw.isChecked()) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

}