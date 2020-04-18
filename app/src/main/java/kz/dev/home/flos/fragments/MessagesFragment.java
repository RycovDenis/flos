package kz.dev.home.flos.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import kz.dev.home.flos.R;

public class MessagesFragment extends Fragment {
    private String uid,fname,lname,email,role,roleId;
    @SuppressLint("SetTextI18n")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_messages, container,
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

        return rootView;
    }
}
