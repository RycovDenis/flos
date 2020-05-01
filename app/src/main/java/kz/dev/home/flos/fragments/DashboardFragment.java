package kz.dev.home.flos.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

import kz.dev.home.flos.R;
import kz.dev.home.flos.datamodels.User;
import kz.dev.home.flos.helper.SharedPrefManager;

public class DashboardFragment extends Fragment {
    @SuppressLint("SetTextI18n")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dashboard, container,
                false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String uid = bundle.getString("UID");
            String fname = bundle.getString("fname");
            String lname = bundle.getString("lname");
            String email = bundle.getString("email");
            String role = bundle.getString("role_name");
            String roleId = bundle.getString("role_id");
        }
        return rootView;
    }




}
