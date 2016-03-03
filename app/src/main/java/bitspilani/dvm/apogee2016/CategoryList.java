package bitspilani.dvm.apogee2016;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CategoryList extends Activity implements SHARED_CONSTANTS {

    ArrayList<EventModel> eventList = new ArrayList<EventModel>();
    Database db;
    String[] name;
    ListView category_list;
    String[] location;
    String[] time;
    String[] desc;
    Date[] reminderStart;
    Date[] reminderEnd;
    String [] day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        db=new Database(this);
        category_list = (ListView) findViewById(R.id.category_list);
        final Bundle details=getIntent().getExtras();
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
                eventList= db.getEventsList(details.getString("category"));
                return msg;
            }

            @Override
            protected void onPostExecute(String s) {
//                Log.d("Neo", "Neo");
                DateFormat df2 = new SimpleDateFormat("hh:mm a");
                DateFormat df = new SimpleDateFormat("dd MMM");
                name=new String[eventList.size()];
                location=new String[eventList.size()];
                time=new String[eventList.size()];
                day=new String[eventList.size()];
                desc=new String[eventList.size()];
                reminderStart=new Date[eventList.size()];
                reminderEnd=new Date[eventList.size()];
                for (int i=0;i<eventList.size();i++)
                {
                    name[i]=eventList.get(i).getName();
                    location[i]=eventList.get(i).getLocation();
                    reminderStart[i]=eventList.get(i).getStart();
                    reminderEnd[i]=eventList.get(i).getEnd();
                    time[i]=df2.format(reminderStart[i]);
                    day[i]=df.format(reminderStart[i]);
                    desc[i]=eventList.get(i).getDesc();
                }


                if (eventList.size() == 0) {
                    Log.d("Neo", "No Data Received1");

                } else {

                    ListAdapter custom = new CustomList(getBaseContext(), name, location,time);
                    category_list.setAdapter(custom);
                }
            }
        }.execute(null, null, null);

        try{
            android.app.ActionBar a=getActionBar();
            a.setTitle(EVENT_CATEGORIES[details.getInt("position")]);
        }
        catch (NullPointerException e)
        {
            Log.d("Nullpointer","Occurred");
        }

    }

    public void showDescription(int position)
    {
        Intent i=new Intent(this,EventDetail.class);
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
