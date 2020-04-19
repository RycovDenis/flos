package kz.dev.home.flos.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import kz.dev.home.flos.R;
import kz.dev.home.flos.adapters.AdapterTicket;
import kz.dev.home.flos.datamodels.Ticket;
import kz.dev.home.flos.services.RequestHandler;
import kz.dev.home.flos.services.URLs;

public class TicketsFragment extends Fragment{

    private static final String TAG = "TicketsFragment :";
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 15000;
    private SwipeRefreshLayout mSwipeRefreshLayout;
//    private AdapterTicket.OnItemClickListener listener;
    private View rootView;
    private String uid,role;



    public TicketsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_tickets, container,
                false);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swifeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(() -> new GetTicket().execute());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            uid = bundle.getString("uid");
            role = bundle.getString("roleid");
        }

        new GetTicket().execute();
        return rootView;
    }

        @SuppressLint("StaticFieldLeak")
        class GetTicket extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                List<Ticket> data=new ArrayList<>();
                try {
                    JSONObject obj = new JSONObject(s);
                    if (!obj.getBoolean("error")) {
                        Toasty.success(requireActivity().getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG, true).show();
                        JSONArray jArray = new JSONArray(obj.getString("tickets"));
                        for(int i=0;i<jArray.length();i++){
                            JSONObject json_data = jArray.getJSONObject(i);
                            Ticket ticketData = new Ticket();
                            ticketData.setTiId(json_data.getInt("ti_id"));
                            ticketData.setTitle(json_data.getString("title"));
                            ticketData.setText(json_data.getString("text"));
                            ticketData.setUserId(json_data.getInt("user_id"));
                            ticketData.setOwnerId(json_data.getInt("owner_id"));
                            ticketData.setPriority (json_data.getInt("priority"));
                            ticketData.setStatus (json_data.getInt("status"));
                            ticketData.setTiDate (json_data.getString("ti_date"));
                            ticketData.setTiEmail(json_data.getString("ti_email"));
                            ticketData.setTiPhone(json_data.getString("ti_phone"));
                            data.add(ticketData);
                        }

                        RecyclerView mRVFishPrice = rootView.findViewById(R.id.fishPriceList);
                        mRVFishPrice.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));
                        AdapterTicket mAdapter = new AdapterTicket(requireActivity().getApplicationContext(), data);
                        mRVFishPrice.setAdapter(mAdapter);
                    }
                } catch (JSONException e) {
                    Log.d(TAG, String.valueOf(e));
                    Toasty.error(requireActivity().getApplicationContext(), e.toString(), Toast.LENGTH_LONG,true).show();
                }
                mSwipeRefreshLayout.setRefreshing(false);

            }

            @Override
            protected String doInBackground(Void... voids) {

                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id", uid);
                params.put("role", role);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_TICKETS, params);
            }
        }

}