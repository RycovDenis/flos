package kz.dev.home.flos.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.amulyakhare.textdrawable.TextDrawable;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.dev.home.flos.R;

public class ProfileFragment extends Fragment {
    private static final String TAG = "TicketsFragment :";
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 15000;
    private View rootView;
    private String uid,role,fname,lname;
    private TextView uName,uRole;
    private CircleImageView imageAvatar;
    public ProfileFragment() {
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile, container,
                false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            uid = bundle.getString("UID");
            fname = bundle.getString("fname");
            lname = bundle.getString("lname");
            role = bundle.getString("role_name");
        }
        getTextViews();
        setTextViews();
        return rootView;
    }

    @SuppressLint("SetTextI18n")
    private void setTextViews() {
        uName.setText(fname + " " + lname);
        uRole.setText(role);
        TextDrawable drawable = TextDrawable.builder()
                .buildRect("D", Color.RED);
        imageAvatar.setImageDrawable(drawable);
    }

    private void getTextViews() {
        uName = rootView.findViewById(R.id.user_name);
        uRole = rootView.findViewById(R.id.user_role);
        imageAvatar =  rootView.findViewById(R.id.image_avatar);
    }

}
