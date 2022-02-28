package xyz.hannah.hannahapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import xyz.hannah.hannahapp.ClasesAyuda.Coche;

public class activity_carInfo extends AppCompatActivity {
    EditText mModeloCoche, mKilometroCoche, mMatriculaCoche ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);
    }


    /**
     * método que obtiene los datos introducidos en los campos y crea un objeto de tipo coche
     * luego los envia a activity_addCar
     * @param view
     */
    public void enviarDatos(View view) {
        mMatriculaCoche = findViewById(R.id.cpMatriculaCoche);
        mModeloCoche = findViewById(R.id.cpModeloCoche);
        mKilometroCoche = findViewById(R.id.cpKilometroCoche);

        String matricula = String.valueOf(mMatriculaCoche.getText());
        String modelo = String.valueOf(mModeloCoche.getText());
        double kilometros = Double.parseDouble(String.valueOf(mKilometroCoche.getText()));

        Coche coche = new Coche(matricula, modelo, kilometros);

        Intent intent = new Intent(activity_carInfo.this, activity_seleccionDatos.class);
        intent.putExtra("claseCoche", coche);

        startActivity(intent);


    }
}