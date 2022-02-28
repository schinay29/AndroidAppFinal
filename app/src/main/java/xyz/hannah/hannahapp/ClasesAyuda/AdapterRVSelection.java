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

import xyz.hannah.hannahapp.R;

public class AdapterRVSelection extends RecyclerView.Adapter<AdapterRVSelection.MyViewHolderSelection> {
    private String data1[];
    private  ArrayList<Integer> images;
    private  Context context;

    public AdapterRVSelection(Context ct, String s1[], ArrayList<Integer> images){
        context = ct;
        data1 = s1;
        this.images = images;


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
        holder.myText.setText(data1[position]);
        holder.myImage.setImageResource(images.get(position));

    }

    @Override
    public int getItemCount() {
        return images.size();
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
