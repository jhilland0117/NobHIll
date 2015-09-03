package net.effect117.mapping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

/**
 * Created by JHilland on 8/25/2015.
 */
public class Splash extends Activity {


    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_subject_list);
        setContentView(R.layout.splash);

        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    int logoTimer = 0;
                    while (logoTimer < 4000) {
                        sleep(100);
                        logoTimer = logoTimer + 100;
                    };
                    startActivity(new Intent("net.effect117.CLEARSCREEN"));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };

        logoTimer.start();
    }*/


    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1950;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        //Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this,
                //ErrorLoadingScreen.class));
        setContentView(R.layout.splash);
       // Toast.makeText(this, "Welcome to the CLSA app!",
              //  Toast.LENGTH_SHORT).show();

        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used Memory before:" + usedMemoryBefore);
        // working code here


        AnimationUtils.loadAnimation(this, R.anim.anim_translate); //load animation method

        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory increased:" + (usedMemoryAfter-usedMemoryBefore));

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){

                                      @Override
                                      public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                                          Intent mainIntent = new Intent(Splash.this, MenuMain.class);
                                          Splash.this.startActivity(mainIntent);
                                          Splash.this.finish();
                                      }
                                  },
                SPLASH_DISPLAY_LENGTH);
    }


}
