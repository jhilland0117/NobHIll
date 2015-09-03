package net.effect117.mapping;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by JHilland on 8/31/2015.
 */



public class TXTAdapter extends ArrayAdapter<AssetsReader> {
    Context ctx;
    ArrayList<AssetsReader> currentFilter = new ArrayList<AssetsReader>();
    private HashMap<String, ArrayList<AssetsReader>> assetsMap = new HashMap<String, ArrayList<AssetsReader>>();
    private boolean sortTitle = true;
    private String currentSearch;


    public TXTAdapter(Context context, int resource) {
        super(context, resource);

        this.ctx = context;

        ArrayList<String> housing = new ArrayList<String>();
        housing.add("Lobo Village");
        housing.add("Coronado Hall Dormitory");
        housing.add("Hokona Hall (Housing)");
        housing.add("Casas del Rio");
        housing.add("Santa Ana Hall Dormitory");
        housing.add("Santa Clara Hall Dormitory");
        housing.add("Alvarado Hall Dormitory");
        housing.add("Redondo Village Student Residences");
        housing.add("Laguna Hall Dormitory");
        housing.add("Devargas Hall Dormitory");

        assetsMap.put("buildings", new ArrayList<AssetsReader>());
        assetsMap.put("parking", new ArrayList<AssetsReader>());
        assetsMap.put("computers", new ArrayList<AssetsReader>());
        assetsMap.put("dining", new ArrayList<AssetsReader>());
        assetsMap.put("libraries", new ArrayList<AssetsReader>());
        assetsMap.put("housing", new ArrayList<AssetsReader>());

        // load the data
        loadArrayFromFile("buildings.txt", assetsMap.get("buildings"));
        loadArrayFromFile("parking.txt", assetsMap.get("parking"));
        loadArrayFromFile("computers.txt", assetsMap.get("computers"));
        loadArrayFromFile("dining.txt", assetsMap.get("dining"));
        loadArrayFromFile("libraries.txt", assetsMap.get("libraries"));

        for(AssetsReader building : assetsMap.get("buildings"))
        {
            if(housing.contains(building.getTitle()))
                assetsMap.get("housing").add(building);
        }

        currentFilter = assetsMap.get("buildings");


    }

    @Override
    public View getView (final int pos, View convertView, final ViewGroup parent) {

        TextView mView = (TextView) convertView;

        if(null == mView) {
            mView = new TextView(parent.getContext());
            mView.setTextSize(16);
        }

        if(sortTitle)
        {
            if (getItem(pos).getBuildingAbbr() == null || getItem(pos).getBuildingAbbr().isEmpty())
                mView.setText(getItem(pos).getTitle());
            else
                mView.setText(getItem(pos).getTitle() + " - " + getItem(pos).getBuildingAbbr());
        }
        else
        {
            if(getItem(pos).getBuildingAbbr() != null)
                mView.setText(getItem(pos).getBuildingAbbr() + " - " + getItem(pos).getTitle());
        }

        return mView;
    }



    public void loadArrayFromFile(String file, ArrayList<AssetsReader> assetsList) {


        try {
            // get stream and buffer reader for file
            InputStream IN = ctx.getAssets().open(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(IN));
            String line;

            // read each line
            while ((line = reader.readLine()) != null) {

                // split to separate the attributes in text file
                String[] RowData = line.split("%");

                AssetsReader current = new AssetsReader();

                // create objects for rowdata
                if(file.equals("buildings.txt"))
                {
                    current.setTitle(RowData[0]); // grab the title from file
                    current.setBuildingAbbr(RowData[1]); // grab the building abbr
                    current.setLatitude(RowData[2]); // grab lat
                    current.setLongitude(RowData[3]); // grab long
                }
                else
                {
                    current.setTitle(RowData[0]); // grab the title from file
                    current.setLatitude(RowData[1]); // grab lat
                    current.setLongitude(RowData[2]); // grab long
                    current.setBuildingAbbr("");
                }
                // add object to array list
                assetsList.add(current);
                this.add(current);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sortTitle() {
        sortTitle = true;

        Collections.sort(currentFilter, new Comparator<AssetsReader>() {

            @Override
            public int compare(AssetsReader lhs, AssetsReader rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }

        });

        if (currentSearch != null)
            search(currentSearch);
        else
        {
            clear();
            addAll(currentFilter);
            notifyDataSetChanged();
        }
    }

    public void sortAbbr() {
        sortTitle = false;

        ArrayList<AssetsReader> tempList = new ArrayList<AssetsReader>();
        for(AssetsReader building : currentFilter){
            if (building.getBuildingAbbr() != null && !building.getBuildingAbbr().isEmpty())
            {
                tempList.add(building);
            }
        }

        Collections.sort(tempList, new Comparator<AssetsReader>() {

            @Override
            public int compare(AssetsReader lhs, AssetsReader rhs) {
                return lhs.getBuildingAbbr().compareTo(rhs.getBuildingAbbr());
            }

        });

        clear();
        addAll(tempList);
        notifyDataSetChanged();

        if (currentSearch != null)
            search(currentSearch);
    }

    public void search(CharSequence s){
        currentSearch = s.toString();
        ArrayList<AssetsReader> tempList = new ArrayList<AssetsReader>();
        for (AssetsReader building : currentFilter){
            String tempTitle = building.getTitle().toLowerCase() + building.getBuildingAbbr().toLowerCase();
            s.toString().toLowerCase();
            if (tempTitle.contains(s))
                if(sortTitle)
                    tempList.add(building);
                else if(!sortTitle && building.getBuildingAbbr() != null && !building.getBuildingAbbr().isEmpty())
                    tempList.add(building);
        }

        clear();
        addAll(tempList);
        notifyDataSetChanged();
    }

    public void setFilter(String s)
    {
        currentFilter = assetsMap.get(s);
        if(currentSearch != null)
        {
            search(currentSearch);
        }
        else
        {
            clear();
            addAll(currentFilter);
            notifyDataSetChanged();

            if (!sortTitle)
                sortAbbr();
        }
    }
}

