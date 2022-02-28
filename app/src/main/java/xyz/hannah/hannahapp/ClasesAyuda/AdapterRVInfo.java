package xyz.hannah.hannahapp.ClasesAyuda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import xyz.hannah.hannahapp.R;

public class AdapterRVInfo  extends RecyclerView.Adapter<AdapterRVInfo.MyViewHolderInfo> {
    private String data1[];
    private  ArrayList<Integer> images;
    private  Context context;


    public AdapterRVInfo(Context ct, String s1[], ArrayList<Integer> images){
        context = ct;
        data1 = s1;
        this.images = images;

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
        holder.myText.setText(data1[position]);
        holder.myImage.setImageResource(images.get(position));

    }


    @Override
    public int getItemCount() {
        return images.size();
    }

    public class MyViewHolderInfo extends RecyclerView.ViewHolder{
        private TextView myText;
        private ImageView myImage;
        private EditText campo_modelo, campo_ultVez;

        public MyViewHolderInfo(@NonNull View itemView) {
            super(itemView);

            myText = itemView.findViewById(R.id.txt_PartOfCar);
            myImage = itemView.findViewById(R.id.imagen_PartOfCar);

            campo_modelo = itemView.findViewById(R.id.cp_modelo_partOfCar);
            campo_ultVez = itemView.findViewById(R.id.cp_ultVez_partOfCar);
        }
    }

}
