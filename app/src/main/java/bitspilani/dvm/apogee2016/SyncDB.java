package bitspilani.dvm.apogee2016;

import android.content.Context;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by jai on 21/10/15.
 */
public class SyncDB {

    public static void refreshEvent(Context context) {

        final Database db = new Database(context);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("EventData");
        query.setLimit(1000);

        query.findInBackground(new FindCallback<ParseObject>() {

            @SuppressWarnings("unchecked")
            @Override
            public void done(List<ParseObject> eventList, ParseException e) {
                int c=0;
                if (e == null) {
                    for (ParseObject parseEvent : eventList) {
                        int check=0;

                 EventModel event = new EventModel(parseEvent.getObjectId(), parseEvent.getString("name").trim(),
                                    parseEvent.getString("location"), parseEvent.getDate("start"), parseEvent.getDate("end"), parseEvent.getString("description"), parseEvent.getBoolean("isKernel"),check,parseEvent.getString("category").trim());

                        db.addEvent(event);

                        c++;
                        Log.d("Event name" +c,parseEvent.getString("name"));
                        Log.d("Date",parseEvent.getDate("start").toString());
                    }

                }
                else
                    Log.d("Jay", "Jay");
            }
        });
    }

    public static void refreshResult(Context context) {

        final Database db = new Database(context);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Results");

        query.setLimit(1000);
        query.findInBackground(new FindCallback<ParseObject>() {

            @SuppressWarnings("unchecked")
            @Override
            public void done(List<ParseObject> resultList, ParseException e) {

                if (e == null) {
                    for (ParseObject parseResult : resultList) {

                        ResultModel result = new ResultModel(parseResult.getObjectId(), parseResult.getString("eventName"),
                                parseResult.getUpdatedAt(), parseResult.getString("result"));

                        db.addResult(result);
                    }

                }
            }
        });
    }


}
