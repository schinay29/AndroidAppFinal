package xyz.hannah.hannahapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class activity_home extends AppCompatActivity {

    private static final String EXTRA_IMAGE = "";
    private ImageView imgPartCar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




    }

    public void add(View view){

        startActivity(new Intent(activity_home.this, activity_profile.class));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showDetails(View view) {

        imgPartCar = findViewById(view.getId());
        switch (view.getId()){
            case R.id.img_luces:
                Toast.makeText(activity_home.this, "luces",Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(activity_home.this, view.getId() + " " + R.id.img_rueda,Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(activity_home.this,Pop_up.class);
        //Toast.makeText(activity_home.this, String.valueOf(getComponentName()),Toast.LENGTH_SHORT);
        //intent.putExtra(EXTRA_IMAGE, imgPartCar);
        //Toast.makeText(activity_home.this, String.valueOf(this.getComponentName().getClassName().toString()),Toast.LENGTH_SHORT);
        //startActivity(intent);
        //Toast.makeText(activity_home.this, String.valueOf(view.getId() + " " + view.getRootView()+ " " + view.findFocus() + " " + view.toString()),Toast.LENGTH_SHORT).show();


        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cuadro de dialogo creado").setTitle("Cuadro de dialogo");
        AlertDialog dialog = builder.create();
        */

    }
}