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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import kz.dev.home.flos.R;
import kz.dev.home.flos.services.RequestHandler;
import kz.dev.home.flos.services.URLs;

import static kz.dev.home.flos.services.URLs.URL_NEW_TICKET;
import static kz.dev.home.flos.services.URLs.URL_CNFNT;

public class NewTiFragment extends Fragment implements View.OnClickListener {
    private SmsVerifyCatcher smsVerifyCatcher;
    private static final String TAG = "NewTiFragment :";
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 15000;
    private View rootView;
    private EditText etvCode, etTIEmail, etTIPhone,etTITitle,etTIDesc;
    private RadioGroup radioGroup;
    private String priority,uid,uphone;
    private Button saveButton;
    private String verCode;

    public NewTiFragment(){

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_newti, container,false);
        createMeViev();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            uid = bundle.getString("UID");
            uphone = bundle.getString("uphone");
        }


        return rootView;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createTicket() {
        etTIEmail = rootView.findViewById(R.id.nt_etEmail);
        etTIPhone = rootView.findViewById(R.id.nt_etPhone);
        etTITitle = rootView.findViewById(R.id.nt_etTitle);
        etTIDesc = rootView.findViewById(R.id.nt_etText);
        final String user_email = etTIEmail.getText().toString();
        final String user_phone = etTIPhone.getText().toString();
        final String ticket_title = etTITitle.getText().toString();
        final String ticket_description = etTIDesc.getText().toString();
        final String priority_ti = priority;
        final String smsCode = etvCode.getText().toString();
        @SuppressLint("StaticFieldLeak")
        class CreateNewTicket extends AsyncTask<Void, Void, String> {
            private ProgressBar progressBar;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                smsVerifyCatcher.onStop();
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
                params.put("VerCode", smsCode);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_NEW_TICKET, params);
            }
        }

        CreateNewTicket ct = new CreateNewTicket();
        ct.execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void smsVerCatch(){
          etvCode = (EditText) rootView.findViewById(R.id.nt_etvcode);
        final Button btnVerify = (Button) rootView.findViewById(R.id.btn_verify);
        //init SmsVerifyCatcher
        smsVerifyCatcher = new SmsVerifyCatcher(Objects.requireNonNull(getActivity()), new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
               String code = parseCode(message);//set code in edit text
                etvCode.setText(code);
                //then you can send verification code to server
            }
        });
        //set phone number filter if needed
        smsVerifyCatcher.setPhoneNumberFilter("76823683");
        smsVerifyCatcher.setFilter("[S][M][S][-][C][E][N][T][R][E]");
//        Log.d(TAG, verCode);
        //button for sending verification code manual
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTicket();
//                Log.d(TAG, verCode);
            }
        });
    }
    private void createMeViev(){
    try {
        saveButton = (Button) rootView.findViewById(R.id.nt_btnSave);
        saveButton.setOnClickListener(this);
    }catch (Exception e) {
        Log.d(TAG, String.valueOf(e));
    }

    radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroup);
    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
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
        }

    });
    int selectedId = radioGroup.getCheckedRadioButtonId();
    RadioButton prior_1 = (RadioButton) rootView.findViewById(R.id.prior_1);
    RadioButton prior_2 = (RadioButton) rootView.findViewById(R.id.prior_2);
    RadioButton prior_3 = (RadioButton) rootView.findViewById(R.id.prior_3);
    RadioButton prior_4 = (RadioButton) rootView.findViewById(R.id.prior_4);
    RadioButton prior_5 = (RadioButton) rootView.findViewById(R.id.prior_5);
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
            confirmTicket();
                    smsVerCatch();
        }
    }

    /**
     * Parse verification code
     *
     * @param message sms message
     * @return only four numbers from massage string
     */
    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{6}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        smsVerifyCatcher.onStart();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        smsVerifyCatcher.onStop();
//    }

    /**
     * need for Android 6 real time permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void confirmTicket() {
        @SuppressLint("StaticFieldLeak")
        class confirmNewTicket extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                smsVerifyCatcher.onStart();
            }

            @Override
            protected String doInBackground(Void... voids) {

                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("uid", uid);
                params.put("uphone", "7"+uphone);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_CNFNT, params);
            }
        }

        confirmNewTicket cnt = new confirmNewTicket();
        cnt.execute();
    }

}
