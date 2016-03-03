package bitspilani.dvm.apogee2016;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by jai on 20/10/15.
 */

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "apogee2016.db";
    public static final String TABLE_EVENTS = "events";
    public static final String TABLE_RESULTS = "results";
    public static final String TABLE_SPONSORS = "sponsors";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_START = "start";
    public static final String COLUMN_END = "end";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_ISALLDAY = "isallday";
    public static final String COLUMN_ISPROFSHOW = "isprofshow";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_UPDATEDAT = "updatedAt";
    public static final String COLUMN_RESULT = "result";

    public static final String COLUMN_HEADING = "heading";
    public static final String COLUMN_PR = "pr";
    public static final String COLUMN_URL = "url";

    String CREATE_EVENTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_EVENTS + "("
            + COLUMN_ID + " TEXT, " + COLUMN_NAME
            + " TEXT, " + COLUMN_LOCATION + " TEXT, " + COLUMN_START
            + " DATETIME, " + COLUMN_END + " DATETIME, " + COLUMN_DESC
            + " TEXT, " + COLUMN_ISALLDAY + " INTEGER DEFAULT 0, " + COLUMN_ISPROFSHOW + " INTEGER, " + COLUMN_CATEGORY + " TEXT)";

    String CREATE_RESULTS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_RESULTS + "("
            + COLUMN_ID + " TEXT, " + COLUMN_NAME
            + " TEXT, " + COLUMN_UPDATEDAT
            + " DATETIME, " + COLUMN_RESULT + " TEXT)";

    String CREATE_SPONSORS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_SPONSORS + "("
            + COLUMN_ID + " TEXT, " + COLUMN_HEADING + " TEXT, " + COLUMN_PR + " INTEGER, "
            + COLUMN_URL + " TEXT)";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_EVENTS_TABLE);
        db.execSQL(CREATE_RESULTS_TABLE);
        db.execSQL(CREATE_SPONSORS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPONSORS);
        onCreate(db);

    }

    private long getmillis(Date date) {
        long millis = 0l;
        try {
            millis = date.getTime();
        } catch (NullPointerException e) {
            Log.d("Null pointer", "null data on the DB");
        }

        return millis;
    }

    public void addEvent(String id, String name, String location, Date start, Date end, String description, boolean isAllDay, int isProfShow, String category) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_START, getmillis(start) - 19800000l);
        values.put(COLUMN_END, getmillis(end) - 19800000l);
        values.put(COLUMN_DESC, description);
        values.put(COLUMN_ISALLDAY, isAllDay);
        values.put(COLUMN_ISPROFSHOW, isProfShow);
        values.put(COLUMN_CATEGORY, category);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_EVENTS, null, values);
        Log.d("data received",name);
        db.close();
    }

    public void addResult(String id, String name, Date updatedAt, String result) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_UPDATEDAT, getmillis(updatedAt) - 19800000l);
        values.put(COLUMN_RESULT, result);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_RESULTS, null, values);
        db.close();
    }

    public void addSponsor(String id, String heading, int pr, String url) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_HEADING, heading);
        values.put(COLUMN_PR, pr);
        values.put(COLUMN_URL, url);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_SPONSORS, null, values);
        db.close();
    }

    public void updateEvent(String id, String name, String location, Date start, Date end, String description, boolean isAllDay, int isProfShow, String category) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_START, getmillis(start) - 19800000l);
        values.put(COLUMN_END, getmillis(end) - 19800000l);
        values.put(COLUMN_DESC, description);
        values.put(COLUMN_ISALLDAY, isAllDay);
        values.put(COLUMN_ISPROFSHOW, isProfShow);
        values.put(COLUMN_CATEGORY, category);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_EVENTS, values, COLUMN_ID + "=?", new String[]{id});
        Log.d("data received",name);
        db.close();
    }

    public void updateResult(String id, String name, Date updatedAt, String result) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_UPDATEDAT, getmillis(updatedAt) - 19800000l);
        values.put(COLUMN_RESULT, result);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_RESULTS, values, COLUMN_ID + "=?", new String[]{id});
        db.close();
    }

    public void updateSponsor(String id, String heading, int pr, String url) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_HEADING, heading);
        values.put(COLUMN_PR, pr);
        values.put(COLUMN_URL, url);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_SPONSORS, values, COLUMN_ID + "=?", new String[]{id});
        db.close();
    }

    public void addEvent(EventModel event) {
        if (checkIfExists(TABLE_EVENTS, event.getID())) {
            updateEvent(event.getID(), event.getName(), event.getLocation(), event.getStart(), event.getEnd(), event.getDesc(), event.isKernel(), event.isProfShow(), event.getCategory());
        } else {
            addEvent(event.getID(), event.getName(), event.getLocation(), event.getStart(), event.getEnd(), event.getDesc(), event.isKernel(), event.isProfShow(), event.getCategory());
        }
    }

    public void addResult(ResultModel result) {
        if (checkIfExists(TABLE_RESULTS, result.getID())) {
            updateResult(result.getID(), result.getName(), result.getUpdatedAt(), result.getResult());
        } else {
            addResult(result.getID(), result.getName(), result.getUpdatedAt(), result.getResult());
        }
    }

//    public void addSponsor(SponsorsModel sponsor) {
//        if (checkIfExists(TABLE_SPONSORS, sponsor.getID())) {
//            updateSponsor(sponsor.getID(), sponsor.getHeading(), sponsor.getPR(), sponsor.getURL());
//        } else {
//            addSponsor(sponsor.getID(), sponsor.getHeading(), sponsor.getPR(), sponsor.getURL());
//        }
//    }

    public boolean checkIfExists(String table_name, String id) {

        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =
                db.query(table_name, new String[]{COLUMN_ID}, COLUMN_ID + " =?", new String[]{id}, null, null, null);
        if (cursor.getCount() != 0)
            result = true;
        cursor.close();
        db.close();
        return result;
    }

    //Returns ArrayList of Events for a date

    public ArrayList<EventModel> getEventsList(long start, long end) {

        ArrayList<EventModel> eventList = new ArrayList<EventModel>();
        SQLiteDatabase db = this.getWritableDatabase();

//        String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE strftime('%d', '" + COLUMN_START + "') = " + d;
//                + " OR strftime('%d', '" + COLUMN_END + "') = " + d;
        String query2 = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_START + " BETWEEN " + start + " AND " + end;
//        String query3="SELECT * FROM " + TABLE_EVENTS;

        Cursor cursor = db.rawQuery(query2, null);

        if (cursor.moveToFirst()) {
            do {
                EventModel event = new EventModel();
                event.setID(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                event.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                event.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
                event.setStart(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_START))));
                event.setEnd(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_END))));
                event.setProfShow(cursor.getInt(cursor.getColumnIndex(COLUMN_ISPROFSHOW)));
                event.setDesc(cursor.getString(cursor.getColumnIndex(COLUMN_DESC)));
                eventList.add(event);


            } while (cursor.moveToNext());

        } else {
            Log.d("Neo", "Jai Failed1");
        }
        cursor.close();
        Collections.sort(eventList);
        db.close();

        return eventList;

    }

    //Returns ArrayList of Events Now

    public ArrayList<EventModel> getEventsNowList() {

        ArrayList<EventModel> eventsNowList = new ArrayList<EventModel>();
        SQLiteDatabase db = this.getWritableDatabase();
        long now = System.currentTimeMillis() - 1800000l;
        long next4 = now + 16200000l;

//        String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_ISALLDAY + " = 0";
        String query2 = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_START + " BETWEEN " + now + " AND " + next4;

        Cursor cursor = db.rawQuery(query2, null);

        if (cursor.moveToFirst()) {
            do {
                long eventStart = cursor.getLong(cursor.getColumnIndex(COLUMN_START));
                long eventEnd = cursor.getLong(cursor.getColumnIndex(COLUMN_END));
                long diff = eventStart - System.currentTimeMillis();


                if ((now <= eventEnd && diff <= 0) ||
                        (diff > 0 && diff < 14400000)) {

                    EventModel eventNow = new EventModel();
                    eventNow.setID(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                    eventNow.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                    eventNow.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
                    eventNow.setStart(new Date(eventStart));
                    eventNow.setEnd(new Date(eventEnd));
                    eventNow.setDesc(cursor.getString(cursor.getColumnIndex(COLUMN_DESC)));

                    if (diff > 0) {
                        long diffMinutes = diff / (60 * 1000) % 60;
                        long diffHours = diff / (60 * 60 * 1000) % 24;

                        Log.d("diff", " " + diffMinutes + " " + diffHours);
                        if (diffHours == 0) {
                            eventNow.setTimeLeft("IN " + diffMinutes + " MIN");
                        } else {
                            if (diffMinutes >= 30)
                                diffHours ++;

                            if(diffHours == 1)
                                eventNow.setTimeLeft("IN 1 HOUR");

                            else
                                eventNow.setTimeLeft("IN " + diffHours + " HOURS");
                        }
                    } else
                        eventNow.setTimeLeft("NOW");

                    eventsNowList.add(eventNow);
                }

            } while (cursor.moveToNext());
        }

        Collections.sort(eventsNowList);
        db.close();

        return eventsNowList;
    }

    //Returns Results Arraylist in sorted form

    public ArrayList<ResultModel> getResultList() {

        ArrayList<ResultModel> resultList = new ArrayList<ResultModel>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_RESULTS;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ResultModel result = new ResultModel();
                result.setID(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                result.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                result.setUpdatedAt(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATEDAT))));
                result.setResult(cursor.getString(cursor.getColumnIndex(COLUMN_RESULT)));

                resultList.add(result);
            } while (cursor.moveToNext());
        }

        Collections.sort(resultList);

        db.close();
        return resultList;

    }

    //Get Prof Shows
    public ArrayList<EventModel> getProfShows() {

        ArrayList<EventModel> profShows = new ArrayList<EventModel>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_ISPROFSHOW + " =1 ";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                EventModel event = new EventModel();
                event.setID(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                event.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                event.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
                event.setStart(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_START))));
                event.setEnd(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_END))));
                event.setProfShow(cursor.getInt(cursor.getColumnIndex(COLUMN_ISPROFSHOW)));
                event.setDesc(cursor.getString(cursor.getColumnIndex(COLUMN_DESC)));
                profShows.add(event);


            } while (cursor.moveToNext());

        } else {
            Log.d("Neo", "Jai Failed");
        }
        cursor.close();
        db.close();
        Collections.sort(profShows);
        return profShows;
    }

    //Jai

    public ArrayList<EventModel> getEventsList(String category) {

        ArrayList<EventModel> eventList = new ArrayList<EventModel>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_CATEGORY + " =?";

        Cursor cursor = db.rawQuery(query, new String[]{category});

        if (cursor.moveToFirst()) {
            do {
                EventModel event = new EventModel();
                event.setID(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                event.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                event.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
                event.setStart(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_START))));
                event.setEnd(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_END))));
                event.setAllDay(cursor.getInt(cursor.getColumnIndex(COLUMN_ISALLDAY)) == 1);
                event.setProfShow(cursor.getInt(cursor.getColumnIndex(COLUMN_ISPROFSHOW)));
                event.setDesc(cursor.getString(cursor.getColumnIndex(COLUMN_DESC)));
                event.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));
                eventList.add(event);


            } while (cursor.moveToNext());

        } else {
            Log.d("Neo", "Jai Failed");
        }
        cursor.close();
        db.close();
        Collections.sort(eventList);
        return eventList;
    }

    public ArrayList<EventModel> getKernelEvents() {

        ArrayList<EventModel> kernelEvents = new ArrayList<EventModel>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_ISALLDAY + " =1 ";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                EventModel event = new EventModel();
                event.setID(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                event.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                event.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
                event.setStart(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_START))));
                event.setEnd(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_END))));
                event.setAllDay(cursor.getInt(cursor.getColumnIndex(COLUMN_ISALLDAY)) == 1);
                event.setProfShow(cursor.getInt(cursor.getColumnIndex(COLUMN_ISPROFSHOW)));
                event.setDesc(cursor.getString(cursor.getColumnIndex(COLUMN_DESC)));
                event.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));
                kernelEvents.add(event);


            } while (cursor.moveToNext());

        } else {
            Log.d("Neo", "Jai Failed");
        }
        cursor.close();
        db.close();
        Collections.sort(kernelEvents);
        return kernelEvents;
    }

    public ArrayList<EventModel> getSearchResults(String s) {

        ArrayList<EventModel> searchResults = new ArrayList<EventModel>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_NAME + " LIKE '%" + s +"%'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                EventModel event = new EventModel();
                event.setID(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                event.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                event.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
                event.setStart(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_START))));
                event.setEnd(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_END))));
                event.setAllDay(cursor.getInt(cursor.getColumnIndex(COLUMN_ISALLDAY)) == 1);
                event.setProfShow(cursor.getInt(cursor.getColumnIndex(COLUMN_ISPROFSHOW)));
                event.setDesc(cursor.getString(cursor.getColumnIndex(COLUMN_DESC)));
                event.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));
                searchResults.add(event);


            } while (cursor.moveToNext());

        } else {
            Log.d("Neo", "Jai Failed");
        }
        cursor.close();
        db.close();
        Collections.sort(searchResults);
        return searchResults;
    }

    // Get Sponsors Images Links

//    public ArrayList<SponsorsModel> getSponsors() {
//
//        ArrayList<SponsorsModel> sponsorList = new ArrayList<SponsorsModel>();
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        String query = "SELECT * FROM " + TABLE_SPONSORS;
//
//        Cursor cursor = db.rawQuery(query, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                SponsorsModel sponsor = new SponsorsModel();
//                sponsor.setID(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
//                sponsor.setHeading(cursor.getString(cursor.getColumnIndex(COLUMN_HEADING)));
//                sponsor.setPR(cursor.getInt(cursor.getColumnIndex(COLUMN_PR)));
//                sponsor.setURL(cursor.getString(cursor.getColumnIndex(COLUMN_URL)));
//
//                sponsorList.add(sponsor);
//            } while (cursor.moveToNext());
//        }
//
//        Collections.sort(sponsorList);
//
//        db.close();
//        return sponsorList;
//    }
}