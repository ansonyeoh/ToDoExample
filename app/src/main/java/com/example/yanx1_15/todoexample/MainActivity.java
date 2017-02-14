package com.example.yanx1_15.todoexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Backendless.initApp(this, "64FA7FFF-9E99-470A-FFD8-2D3084A48200", "E827E68A-40E2-E15C-FF67-1CF476665800", "v1");

        Backendless.Persistence.of(Tasks.class).find(
                new AsyncCallback<BackendlessCollection<Tasks>>() {

                    @Override
                    public void handleResponse( BackendlessCollection<Tasks> tc) {

                        List<Tasks> tasks = tc.getData();
                        Log.d("ToDo", "Retrieved " + tasks.size() + " tasks");

                        LinearLayout taskList = (LinearLayout) findViewById(R.id.taskList);

                        //for each task in the list of tasks
                        for(Tasks task: tasks) {

                            //retrieve the task text from the task object and log it
                            String taskText = task.getTask();
                            Log.d("ToDo", taskText);

                            //create a new textview and set the task text
                            TextView textView = new TextView(getApplicationContext());
                            textView.setText(taskText);

                            //add the textview to the linear layout
                            taskList.addView(textView);

                        }
                    }

                    @Override
                    public void handleFault(BackendlessFault e) {
                        Log.d("ToDo", "Error: " + e.getMessage());
                    }
                }
        );
    }

    public void onAddTaskButtonClicked(View Button)
    {
        Log.d("ToDo", "Button clicked");

        TextView taskView = (TextView) findViewById(R.id.taskText);
        String newText = taskView.getText().toString();

        Tasks newTask = new Tasks();
        newTask.setTask(newText);

        Backendless.Persistence.save(newTask, new AsyncCallback<Tasks>() {

            @Override
            public void handleResponse(Tasks tasks) {
                Log.d("ToDo", "Saved task");
            }

            @Override
            public void handleFault(BackendlessFault e) {
                Log.d("ToDo", "Error: " + e.getMessage());
            }

        });



    }
}

