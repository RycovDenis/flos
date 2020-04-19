package kz.dev.home.flos.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import kz.dev.home.flos.R;
import kz.dev.home.flos.services.RequestHandler;
import kz.dev.home.flos.services.URLs;
public class NewTiFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "NewTiFragment :";
    private View rootView;
    private String priority;
    private String uid;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_newti, container,false);
        createMeViev();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            uid = bundle.getString("uid");
            String uphone = bundle.getString("uphone");
        }
        return rootView;

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createTicket() {
        EditText etTIEmail = rootView.findViewById(R.id.nt_etEmail);
        EditText etTIPhone = rootView.findViewById(R.id.nt_etPhone);
        EditText etTITitle = rootView.findViewById(R.id.nt_etTitle);
        EditText etTIDesc = rootView.findViewById(R.id.nt_etText);
        final String user_email = etTIEmail.getText().toString();
        final String user_phone = etTIPhone.getText().toString();
        final String ticket_title = etTITitle.getText().toString();
        final String ticket_description = etTIDesc.getText().toString();
        final String priority_ti = priority;
        @SuppressLint("StaticFieldLeak")
        class CreateNewTicket extends AsyncTask<Void, Void, String> {
            private ProgressBar progressBar;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = rootView.findViewById(R.id.ti_pgb);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                progressBar.setVisibility(View.GONE);
                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toasty.info(Objects.requireNonNull(getActivity()).getApplicationContext(), obj.getString("message")+" ticket id:"+obj.getString("result"), Toast.LENGTH_LONG, true).show();
                    } else {
                        Toasty.error(Objects.requireNonNull(getActivity()).getApplicationContext(), R.string.email_input_error,Toast.LENGTH_LONG, true).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {

                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("email", user_email);
                params.put("UserPhone", user_phone);
                params.put("ticketTitle", ticket_title);
                params.put("ticketPriority", priority_ti);
                params.put("TicketDesc", ticket_description);
                params.put("UserID", uid);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_NEW_TICKET, params);
            }
        }

        CreateNewTicket ct = new CreateNewTicket();
        ct.execute();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createMeViev(){
    try {
        Button saveButton = rootView.findViewById(R.id.nt_btnSave);
        saveButton.setOnClickListener(this);
    }catch (Exception e) {
        Log.d(TAG, String.valueOf(e));
    }

        RadioGroup radioGroup = rootView.findViewById(R.id.radioGroup);
    radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
        // find which radio button is selected
        if(checkedId == R.id.prior_1) {
            priority = "1";
        } else if(checkedId == R.id.prior_2) {
            priority = "2";
        } else if(checkedId == R.id.prior_3) {
            priority = "3";
        }else if(checkedId == R.id.prior_4) {
            priority = "4";
        }else if(checkedId == R.id.prior_5) {
            priority = "5";

        }
    });
    int selectedId = radioGroup.getCheckedRadioButtonId();
    RadioButton prior_1 = rootView.findViewById(R.id.prior_1);
    RadioButton prior_2 = rootView.findViewById(R.id.prior_2);
    RadioButton prior_3 = rootView.findViewById(R.id.prior_3);
    RadioButton prior_4 = rootView.findViewById(R.id.prior_4);
    RadioButton prior_5 = rootView.findViewById(R.id.prior_5);
    // find which radioButton is checked by id
    if(selectedId == prior_1.getId()) {
        priority = "1";
    } else if(selectedId == prior_2.getId()) {
        priority = "2";
    } else if(selectedId == prior_3.getId()) {
        priority = "3";
    }else if(selectedId == prior_4.getId()) {
        priority = "4";
    }else if(selectedId == prior_5.getId()) {
        priority = "5";
    }
}
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(View v) {
        //do what you want to do when button is clicked
        if (v.getId() == R.id.nt_btnSave) {
            createTicket();
        }
    }

}
