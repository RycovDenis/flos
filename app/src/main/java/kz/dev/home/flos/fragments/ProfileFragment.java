package kz.dev.home.flos.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import kz.dev.home.flos.R;
import kz.dev.home.flos.services.RequestHandler;
import kz.dev.home.flos.services.URLs;

//import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private static final String TAG = "TicketsFragment :";
    private View rootView;
    private String uid,role;
    private TextView uName,uRole,vtUID,vtEmail,vtPhone,vtINN,vtIP,vtTabNum,vtCP,vtRegion,vtDatePass;
    private String parsedValueEmail;
    private String parsedValueLname;
    private String parsedValueUid;
    private String parsedValueFname;
    private String parsedValueMname;
    private String parsedValueUINN;
    private String parsedValueUphone;
    private String parsedValueUip;
    private String parsedValueUTabNum;
    private String parsedValueUCP;
    private String parsedValueUregion;
    private String parsedValueUDPass;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile, container,
                false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            uid = bundle.getString("uid");
            role = bundle.getString("rolename");
        }
        getUserInfo();
        getTextViews();
        setTextHeaderViews();
        return rootView;
    }

    @SuppressLint("SetTextI18n")
    private void setTextHeaderViews() {
        uRole.setText(role);
    }
    @SuppressLint("SetTextI18n")
    private void setTextViews() {
        uName.setText(parsedValueLname + " " +parsedValueFname + " " +parsedValueMname);
        vtUID.setText(parsedValueUid);
        vtEmail.setText(parsedValueEmail);
        vtPhone.setText(parsedValueUphone);
        vtINN.setText(parsedValueUINN);
        vtIP.setText(parsedValueUip);
        vtTabNum.setText(parsedValueUTabNum);
        vtCP.setText(parsedValueUCP);
        vtRegion.setText(parsedValueUregion);
        vtDatePass.setText(parsedValueUDPass);
    }

    private void getTextViews() {
        uName = rootView.findViewById(R.id.user_name);
        uRole = rootView.findViewById(R.id.user_role);
//        CircleImageView imageAvatar = rootView.findViewById(R.id.image_avatar);
        vtUID = rootView.findViewById(R.id.tv_user_id);
        vtEmail = rootView.findViewById(R.id.tv_email);
        vtPhone = rootView.findViewById(R.id.tv_mobile_phone);
        vtINN = rootView.findViewById(R.id.tv_inn);
        vtIP = rootView.findViewById(R.id.tv_ip);
        vtTabNum = rootView.findViewById(R.id.tv_tab_num);
        vtCP = rootView.findViewById(R.id.tv_company_post);
        vtRegion = rootView.findViewById(R.id.tv_region);
        vtDatePass = rootView.findViewById(R.id.tv_date_password);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    private void getUserInfo() {
        @SuppressLint("StaticFieldLeak")
        class UseInfo extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toasty.info(Objects.requireNonNull(getActivity()).getApplicationContext(), obj.getString("message"),Toast.LENGTH_LONG, true).show();

                        String str = obj.getString("uinfo");
                        JWT jwt = new JWT(str);
                        Claim email = jwt.getClaim("email");
                        Claim username = jwt.getClaim("username");
                        Claim usid = jwt.getClaim("user_id");
                        Claim fname = jwt.getClaim("f_name");
                        Claim lname = jwt.getClaim("l_name");
                        Claim mname = jwt.getClaim("m_name");
                        Claim uinn = jwt.getClaim("inn");
                        Claim uphone = jwt.getClaim("mobile_phone");
                        Claim uip = jwt.getClaim("INET_NTOA(ip)");
                        Claim utabnum = jwt.getClaim("tab_num");
                        Claim ucp = jwt.getClaim("company_post");
                        Claim uregion = jwt.getClaim("region");
                        Claim udp = jwt.getClaim("date_password");

                        parsedValueEmail = email.asString();
                        username.asString();
                        parsedValueUid = usid.asString();
                        parsedValueFname = fname.asString();
                        parsedValueLname = lname.asString();
                        parsedValueMname = mname.asString();
                        parsedValueUINN = uinn.asString();
                        parsedValueUphone = uphone.asString();
                        parsedValueUip = uip.asString();
                        parsedValueUTabNum = utabnum.asString();
                        parsedValueUCP = ucp.asString();
                        parsedValueUregion = uregion.asString();
                        parsedValueUDPass = udp.asString();
                        setTextViews();
                    } else {
                        Toasty.error(Objects.requireNonNull(getActivity()).getApplicationContext(), R.string.email_input_error,Toast.LENGTH_LONG, true).show();
                    }
                } catch (JSONException e) {
                    Log.d(TAG,e.toString());
                    Toasty.error(Objects.requireNonNull(getActivity()).getApplicationContext(),TAG+" "+e.toString(),Toast.LENGTH_LONG, true).show();

                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("uid", uid);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_GETUINFO, params);
            }
        }
        UseInfo ui = new UseInfo();
        ui.execute();
    }


}
