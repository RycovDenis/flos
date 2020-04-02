package kz.dev.home.flos;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    private static RecyclerView.Adapter adapter;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    private RecyclerView.LayoutManager layoutManager;
    private static final String TAG = "Main:";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//                if (SharedPrefManager.getInstance(this).isLoggedIn()) {
//                    finish();
//                    startActivity(new Intent(this, ProfileActivity.class));
//                    return;
//                }
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<DataModel>();
        for (int i = 0; i < DataSet.title_Array.length; i++) {
            data.add(new DataModel(
                    DataSet.title_Array[i],
                    DataSet.desc_Array[i]
            ));
        }
        adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);
    }
}

