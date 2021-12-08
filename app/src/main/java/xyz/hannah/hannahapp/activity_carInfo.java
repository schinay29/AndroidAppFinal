package xyz.hannah.hannahapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class activity_carInfo extends AppCompatActivity {
    EditText mCoche, kCoche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);
    }


    public void enviarDatos(View view) {
        mCoche = findViewById(R.id.cpModeloCoche);
        kCoche = findViewById(R.id.cpKilometroCoche);

        String modelo = String.valueOf(mCoche.getText());
        double kilometro = Double.parseDouble(String.valueOf(kCoche.getText()));

        Bundle extras = new Bundle();

        extras.putDouble("kilometro", kilometro);
        extras.putString("modelo", modelo);

        Intent intent = new Intent(activity_carInfo.this, activity_addCar.class);
        intent.putExtras(extras);

        startActivity(intent);


    }
}