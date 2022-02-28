package xyz.hannah.hannahapp.ClasesAyuda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import xyz.hannah.hannahapp.R;

public class AdapterRVSelection extends RecyclerView.Adapter<AdapterRVSelection.MyViewHolderSelection> {
    private List<PlantillaPartOfCar> partesCoche;
    private  Context context;

    public AdapterRVSelection(Context ct, List<PlantillaPartOfCar> partesCoche){
        context = ct;
        this.partesCoche = partesCoche;


    }

    @NonNull
    @Override
    public MyViewHolderSelection onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.content_selection, parent, false);

        return new MyViewHolderSelection(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderSelection holder, int position) {
        holder.itemView.setId(partesCoche.get(position).getId());
        holder.myText.setText(partesCoche.get(position).getNombre());
        holder.myImage.setImageResource(partesCoche.get(position).getIdImagen());

    }

    @Override
    public int getItemCount() {
        return partesCoche.size();
    }

    public class MyViewHolderSelection extends RecyclerView.ViewHolder{
        TextView myText;
        ImageView myImage;



        public MyViewHolderSelection(@NonNull View itemView) {
            super(itemView);
            myText = itemView.findViewById(R.id.textPart);
            myImage = itemView.findViewById(R.id.imgPart);
        }
    }
}
