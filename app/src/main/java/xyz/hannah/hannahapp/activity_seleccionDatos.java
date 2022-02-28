package xyz.hannah.hannahapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class activity_seleccionDatos extends AppCompatActivity {
    MaterialCardView cardView;

    List<Integer> idImagen;
    RecyclerView recyclerView;
    String s1[];
    int images[]={R.mipmap.ic_aceite_lubricante, R.mipmap.ic_amortiguador, R.mipmap.ic_bateria, R.mipmap.ic_catalizador, R.mipmap.ic_rueda,
            R.mipmap.ic_correoa_de_distribucion, R.mipmap.ic_filtro, R.mipmap.ic_frenos, R.mipmap.ic_luces};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_datos);

        s1 = getResources().getStringArray(R.array.partes_coche);
        recyclerView = findViewById(R.id.recyclerView);
        idImagen = new ArrayList<Integer>();

        MyAdapterRecyclerView adapter = new MyAdapterRecyclerView(this, s1, (ArrayList<Integer>) idImagen);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linear = new LinearLayoutManager(this);
        linear.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linear);



    }




    public void addSelection(View view) {
        Integer id = 0;
        cardView = findViewById(view.getId());

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
        Intent intent = new Intent(activity_seleccionDatos.this,activity_home.class);

        Bundle extras = new Bundle();
        extras.putIntegerArrayList("lista", (ArrayList<Integer>) idImagen);
        //extras.putString("ultVez", ultVez);

        intent.putExtras(extras);
        startActivity(intent);


    }
}