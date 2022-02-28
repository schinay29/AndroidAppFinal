package xyz.hannah.hannahapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterRecyclerView extends RecyclerView.Adapter<MyAdapterRecyclerView.MyViewHolder> {
    String data1[];
    ArrayList<Integer> images;
    Context context;

    public  MyAdapterRecyclerView(Context ct, String s1[], ArrayList<Integer> images){
        context = ct;
        data1 = s1;
        this.images = images;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.content_selection, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText.setText(data1[position]);
        holder.myImage.setImageResource(images.get(position));

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView myText;
        ImageView myImage;

        public ImageView getMyImage() {
            return myImage;
        }

        public void setMyImage(ImageView myImage) {
            this.myImage = myImage;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText = itemView.findViewById(R.id.textPart);
            myImage = itemView.findViewById(R.id.imgPart);
        }
    }
}
