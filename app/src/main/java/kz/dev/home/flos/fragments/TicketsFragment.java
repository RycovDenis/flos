package kz.dev.home.flos.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import kz.dev.home.flos.R;
import kz.dev.home.flos.adapters.AdapterTicket;
import kz.dev.home.flos.datamodels.Ticket;

import static kz.dev.home.flos.services.URLs.URL_TICKETS;

public class TicketsFragment extends Fragment {
    private static final String TAG = "TicketsFragment :";
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 15000;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar progressBar;
    private View rootView;
    private String uid,role;

    public TicketsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_tickets, container,
                false);
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swifeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncFetch().execute();
            }
        });
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            uid = bundle.getString("UID");
            role = bundle.getString("role_id");
        }


        //Make call to AsyncTask
        new AsyncFetch().execute();
        return rootView;
    }
    @SuppressLint("StaticFieldLeak")
    private class AsyncFetch extends AsyncTask<String, String, String> {
//        ProgressDialog pdLoading = new ProgressDialog(TicketsFragment.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            progressBar = (ProgressBar) rootView.findViewById(R.id.pgBar);
            progressBar.setVisibility(ProgressBar.VISIBLE);
// запускаем длительную операцию

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(URL_TICKETS+"id="+ uid +"&role=" + role);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            List<Ticket> data=new ArrayList<>();
            progressBar.setVisibility(ProgressBar.INVISIBLE);

            try {
                JSONObject obj = new JSONObject(s);
                if (!obj.getBoolean("error")) {
                    Toasty.success(Objects.requireNonNull(getActivity()).getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG, true).show();
//                    JSONObject ticketsJson = obj.getJSONObject("tickets");
                    JSONArray jArray = new JSONArray(obj.getString("tickets"));
                    for(int i=0;i<jArray.length();i++){
                        JSONObject json_data = jArray.getJSONObject(i);
                        Ticket ticketData = new Ticket();
                        ticketData.tiId= json_data.getInt("ti_id");
                        ticketData.title= json_data.getString("title");
                        ticketData.text= json_data.getString("text");
                        ticketData.userId= json_data.getInt("user_id");
                        ticketData.ownerId= json_data.getInt("owner_id");
                        ticketData.priority= json_data.getInt("priority");
                        ticketData.status= json_data.getInt("status");
                        ticketData.tiDate= json_data.getString("ti_date");
                        ticketData.tiEmail= json_data.getString("ti_email");
                        ticketData.tiPhone= json_data.getString("ti_phone");
                        data.add(ticketData);
                    }

                    // Setup and Handover data to recyclerview
                    RecyclerView mRVFishPrice = rootView.findViewById(R.id.fishPriceList);
                    AdapterTicket mAdapter = new AdapterTicket(Objects.requireNonNull(getActivity()).getApplicationContext(), data);
                    mRVFishPrice.setAdapter(mAdapter);
                    mRVFishPrice.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                } else {
                    Toasty.error(Objects.requireNonNull(getActivity()).getApplicationContext(), R.string.email_input_error,Toast.LENGTH_LONG, true).show();
                }
            } catch (JSONException e) {
                Log.d(TAG, String.valueOf(e));
                Toasty.error(Objects.requireNonNull(getActivity()).getApplicationContext(), e.toString(), Toast.LENGTH_LONG,true).show();
            }
            mSwipeRefreshLayout.setRefreshing(false);

        }

    }
}