package xyz.hannah.hannahapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import xyz.hannah.hannahapp.ClasesAyuda.Coche;
import xyz.hannah.hannahapp.ClasesAyuda.AdapterRVSelection;
import xyz.hannah.hannahapp.ClasesAyuda.PartOfCar;
import xyz.hannah.hannahapp.ClasesAyuda.PlantillaPartOfCar;

public class activity_home extends AppCompatActivity {

    FirebaseAuth myAuth;
    FirebaseFirestore myStore;
    String idUsuario;
    private TextView nombre, mModelo, mKilometros;
    private ImageView imagen;
    private LinearLayout linearLayout;
    BottomNavigationView bottomNavigation;
    private String texto, modelo, ultVez, modeloCoche;
    RelativeLayout mNotificacion;


    /**
     * declarando variables para vista
     */
    private RecyclerView recyclerView;
    private List<PlantillaPartOfCar> partesCoche;

    /**
     * API google
     * declarando variables
     */
    private final static String TAG = "MainActivity";
    // Id para identificar la actividad al preguntar permiso para acceder a la ubicación
    private static final int PERMISSION_REQUEST_ACTIVITY_RECOGNITION = 45;

    // revisa que los dispositivos tengan Android 10
    private boolean runningQOrLater = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;

    private boolean seguimientoActivado;

    // lista con todos los ActivityTransition con los que se trabajará (STILL, WALKING, IN_VEHICLE)
    private List<ActivityTransition> transitions;

    // Action fired when transitions are triggered.
    private final String TRANSITIONS_RECEIVER_ACTION = BuildConfig.APPLICATION_ID + "TRANSITIONS_RECEIVER_ACTION";


    private PendingIntent pendingIntent;
    private TransitionsReceiver mTransitionsReceiver;

    /**
     * método que devuelve el tipo de actividad detectada
     * @param activity
     * @return
     */
    private static String getActivity(int activity) {
        switch (activity) {
            case DetectedActivity.STILL:
                return "STILL";
            case DetectedActivity.WALKING:
                return "WALKING";
            case DetectedActivity.IN_VEHICLE:
                return "IN_VEHICLE";
            default:
                return "UNKNOWN";
        }
    }

    /**
     * método que devuelve el estado de la actividad (si ya termino, empezó o ninguna)
     * @param transitionType
     * @return
     */
    private static String getTransitionType(int transitionType) {
        switch (transitionType) {
            case ActivityTransition.ACTIVITY_TRANSITION_ENTER:
                return "ENTER";
            case ActivityTransition.ACTIVITY_TRANSITION_EXIT:
                return "EXIT";
            default:
                return "UNKNOWN";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mModelo = findViewById(R.id.modelo);
        mKilometros = findViewById(R.id.kilometros);
        bottomNavigation = findViewById(R.id.bottom_nav);
        mNotificacion = findViewById(R.id.notificacion_popup);
        mNotificacion.setVisibility(View.INVISIBLE);

        myAuth = FirebaseAuth.getInstance();
        myStore = FirebaseFirestore.getInstance();
        idUsuario = myAuth.getCurrentUser().getUid();

        /**
         * creando vista inicial
         */

        partesCoche = new ArrayList<PlantillaPartOfCar>();

        Bundle parametros;
        if((parametros = this.getIntent().getExtras()) !=null){
            if(!parametros.isEmpty()){
                partesCoche = (List<PlantillaPartOfCar>) parametros.getSerializable("lista");

                // obtengo el objeto coche enviado desde la actividad anterior
                // Coche coche = (Coche) getIntent().getSerializableExtra("claseCoche");
                Coche coche = (Coche) parametros.getSerializable("claseCoche");
            }
        }




        //String ultVez = parametros.getString("ultVez");
        // inicializo el recyclerView
        recyclerView = findViewById(R.id.recyclerViewHome);

        AdapterRVSelection adapter = new AdapterRVSelection(this, (ArrayList<PlantillaPartOfCar>) partesCoche);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



        // inicializa el seguimiento de la actividad a false
        seguimientoActivado = false;

        // Lista de transiciones
        transitions = new ArrayList<>();

        // se añade las transiciones con lás que se trabajará
        transitions.add(new ActivityTransition.Builder()
                .setActivityType(DetectedActivity.WALKING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build());
        transitions.add(new ActivityTransition.Builder()
                .setActivityType(DetectedActivity.WALKING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build());
        transitions.add(new ActivityTransition.Builder()
                .setActivityType(DetectedActivity.STILL)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build());
        transitions.add(new ActivityTransition.Builder()
                .setActivityType(DetectedActivity.STILL)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build());
        transitions.add(new ActivityTransition.Builder()
                .setActivityType(DetectedActivity.IN_VEHICLE)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build());

        transitions.add( new ActivityTransition.Builder()
                .setActivityType(DetectedActivity.IN_VEHICLE)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build());


        // TODO: Initialize PendingIntent that will be triggered when a activity transition occurs.
        Intent intent2 = new Intent(TRANSITIONS_RECEIVER_ACTION);
        pendingIntent = PendingIntent.getBroadcast(activity_home.this, 0, intent2, 0);

        // Crea un BroadcastReciever que recibirá la información cuando suceda una transición
        mTransitionsReceiver = new TransitionsReceiver();

        enviarNotificacion("App initialized.");

        /**
         * se obtiene datos de la BBDD para envviarlo a la pantalla
         */

        //se situa en el documento de FirebaseStorage
        DocumentReference docRef = myStore.collection("usuarios").document(idUsuario);
        // obtine los valores de la BBDD
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                mModelo.setText("MODELO: "+  value.getString("Modelo"));
                mKilometros.setText("KILOMETROS: "+ value.getString("Kilometros") + "km");

            }
        });

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        //mModelo.setText("MODELO: "+ modeloCoche );
                        //mKilometros.setText("KILOMETROS: "+ kilometroCoche + "km");
                        break;

                    case R.id.nav_add:
                        intent = new Intent(activity_home.this,Pop_up.class);

                        Bundle extras = new Bundle();
                        extras.putInt("imagen", R.mipmap.ic_coche0_foreground);
                        extras.putString("nombre", "Actualizar Datos");
                        extras.putString("modelo", modeloCoche);
                        //extras.putString("ultVez", ultVez);

                        intent.putExtras(extras);
                        startActivity(intent);
                        break;

                    case R.id.nav_profile:
                        intent = new Intent(activity_home.this, activity_profile.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

    }


    private String obtenerModelo(String nombreParte){
        //se situa en el documento
        DocumentReference docRef = myStore.collection("usuarios").document(idUsuario);
        // obtine los valores de la BBDD
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                value.getString(nombreParte);

            }
        });

        return null;
    }


    private String obtenerUltVez(String nombreParte){
        return null;
    }



    public void showDetails(View view) {

        Toast.makeText(this, "id: " + view.getId(), Toast.LENGTH_SHORT).show();

        for (PlantillaPartOfCar p: partesCoche) {
            if (p.getId() == view.getId()) {

                Log.d(TAG, "son iguales");

                Intent intent = new Intent(activity_home.this,Pop_up.class);
                Toast.makeText(this, modelo, Toast.LENGTH_SHORT).show();
                intent.putExtra("PlantillaPartOfCar", p);
                startActivity(intent);
                break;
            }
        }
    }


    /**
     * API GOOOGLE
     *
     */

    @Override
    protected void onStart() {
        super.onStart();

        // TODO: Register the BroadcastReceiver to listen for activity transitions.
        registerReceiver(mTransitionsReceiver, new IntentFilter(TRANSITIONS_RECEIVER_ACTION));
    }

    @Override
    protected void onPause() {

        // Desactiva la actividad de transicion cuando el usuario deja la app
        if (seguimientoActivado) {
            disableActivityTransitions();
        }
        super.onPause();
    }


    @Override
    protected void onStop() {

        // TODO: Unregister activity transition receiver when user leaves the app.
        unregisterReceiver(mTransitionsReceiver);

        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Start activity recognition if the permission was approved.
        if (activityRecognitionPermissionApproved() && !seguimientoActivado) {
            enableActivityTransitions();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * método que activa y escucha cuando el usuario hace un cambio de transicion
     */
    private void enableActivityTransitions() {

        Log.d(TAG, "enableActivityTransitions()");

        // crea una peticion y escucha los cambios de transicion de los usuarios
        // TODO: Create request and listen for activity changes.
        ActivityTransitionRequest request = new ActivityTransitionRequest(transitions);

        // Register for Transitions Updates.
        Task<Void> task = ActivityRecognition.getClient(this)
                .requestActivityTransitionUpdates(request, pendingIntent);

        task.addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        seguimientoActivado = true;
                        enviarNotificacion("Transitions Api was successfully registered.");

                    }
                });

        task.addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        enviarNotificacion("Transitions Api could NOT be registered: " + e);
                        Log.e(TAG, "Transitions Api could NOT be registered: " + e);

                    }
                });
    }


    /**
     * método quye desactiva del Api los cambios de transicion que hace el usuario
     */
    private void disableActivityTransitions() {

        Log.d(TAG, "disableActivityTransitions()");

        // detiene y hace que el sistema deje de escuchar los cambios de transicion
        // TODO: Stop listening for activity changes.
        ActivityRecognition.getClient(this).removeActivityTransitionUpdates(pendingIntent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        seguimientoActivado = false;
                        enviarNotificacion("Transitions successfully unregistered.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        enviarNotificacion("Transitions could not be unregistered: " + e);
                        Log.e(TAG,"Transitions could not be unregistered: " + e);
                    }
                });
    }

    /**
     * On devices Android 10 and beyond (29+), you need to ask for the ACTIVITY_RECOGNITION via the
     * run-time permissions.
     */
    private boolean activityRecognitionPermissionApproved() {

        // TODO: Review permission check for 29+.
        if (runningQOrLater) {

            return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACTIVITY_RECOGNITION
            );
        } else {
            return true;
        }
    }

    /**
     * activa o desactiva el API de transicion y revisa si se tiene los permisos para usarlo
     * @param view
     */
    public void onClickEnableOrDisableActivityRecognition(View view) {

        // activa o desactiva lel reconocimiento de actividad de transicion y pregunta
        // por los permisos si se necesitan y no están activados
        // TODO: Enable/Disable activity tracking and ask for permissions if needed.
        if (activityRecognitionPermissionApproved()) {

            if (seguimientoActivado) {
                disableActivityTransitions();

            } else {
                enableActivityTransitions();
            }

        } else {
            /**
             * pregunta al usuario para activar el permiso de actividad y ubicación
             */
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                    PERMISSION_REQUEST_ACTIVITY_RECOGNITION);
        }
    }

    /**
     * escribe en un Toast el mensaje que se le pase por parámetro
     * @param message
     */
    private void enviarNotificacion(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        //mLogFragment.getLogView().println(message);
        Log.d(TAG, message);
    }

    /**
     * clase que extiende de BroadcastReceiver y escucha cuando una ActivityTransition ocurre
     *
     */
    public class TransitionsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG, "onReceive(): " + intent);

            if (!TextUtils.equals(TRANSITIONS_RECEIVER_ACTION, intent.getAction())) {

                enviarNotificacion("Received an unsupported action in TransitionsReceiver: action = " +
                        intent.getAction());
                return;
            }

            // TODO: Extract activity transition information from listener.
            if (ActivityTransitionResult.hasResult(intent)) {

                ActivityTransitionResult result = ActivityTransitionResult.extractResult(intent);

                for (ActivityTransitionEvent event : result.getTransitionEvents()) {

                    String info = "Transition: " + getActivity(event.getActivityType()) +
                            " (" + getTransitionType(event.getTransitionType()) + ")" + "   " +
                            new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date());
                    NotificationCompat.Builder mBuilder;

                    enviarNotificacion(info);
                }
            }
        }
    }



}


