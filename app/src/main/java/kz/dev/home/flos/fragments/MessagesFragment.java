package kz.dev.home.flos.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import kz.dev.home.flos.R;

//bundle.putString("UID", parsedValueUid);
//        bundle.putString("fname", parsedValueFname);
//        bundle.putString("lname", parsedValueLname);
//        bundle.putString("email", parsedValueEmail);
//        bundle.putString("role", parsedValueRole);

public class MessagesFragment extends Fragment {
    private static final String TAG = "TicketsFragment :";
    private View rootView;
    private String uid,fname,lname,email,role,roleId;
    @SuppressLint("SetTextI18n")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_messages, container,
                false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            uid = bundle.getString("UID");
            fname = bundle.getString("fname");
            lname = bundle.getString("lname");
            email = bundle.getString("email");
            role = bundle.getString("role_name");
            roleId = bundle.getString("role_id");
        }

       TextView tvJWT = rootView.findViewById(R.id.textView2);
        tvJWT.setText(uid +" "+ " " + fname +" " + " "+ lname + " "+ email +" " + role + " "+ roleId);
        return rootView;
    }
}
