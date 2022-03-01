package xyz.hannah.hannahapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import xyz.hannah.hannahapp.ClasesAyuda.Coche;
import xyz.hannah.hannahapp.ClasesAyuda.PlantillaPartOfCar;

public class activity_seleccionDatos extends AppCompatActivity {
    private MaterialCardView cardView;
    private List<PlantillaPartOfCar> partesCoche;
    private Coche coche;
    PlantillaPartOfCar plantilla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_datos);

        // obtengo el objeto coche enviado desde la actividad anterior
        coche = (Coche) getIntent().getSerializableExtra("claseCoche");

        // inicializo el List idImagen que guardará los id de las imagenes seleccionadas
        partesCoche = new ArrayList<PlantillaPartOfCar>();

    }

    /**
     * método que guarda en una lista los elementos (Partes del coche) que el usuario seleccione
     * @param view
     */
    public void addSelection(View view) {
        cardView = findViewById(view.getId());

        switch (cardView.getId()){
            case R.id.cardViewAceite:
                plantilla = new PlantillaPartOfCar(R.id.plantilla_aceiteLubricante, R.mipmap.ic_aceite_lubricante, "aceite lubricante");
                break;

            case R.id.cardViewAmortiguador:
                plantilla = new PlantillaPartOfCar(R.id.plantilla_amortiguadores, R.mipmap.ic_amortiguador, "amortiguador");
                break;

            case R.id.cardViewBateria:
                plantilla = new PlantillaPartOfCar(R.id.plantilla_bateria, R.mipmap.ic_bateria, "bateria");
                break;

            case R.id.cardViewCatalizador:
                plantilla = new PlantillaPartOfCar(R.id.plantilla_sistemaEscape, R.mipmap.ic_catalizador, "sistema de escape y catalizador");
                break;

            case R.id.cardViewFiltro:
                plantilla = new PlantillaPartOfCar(R.id.plantilla_filtros, R.mipmap.ic_filtro, "filtros");
                break;

            case R.id.cardViewCorreaDistribucion:
                plantilla = new PlantillaPartOfCar(R.id.plantilla_correaDistribucion, R.mipmap.ic_correoa_de_distribucion, "correa de distribucion");
                break;

            case R.id.cardViewFrenos:
                plantilla = new PlantillaPartOfCar(R.id.plantilla_frenos, R.mipmap.ic_frenos, "frenos");
                break;

            case R.id.cardViewLuces:
                plantilla = new PlantillaPartOfCar(R.id.plantilla_luces, R.mipmap.ic_luces, "luces");
                break;

            case R.id.cardViewRueda:
                plantilla = new PlantillaPartOfCar(R.id.plantilla_rueda, R.mipmap.ic_rueda, "ruedas");
                break;
        }
        // selecciona y deselecciona los cardView
        if(cardView.isChecked()){
            cardView.setChecked(false);
            Log.d("MainActivity", "remove id: " + plantilla.getNombre() + " | " + plantilla.getId());

            Iterator<PlantillaPartOfCar> iter= partesCoche.iterator();
            while (iter.hasNext()){
                if (iter.next().getNombre().equals(plantilla.getNombre())) {
                    iter.remove();
                }
            }
        }else{
            cardView.setChecked(true);
            Log.d("MainActivity", "add id: " + plantilla.getNombre() + " | " + plantilla.getId());
            partesCoche.add(plantilla);
        }
    }

    /**
     * método que envia la lista de selccion y los envia al intent activity_addCar
     * @param view
     */
    public void pulsar(View view) {
        for (PlantillaPartOfCar plant: partesCoche) {
            Log.d("MainActivity", "id: " + plant.getNombre() + " | " + plant.getId());
        }
        Intent intent = new Intent(activity_seleccionDatos.this,activity_addCar.class);

        Bundle extras = new Bundle();
        extras.putSerializable("lista", (Serializable) partesCoche);
        extras.putSerializable("claseCoche", coche);
        intent.putExtras(extras);
        startActivity(intent);

    }
}