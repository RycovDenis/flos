package kz.dev.home.flos.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kz.dev.home.flos.R;
import kz.dev.home.flos.adapters.TasksAdapter;
import kz.dev.home.flos.datamodels.Task;
import kz.dev.home.flos.helper.DatabaseClient;

public class TasksFragment extends Fragment {
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tasks, container,
                false);
        super.onCreate(savedInstanceState);

        recyclerView = rootView.findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));



        getTasks();
    return rootView;
    }


    private void getTasks() {
        @SuppressLint("StaticFieldLeak")
        class GetTasks extends AsyncTask<Void, Void, List<Task>> {

            @Override
            protected List<Task> doInBackground(Void... voids) {
                return DatabaseClient
                        .getInstance(requireActivity().getApplicationContext())
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                TasksAdapter adapter = new TasksAdapter(requireActivity().getApplicationContext(), tasks);
                recyclerView.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
}
