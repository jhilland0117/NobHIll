package net.effect117.mapping;



import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MenuMain extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main_activity);

        //Runtime runtime = Runtime.getRuntime();
        //long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        //System.out.println("Used Memory before:" + usedMemoryBefore);


        ActionBar ab = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FFFFFF"));
        ab.setBackgroundDrawable(colorDrawable);
        ab.setTitle(Html.fromHtml("<font color=\"#990000\">Main Menu<center>"));
        //long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        //System.out.println("Memory increased:" + (usedMemoryAfter-usedMemoryBefore));

    }

    public void openmap(View view) {
        Animation animMap = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        view.startAnimation(animMap);

        Intent i = new Intent(this , MapsActivity.class);
        startActivity(i);
    }

    public void openPlaces(View view) {
        Animation animMap = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        view.startAnimation(animMap);

        Intent i = new Intent(this , FilterMapsActivity.class);
        startActivity(i);
    }

    public void goToWebSite(View view) {
        Uri uri = Uri.parse("http://route66central.com/"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

        // another site to use http://www.visitalbuquerque.org/restaurants/?sfilter=ALL&efilter=ALL&MAXSHOW=10&ADDMEETINGFACILITIES=false&USERANKSORT=0&USEDTNSORT=1&ADDUDFS=false&SHOWMAP=true&TOTALRESULTS=348&ADDAMENITIES=false&SORTPRESET=default&USEDTN=1&STARTROW=1&PRICERANGE=0&LATITUDE=0&ITINERARYTHEMEID=0&LISTIDS=0&DISTANCE=0&SUBCATID=0&CATID=19&LONGITUDE=0&REGIONID=0
    }

    public void filteringLocations(View view)
    {
        // bring up new activity with filters???
        Animation animMap = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        view.startAnimation(animMap);

        Intent i = new Intent(this , FilterChoice.class);
        startActivity(i);
    }


/*
    public void openabout(View view) {
        Animation animAbout = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        view.startAnimation(animAbout);
        //Intent i = new Intent(this , HelpPage.class);
        //startActivity(i);
    }

    public void openevents(View view) {
        Animation animEvents = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        view.startAnimation(animEvents);
        goToUrl ( "http://unmevents.unm.edu");
    }

    public void openschedule(View view) {
        Animation animSchedule = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        view.startAnimation(animSchedule);
        goToUrl ( "https://schedule.unm.edu/");
    }

    public void openMyUNM (View view) {
        Animation animMyUNM = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        view.startAnimation(animMyUNM);
        goToUrl ( "https://my.unm.edu/cp/home/displaylogin");
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }*/
}
