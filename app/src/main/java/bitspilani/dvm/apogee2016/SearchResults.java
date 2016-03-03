package bitspilani.dvm.apogee2016;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchResults extends Fragment {

    String search_key;
    String[] name;
    ListView category_list;
    Database db;
    String[] location;
    String[] time;
    String[] desc;
    ArrayList<EventModel> searchResults;
    Date[] reminderStart;
    Date[] reminderEnd;
    String [] day;

    public SearchResults()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_searchresults, container, false);

        search_key=getArguments().getString("key");
        db=new Database(getActivity());
        category_list = (ListView) view.findViewById(R.id.category_list);

        final String msg = "Success";

        category_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDescription(position);
            }
        });

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                searchResults= db.getSearchResults(search_key);
                return msg;
            }

            @Override
            protected void onPostExecute(String s) {
//                Log.d("Neo", "Neo");
                DateFormat df2 = new SimpleDateFormat("hh:mm a");
                DateFormat df = new SimpleDateFormat("dd MMM");
                name=new String[searchResults.size()];
                location=new String[searchResults.size()];
                time=new String[searchResults.size()];
                day=new String[searchResults.size()];
                desc=new String[searchResults.size()];
                reminderStart=new Date[searchResults.size()];
                reminderEnd=new Date[searchResults.size()];
                for (int i=0;i<searchResults.size();i++)
                {
                    name[i]=searchResults.get(i).getName();
                    location[i]=searchResults.get(i).getLocation();
                    reminderStart[i]=searchResults.get(i).getStart();
                    reminderEnd[i]=searchResults.get(i).getEnd();
                    time[i]=df2.format(reminderStart[i]);
                    day[i]=df.format(reminderStart[i]);
                    desc[i]=searchResults.get(i).getDesc();
                }


                if (searchResults.size() == 0) {
                    Log.d("Neo", "No Data Received1");

                } else {

                    ListAdapter custom = new CustomList(getActivity(), name, location,time);
                    category_list.setAdapter(custom);
                }
            }
        }.execute(null, null, null);


        return view;
    }


    public void showDescription(int position)
    {
        Intent i=new Intent(getActivity(),EventDetail.class);
        i.putExtra("name",name[position]);
        i.putExtra("location",location[position]);
        i.putExtra("time",time[position]);
        i.putExtra("desc",desc[position]);
        i.putExtra("date",day[position]);
        i.putExtra("reminderStart",reminderStart[position].getTime());
        i.putExtra("reminderEnd",reminderEnd[position].getTime());
        startActivity(i);
    }
}
