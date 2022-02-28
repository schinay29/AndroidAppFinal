package xyz.hannah.hannahapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import xyz.hannah.hannahapp.ClasesAyuda.Coche;

public class activity_seleccionDatos extends AppCompatActivity {
    private MaterialCardView cardView;
    private List<Integer> idImagen;
    private Coche coche;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_datos);

        // obtengo el objeto coche enviado desde la actividad anterior
        coche = (Coche) getIntent().getSerializableExtra("claseCoche");

        // inicializo el List idImagen que guardará los id de las imagenes seleccionadas
        idImagen = new ArrayList<Integer>();



    }




    public void addSelection(View view) {
        Integer id = 0;
        cardView = findViewById(view.getId());
        //cardView.setId();

        switch (cardView.getId()){
            case R.id.cardViewAceite:
                id = R.mipmap.ic_aceite_lubricante;
                break;

            case R.id.cardViewAmortiguador:
                id = R.mipmap.ic_amortiguador;
                break;

            case R.id.cardViewBateria:
                id = R.mipmap.ic_bateria;
                break;

            case R.id.cardViewCatalizador:
                id = R.mipmap.ic_catalizador;
                break;

            case R.id.cardViewFiltro:
                id = R.mipmap.ic_filtro;
                break;

            case R.id.cardViewCorreaDistribucion:
                id = R.mipmap.ic_correoa_de_distribucion;
                break;

            case R.id.cardViewFrenos:
                id = R.mipmap.ic_frenos;
                break;

            case R.id.cardViewLuces:
                id = R.mipmap.ic_luces;
                break;

            case R.id.cardViewRueda:
                id = R.mipmap.ic_rueda;
                break;
        }

        if(cardView.isChecked()){
            cardView.setChecked(false);
            Log.d("MainActivity", "remove id: " + id);
            idImagen.remove(id);
            //idImagen.
        }else{
            cardView.setChecked(true);
            Log.d("MainActivity", "add id: " + id);
            idImagen.add(id);
        }


    }

    public void pulsar(View view) {
        for (int ident: idImagen) {
            Log.d("MainActivity", "id: " + ident);
        }

        Intent intent = new Intent(activity_seleccionDatos.this,activity_addCar.class);

        Bundle extras = new Bundle();
        extras.putIntegerArrayList("lista", (ArrayList<Integer>) idImagen);
        //extras.putString("ultVez", ultVez);
        extras.putSerializable("claseCoche", coche);
        //intent.putExtras("claseCoche", coche);
        intent.putExtras(extras);
        startActivity(intent);


    }
}