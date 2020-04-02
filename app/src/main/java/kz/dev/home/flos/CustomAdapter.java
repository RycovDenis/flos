package kz.dev.home.flos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private ArrayList<DataModel> dataModel;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text_title;
        TextView text_desc;
        public MyViewHolder(View view) {
            super(view);
            this.text_title = (TextView) view.findViewById(R.id.text_title);
            this.text_desc = (TextView) view.findViewById(R.id.text_desc);
        }
    }
    public CustomAdapter(ArrayList<DataModel> data) {
        this.dataModel = data;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);
        // view.setOnClickListener(MainActivity.myOnClickListener);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        TextView text_title = holder.text_title;
        TextView text_desc = holder.text_desc;
        text_title.setText(dataModel.get(position).getTitle());
        text_desc.setText(dataModel.get(position).getDesc());
    }
    @Override
    public int getItemCount() {
        return dataModel.size();
    }
}
