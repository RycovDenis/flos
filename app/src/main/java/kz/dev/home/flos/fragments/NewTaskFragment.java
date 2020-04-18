package kz.dev.home.flos.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import kz.dev.home.flos.R;
import kz.dev.home.flos.datamodels.Task;
import kz.dev.home.flos.helper.DatabaseClient;

public class NewTaskFragment extends Fragment {

        private EditText editTextTask, editTextDesc, editTextFinishBy;
        private View rootView;

    public NewTaskFragment(){

        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            rootView = inflater.inflate(R.layout.fragment_new_task, container,
                    false);
            super.onCreate(savedInstanceState);

            editTextTask = rootView.findViewById(R.id.editTextTask);
            editTextDesc = rootView.findViewById(R.id.editTextDesc);
            editTextFinishBy = rootView.findViewById(R.id.editTextFinishBy);

            rootView.findViewById(R.id.button_tasksave).setOnClickListener(view -> saveTask());
            return rootView;
        }

        private void saveTask() {
            final String sTask = editTextTask.getText().toString().trim();
            final String sDesc = editTextDesc.getText().toString().trim();
            final String sFinishBy = editTextFinishBy.getText().toString().trim();

            if (sTask.isEmpty()) {
                editTextTask.setError(getString(R.string.task_error_empty));
                editTextTask.requestFocus();
                return;
            }

            if (sDesc.isEmpty()) {
                editTextDesc.setError("Desc required");
                editTextDesc.requestFocus();
                return;
            }

            if (sFinishBy.isEmpty()) {
                editTextFinishBy.setError("Finish by required");
                editTextFinishBy.requestFocus();
                return;
            }

            @SuppressLint("StaticFieldLeak")
            class SaveTask extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {

                    //creating a task
                    Task task = new Task();
                    task.setTask(sTask);
                    task.setDesc(sDesc);
                    task.setFinishBy(sFinishBy);
                    task.setFinished(false);

                    //adding to database
                    DatabaseClient.getInstance(requireActivity().getApplicationContext()).getAppDatabase()
                            .taskDao()
                            .insert(task);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Fragment newFragment = new TasksFragment(); //YourFragment заменить на нужный
                    assert getFragmentManager() != null;
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, newFragment); //fragment_container заменить на Ваш id контейнера
                    transaction.commit();
//                    new MainActivity().displayView(R.id.button_tasksave);
                    Toast.makeText(requireActivity().getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                }
            }

            SaveTask st = new SaveTask();
            st.execute();
        }

    }
