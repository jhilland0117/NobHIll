package net.effect117.mapping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * Created by JHilland on 8/26/2015.
 */
public class StartChallenge extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_subject_list);
        setContentView(R.layout.start_challenge);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate menu items for use in action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
