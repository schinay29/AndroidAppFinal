package xyz.hannah.hannahapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class activity_seleccionDatos extends AppCompatActivity {
    MaterialCardView cardView;


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

        MyAdapterRecyclerView adapter = new MyAdapterRecyclerView(this, s1, images);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linear = new LinearLayoutManager(this);
        linear.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linear);


    }




    public void addSelection(View view) {
        cardView = findViewById(view.getId());

        if(cardView.isChecked()){
            cardView.setChecked(false);
        }else{
            cardView.setChecked(true);
        }




        switch (view.getId()){
            case R.id.selec_aceiteLubricante:


                break;

            case R.id.selec_amortiguador:

                break;

            case R.id.selec_bateria:

                break;

            case R.id.selec_catalizador:

                break;

            case R.id.selec_filtro:

                break;

            case R.id.selec_correaDistribucion:

                break;

            case R.id.selec_frenos:

                break;

            case R.id.selec_luces:

                break;

            case R.id.selec_rueda:

                break;
        }


    }
}