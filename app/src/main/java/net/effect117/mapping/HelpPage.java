package net.effect117.mapping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by JHilland on 9/1/2015.
 */


public class HelpPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activity);
    }

    public void openlists(View view) {
        Intent i = new Intent(this , SubActivity.class);
        startActivity(i);
    }

    public void openlists2(View view) {
        Intent i2 = new Intent(this , SubActivity.class);
        startActivity(i2);
        Toast toast= Toast.makeText(getApplicationContext(),
                "Click here->", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
        toast.show();
    }

    public void helptoast(View view) {
        Toast.makeText(this, "You are here", Toast.LENGTH_SHORT).show();
    }
}