package kz.dev.home.flos.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.dev.home.flos.R;
import kz.dev.home.flos.datamodels.Ticket;

public class AdapterTicket extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;

    private List<Ticket> data;

    private final int blockedColorValue = Color.parseColor("#D30000");
    private final int criticalColorValue = Color.parseColor("#FF2800");
    private final int hightColorValue = Color.parseColor("#FFAB00");
    private final int middleColorValue = Color.parseColor("#00B8D4");
    private final int lowerColorValue = Color.parseColor("#64DD17");



    public AdapterTicket(Context context, List<Ticket> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // Inflate the layout when viewholder created
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.rowlayout, parent,false);
        return new MyHolder(view);
    }

    // Bind data
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder= (MyHolder) holder;
        Ticket current=data.get(position);
        myHolder.texttitle.setText("# "+ current.getTiId()+"  " + current.getTitle());
        myHolder.textDesc.setText(current.getText());
        switch (current.getPriority()){
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
        switch (current.getStatus()){
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

        myHolder.textTiDate.setText(current.getTiDate());
        myHolder.buttonViewOption.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(AdapterTicket.this.context, v);
            popupMenu.getMenuInflater().inflate(R.menu.ticket_menu, popupMenu.getMenu());
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_vwt:
                        //handle menu1 click
                        break;
                    case R.id.menu_act:
                        //handle menu2 click
                        break;
                    case R.id.menu_rjt:
                        //handle menu3 click
                        break;
                }
                return false;
            });
        });
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{

        final TextView texttitle;
        final TextView textDesc;
        final TextView textTiDate;
        final CardView cvTicket;
        final CircleImageView ivTiStatusPriority;
        final TextView buttonViewOption;

//        final TextView textTiID;
//        final TextView textUserEmail;
//        final TextView textUserPhone;

        // create constructor to get widget reference
        MyHolder(View itemView) {
            super(itemView);
//            textTiID = itemView.findViewById(R.id.text_id);
            texttitle= itemView.findViewById(R.id.text_title);
            textDesc= itemView.findViewById(R.id.text_desc);
            cvTicket = itemView.findViewById(R.id.card_view_ticket);
            ivTiStatusPriority = itemView.findViewById(R.id.iv_ti_status_priority);
            textTiDate = itemView.findViewById(R.id.text_date);
            buttonViewOption = itemView.findViewById(R.id.textViewOptions);
//            textUserEmail = itemView.findViewById(R.id.text_user_email);
//            textUserPhone = itemView.findViewById(R.id.text_user_phone);


        }

    }

}