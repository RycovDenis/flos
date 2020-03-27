package kz.dev.home.flos.ContentActivitys;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kz.dev.home.flos.MainActivity;

import kz.dev.home.flos.R;

public class ProfileActivity extends MainActivity {
    TextView userIDTv,userFullNAme,userSysRole,emailTv,phoneTv,innTv,ipTv,tabnumTv,cpostTv,datePassTv,regionTv;
    Button homebtn;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        init();
        setContentView(R.layout.activity_profile);
        getViews();
    }

    @SuppressLint("SetTextI18n")
    private void getViews() {

        userFullNAme = findViewById(R.id.user_name);
        userSysRole = findViewById(R.id.user_role);
        userIDTv = findViewById(R.id.tv_user_id);
        emailTv = findViewById(R.id.tv_email);
        phoneTv = findViewById(R.id.tv_mobile_phone);
        ipTv = findViewById(R.id.tv_ip);
        tabnumTv = findViewById(R.id.tv_tab_num);
        cpostTv = findViewById(R.id.tv_company_post);
        innTv = findViewById(R.id.tv_inn);
        datePassTv = findViewById(R.id.tv_date_password);
        regionTv = findViewById(R.id.tv_region);
        userFullNAme.setText(sharedPreferences.getString("l_name","")+ " " +sharedPreferences.getString("f_name",""));
        userSysRole.setText(sharedPreferences.getString("role",""));
        userIDTv.setText(sharedPreferences.getString("user_id",""));
        emailTv.setText(sharedPreferences.getString("email",""));
        innTv.setText(sharedPreferences.getString("inn",""));
        phoneTv.setText(sharedPreferences.getString("mobile_phone",""));
        ipTv.setText(sharedPreferences.getString("ip",""));
        tabnumTv.setText(sharedPreferences.getString("tab_num",""));
        cpostTv.setText(sharedPreferences.getString("company_post",""));
        datePassTv.setText(sharedPreferences.getString("date_password",""));
        regionTv.setText(sharedPreferences.getString("region",""));
        homebtn = findViewById(R.id.homeBtn);



        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect back to login page
                logout();
                intent = new Intent(context, HomeActivity.class);
                //remove all previous stack activities
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
