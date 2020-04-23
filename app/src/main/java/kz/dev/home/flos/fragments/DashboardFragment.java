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
    private View rootView;
    private CardView cvOpened,cvClosed,cvProcessed,cvRedirected;
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
        initViews();
        oncCLkListener();
        return rootView;
    }

    private void oncCLkListener() {
        assert getFragmentManager() != null;
        FragmentTransaction ftrans = getFragmentManager().beginTransaction();
        if (item == 1) {
            ftrans.replace(R.id.container, fglavnaya);

        } else if (item == 2) {
            ftrans.replace(R.id.container, fMusey);

        } else if (item == 3) {
            ftrans.replace(R.id.container, fvistavki);

        }ftrans.commit();
    }



    private void initViews() {
        cvOpened = rootView.findViewById(R.id.cv_openedID);
        cvClosed = rootView.findViewById(R.id.cv_closedID);
        cvProcessed = rootView.findViewById(R.id.cv_procesedID);
        cvRedirected = rootView.findViewById(R.id.cv_redirectedID);
    }
}
