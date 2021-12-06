package xyz.hannah.hannahapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class activity_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }

    public void add(View view){

    }

    public void showDetails(View view) {

        startActivity(new Intent(activity_home.this,Pop_up.class));

        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cuadro de dialogo creado").setTitle("Cuadro de dialogo");
        AlertDialog dialog = builder.create();
        */

    }
}