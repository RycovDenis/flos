package kz.dev.home.flos.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.dev.home.flos.R;
import kz.dev.home.flos.datamodels.Ticket;

public class AdapterTicket extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Ticket> data = Collections.emptyList();
//    Ticket current;
//    int currentPos=0;
    private int blockedColorValue = Color.parseColor("#D30000");
    private int criticalColorValue = Color.parseColor("#FF2800");
    private int hightColorValue = Color.parseColor("#FFAB00");
    private int middleColorValue = Color.parseColor("#00B8D4");
    private int lowerColorValue = Color.parseColor("#64DD17");

    // create constructor to innitilize context and data sent from MainActivity
    public AdapterTicket(Context context, List<Ticket> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.cards_layout, parent,false);
        return new MyHolder(view);
    }

    // Bind data
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        Ticket current=data.get(position);
        myHolder.textTiID.setText("# " + current.tiId);
        myHolder.texttitle.setText(current.title);
        myHolder.textDesc.setText(current.text);
        switch (current.priority){
            case 1:
                myHolder.ivTiStatusPriority.setBorderColor(blockedColorValue);
                break;
            case 2:
                myHolder.ivTiStatusPriority.setBorderColor(criticalColorValue);
                break;
            case 3:
                myHolder.ivTiStatusPriority.setBorderColor(hightColorValue);
                break;
            case 4:
                myHolder.ivTiStatusPriority.setBorderColor(middleColorValue);
                break;
            case 5:
                myHolder.ivTiStatusPriority.setBorderColor(lowerColorValue);
                break;
        }
        switch (current.status){
            case 1:
                myHolder.ivTiStatusPriority.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_new, context.getApplicationContext().getTheme()));
                break;
            case 2:
                myHolder.ivTiStatusPriority.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_process, context.getApplicationContext().getTheme()));
                break;
            case 3:
                myHolder.ivTiStatusPriority.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_return, context.getApplicationContext().getTheme()));
                break;
            case 4:
                myHolder.ivTiStatusPriority.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_icon_success, context.getApplicationContext().getTheme()));
                break;
        }


//        myHolder.textStatus.setText(current.status);
        myHolder.textUserEmail.setText(current.tiEmail);
        myHolder.textUserPhone.setText(current.tiPhone);
        myHolder.textTiDate.setText(current.tiDate);
//        myHolder.textPrice.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    static class MyHolder extends RecyclerView.ViewHolder{

        TextView texttitle,textDesc,textTiDate;
        CardView cvTicket;
        CircleImageView ivTiStatusPriority;
        TextView textTiID,textUserEmail,textUserPhone;

        // create constructor to get widget reference
        MyHolder(View itemView) {
            super(itemView);
            textTiID = itemView.findViewById(R.id.text_id);
            texttitle= itemView.findViewById(R.id.text_title);
            textDesc= itemView.findViewById(R.id.text_desc);
            cvTicket = itemView.findViewById(R.id.card_view_ticket);
            ivTiStatusPriority = itemView.findViewById(R.id.iv_ti_status_priority);
            textTiDate = itemView.findViewById(R.id.text_date);
            textUserEmail = itemView.findViewById(R.id.text_user_email);
            textUserPhone = itemView.findViewById(R.id.text_user_phone);
        }

    }

}