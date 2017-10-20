package fr.codevallee.formation.android_projet_sante;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor prefsEditor = prefs.edit();
        Set<String> jobsSet = prefs.getStringSet("jobs", new HashSet<String>());

        ListView jobsListView = (ListView) findViewById(R.id.jobs_list);
        final ArrayList<String> jobsList = new ArrayList<String>(jobsSet);
        if (jobsSet != null) {
            ArrayAdapter<String> jobsAdapter = new ArrayAdapter<String>(SettingsActivity.this, android.R.layout.simple_list_item_1, jobsList);
            jobsListView.setAdapter(jobsAdapter);
        }

        Button addJobButton = (Button) findViewById(R.id.settings_add_job);
        addJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = SettingsActivity.this.getLayoutInflater();
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle(R.string.add_job);

                final EditText input = new EditText(SettingsActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Nothing special :-P
                    }
                });
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String job = input.getText().toString();
                        Log.d("INFO Job", job);
                        if (!job.isEmpty()) {
                            ArrayList<String> newJobsList = (ArrayList<String>) jobsList.clone();
                            newJobsList.add(job);
                            prefsEditor.putStringSet("jobs", new HashSet<String>(newJobsList));
                            prefsEditor.commit();

                            Intent refresh = new Intent(SettingsActivity.this, SettingsActivity.class);
                            startActivity(refresh);
                            SettingsActivity.this.finish();
                        }
                    }
                });
                AlertDialog dialog = builder.create();

                dialog.show();
            }
        });
    }
}
