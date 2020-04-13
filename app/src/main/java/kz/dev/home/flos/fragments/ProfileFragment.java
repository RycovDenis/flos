package kz.dev.home.flos.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.dev.home.flos.R;

public class ProfileFragment extends Fragment {
    private static final String TAG = "TicketsFragment :";
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
    }

    private void getTextViews() {
        uName = rootView.findViewById(R.id.user_name);
        uRole = rootView.findViewById(R.id.user_role);
        imageAvatar =  rootView.findViewById(R.id.image_avatar);
    }

    private void jwtUserParse(){
//        Intent intent = getIntent();
//        token = intent.getStringExtra("token");
//        assert token != null;
        JWT jwt = new JWT(uinfo);
        Claim uid = jwt.getClaim("uid");
        Claim firstname = jwt.getClaim("firstname");
        Claim lastname = jwt.getClaim("lastname");
        Claim email = jwt.getClaim("email");
        Claim role_id = jwt.getClaim("role_id");
        Claim u_phone = jwt.getClaim("mphone");
        Claim role_name = jwt.getClaim("role_name");
        parsedValueUid = uid.asString();
        parsedValueFname = firstname.asString();
        parsedValueLname = lastname.asString();
        parsedValueEmail = email.asString();
        parsedValueUphone = u_phone.asString();
        parsedValueRoleID = role_id.asString();
        parsedValueRoleName = role_name.asString();
    }


}
