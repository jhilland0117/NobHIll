package net.effect117.mapping;

/**
 * Created by JHilland on 8/31/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import java.lang.reflect.Field;


public class SubActivity extends Activity{
    private TXTAdapter adapter;
    private ListView listView;
    private EditText inputSearch;

	/*public boolean showPopup(MenuItem item) {
	    PopupMenu popup = new PopupMenu(this, findViewById(R.id.dropdown));
	    MenuInflater inflater = popup.getMenuInflater();
	    inflater.inflate(R.menu.items, popup.getMenu());
	    popup.show();
		return false;
	}*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        TextView textView = (TextView) findViewById(R.id.inputSearch);
        switch(item.getItemId())
        {
            case R.id.dropdown:
                return true;
            //case R.id.help:
             //   Intent i = new Intent(this, HelpPage.class);
               // startActivity(i);
               // return true;
            case R.id.buildings:
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_building_icon, 0, 0, 0);
                adapter.setFilter("buildings");
                break;
            case R.id.parking:
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_parking_icon, 0, 0, 0);
                adapter.setFilter("parking");
                break;
            case R.id.computer_labs:
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_computer_icon, 0, 0, 0);
                adapter.setFilter("computers");
                break;
            case R.id.food:
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_food_icon, 0, 0, 0);
                adapter.setFilter("dining");
                break;
            case R.id.libraries:
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_book_icon, 0, 0, 0);
                adapter.setFilter("libraries");
                break;
            case R.id.dorms:
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_dorm_icon, 0, 0, 0);
                adapter.setFilter("housing");
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.sub_activity);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.radioName:
                        if (!adapter.equals(null))
                            adapter.sortTitle();
                        break;
                    case R.id.radioAbbr:
                        if (!adapter.equals(null))
                            adapter.sortAbbr();
                        break;
                }
            }
        });

        // get id of the listview we are using (only one)
        listView = (ListView)findViewById(R.id.listView1);

        //get search
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        inputSearch.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                adapter.search(s);

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });
        // -1 value is dummy, constructor of TXTAdapter required it
        adapter = new TXTAdapter(this, -1);
        listView.setAdapter(adapter);

        // on item click what do we want to do??
	    /* in theory we want to send the lat and long coordinates to the
	     * map methods to display the location on the map.
	     */
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(SubActivity.this, MapsActivity.class);
                intent.putExtra("longitude", adapter.getItem(arg2).getLatitude());
                intent.putExtra("latitude", adapter.getItem(arg2).getLongitude());
                intent.putExtra("title", adapter.getItem(arg2).getTitle());
                intent.putExtra("abbr", adapter.getItem(arg2).getBuildingAbbr());

                if(adapter.getItem(arg2).getTitle().equals("Electrical And Computer Engineering"))
                {
                    intent.putExtra("description", "(Note: The new Math MaLL is located \n in the "
                            + "basement of Centennial Library, room L185)");
                }

                if(adapter.getItem(arg2).getTitle().equals("Centennial Library"))
                {
                    intent.putExtra("description", "(Note: The new Math MaLL is located \n in the "
                            + "basement of Centennial Library, room L185) \n *Touch here to view larger images*");
                }

                startActivity(intent);
            }
        });
    }

}