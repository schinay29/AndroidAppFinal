package xyz.hannah.hannahapp.ClasesAyuda;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import xyz.hannah.hannahapp.R;

public class AdapterRVInfo extends RecyclerView.Adapter<AdapterRVInfo.MyViewHolderInfo> {
    private List<PlantillaPartOfCar> partesCoche;
    private Context context;
    String[] modelos, ultVez;


    public AdapterRVInfo(Context ct, List<PlantillaPartOfCar> partesCoche) {
        context = ct;
        this.partesCoche = partesCoche;
        modelos = new String[partesCoche.size()];
        ultVez = new String[partesCoche.size()];
    }


    @NonNull
    @Override
    public MyViewHolderInfo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.content_info_partofcar, parent, false);
        return new MyViewHolderInfo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderInfo holder, int position) {
        //holder.itemView.setId(partesCoche.get(position).getId());
        holder.myText.setText(partesCoche.get(position).getNombre());
        holder.myImage.setImageResource(partesCoche.get(position).getIdImagen());


    }


    @Override
    public int getItemCount() {
        return partesCoche.size();
    }

    public String[] getModelos() {
        return modelos;
    }

    public String[] getUltVez() {
        return ultVez;
    }

    public class MyViewHolderInfo extends RecyclerView.ViewHolder {
        private TextView myText;
        private ImageView myImage;
        private EditText campo_modelo, campo_ultVez;

        public MyViewHolderInfo(@NonNull View itemView) {
            super(itemView);

            myText = itemView.findViewById(R.id.txt_PartOfCar);
            myImage = itemView.findViewById(R.id.imagen_PartOfCar);
            campo_modelo = itemView.findViewById(R.id.cp_modelo_partOfCar);
            campo_ultVez = itemView.findViewById(R.id.cp_ultVez_partOfCar);

            campo_modelo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    modelos[getAdapterPosition()] = s.toString();

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            campo_ultVez.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    ultVez[getAdapterPosition()] = s.toString();

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        }



    }



}
