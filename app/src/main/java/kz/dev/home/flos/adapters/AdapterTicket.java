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

import java.util.List;

import kz.dev.home.flos.R;
import kz.dev.home.flos.datamodels.Ticket;

public class AdapterTicket extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mCtx;
    private List<Ticket> ticketList;

    private final int blockedColorValue = Color.parseColor("#D30000");
    private final int criticalColorValue = Color.parseColor("#FF2800");
    private final int hightColorValue = Color.parseColor("#FFAB00");
    private final int middleColorValue = Color.parseColor("#00B8D4");
    private final int lowerColorValue = Color.parseColor("#64DD17");

    public AdapterTicket(Context mCtx, List<Ticket> ticketList){
        this.mCtx = mCtx;
        this.ticketList = ticketList;
    }

    // Inflate the layout when viewholder created
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mCtx).inflate(R.layout.cards_layout, parent,false);
        return new MyHolder(view);
    }

    // Bind data
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder= (MyHolder) holder;
        Ticket tl=ticketList.get(position);
        String priortyLabel = mCtx.getResources().getString(R.string.label_priority);
        String statusLabel =  mCtx.getResources().getString(R.string.label_status);
        String dateLabel =  mCtx.getResources().getString(R.string.label_date);
        String uphoneLabel =  mCtx.getResources().getString(R.string.label_uphone);
        String emailLabel =  mCtx.getResources().getString(R.string.label_email);

        myHolder.textTiIDTitle.setText("# "+ tl.getTiId().toString()+" "+tl.getTitle());
        myHolder.textDesc.setText(tl.getText());
        myHolder.textTiDate.setText(dateLabel+": " + tl.getTiDate());
        myHolder.textTiUphone.setText(uphoneLabel+": " + tl.getTiPhone());
        myHolder.textTiUphone.setVisibility(View.GONE);
        myHolder.textUserEmail.setText(emailLabel+": " + tl.getTiEmail());
        myHolder.textUserEmail.setVisibility(View.GONE);
        myHolder.textUserFullNamw.setText("#");
        myHolder.textUserFullNamw.setVisibility(View.GONE);


        switch (tl.getPriority()){
            case 1:
                myHolder.tvTiPriority.setTextColor(blockedColorValue);
                myHolder.tvTiPriority.setText(priortyLabel +": "+mCtx.getResources().getString(R.string.prior_1));
                break;
            case 2:
                myHolder.tvTiPriority.setTextColor(criticalColorValue);
                myHolder.tvTiPriority.setText(priortyLabel +": "+mCtx.getResources().getString(R.string.prior_2));
                break;
            case 3:
                myHolder.tvTiPriority.setTextColor(hightColorValue);
                myHolder.tvTiPriority.setText(priortyLabel +": "+mCtx.getResources().getString(R.string.prior_3));
                break;
            case 4:
                myHolder.tvTiPriority.setTextColor(middleColorValue);
                myHolder.tvTiPriority.setText(priortyLabel +": "+mCtx.getResources().getString(R.string.prior_4));
                break;
            case 5:
                myHolder.tvTiPriority.setTextColor(lowerColorValue);
                myHolder.tvTiPriority.setText(priortyLabel +": "+mCtx.getResources().getString(R.string.prior_5));
                break;
        }
        switch (tl.getStatus()){
            case 1:
                myHolder.tvTiStatus.setText(statusLabel + ": " +mCtx.getResources().getString(R.string.status_1));
                break;
            case 2:
                myHolder.tvTiStatus.setText(statusLabel + ": " +mCtx.getResources().getString(R.string.status_2));
                break;
            case 3:
                myHolder.tvTiStatus.setText(statusLabel + ": " +mCtx.getResources().getString(R.string.status_3));
                break;
            case 4:
                myHolder.tvTiStatus.setText(statusLabel + ": " +mCtx.getResources().getString(R.string.status_4));
                break;
        }
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{

        final TextView textTiIDTitle;
        final TextView  textDesc;
        final TextView textTiDate;
        final TextView tvTiPriority;
        final TextView tvTiStatus;
        final TextView textTiUphone;
        final TextView textUserEmail;
        final TextView textUserFullNamw;
        final CardView cvTicket;
        MyHolder(View itemView) {
            super(itemView);
            textTiIDTitle = itemView.findViewById(R.id.tv_tivIDTitle);
            textDesc= itemView.findViewById(R.id.tv_tivDesc);
            cvTicket = itemView.findViewById(R.id.cvTicket);
            tvTiPriority = itemView.findViewById(R.id.tv_tivPriority);
            tvTiStatus = itemView.findViewById(R.id.tv_tivStatus);
            textTiDate = itemView.findViewById(R.id.tv_tivDate);
            textTiUphone = itemView.findViewById(R.id.tv_tivuPhone);
            textUserEmail = itemView.findViewById(R.id.tv_tivEmail);
            textUserFullNamw = itemView.findViewById(R.id.tv_tivFullName);
        }
    }

}