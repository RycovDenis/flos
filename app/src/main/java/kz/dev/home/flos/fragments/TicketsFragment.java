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
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String uid,role;
    private RecyclerView recyclerView;


    public TicketsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tickets, container,
                false);
        super.onCreate(savedInstanceState);
        recyclerView = rootView.findViewById(R.id.fishPriceList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));

        mSwipeRefreshLayout = rootView.findViewById(R.id.swifeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this::getTickets);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            uid = bundle.getString("uid");
            role = bundle.getString("roleid");
        }
        getTickets();
        return rootView;
    }
    private void getTickets() {

        @SuppressLint("StaticFieldLeak")
        class GetTicket extends AsyncTask<Void, Void, String> {

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

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                List<Ticket> data = new ArrayList<>();
                try {
                    JSONObject obj = new JSONObject(s);
                    if (!obj.getBoolean("error")) {
                        Toasty.success(requireActivity().getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG, true).show();
                        JSONArray jArray = new JSONArray(obj.getString("tickets"));
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json_data = jArray.getJSONObject(i);
                            Ticket ticketData = new Ticket();
                            ticketData.setTiId(json_data.getInt("ti_id"));
                            ticketData.setTitle(json_data.getString("title"));
                            ticketData.setText(json_data.getString("text"));
                            ticketData.setUserId(json_data.getInt("user_id"));
                            ticketData.setOwnerId(json_data.getInt("owner_id"));
                            ticketData.setPriority(json_data.getInt("priority"));
                            ticketData.setStatus(json_data.getInt("status"));
                            ticketData.setTiDate(json_data.getString("ti_date"));
                            ticketData.setTiEmail(json_data.getString("ti_email"));
                            ticketData.setTiPhone(json_data.getString("ti_phone"));
                            data.add(ticketData);
                        }
                        AdapterTicket mAdapter = new AdapterTicket(requireActivity().getApplicationContext(), data);
                        recyclerView.setAdapter(mAdapter);
                    }
                } catch (JSONException e) {
                    Log.d(TAG, String.valueOf(e));
                    Toasty.error(requireActivity().getApplicationContext(), e.toString(), Toast.LENGTH_LONG, true).show();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
        GetTicket gt = new GetTicket();
        gt.execute();
    }

}