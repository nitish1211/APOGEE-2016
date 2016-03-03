package bitspilani.dvm.apogee2016;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Patterns;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.regex.Pattern;

/**
 * Created by HP on 2/13/2016.
 */
public class Apogee extends Application {

    SharedPreferences prefs = null;
    String emailID=" ";

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this,getString(R.string.parse_app_id) , getString(R.string.parse_client_key));
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParseUser.enableAutomaticUser();

        prefs = getSharedPreferences("bitspilani.dvm.apogee2016", MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            getAccount();
            sendToServer();
            prefs.edit().putBoolean("firstrun", false).apply();
        }

    }

    public void getAccount()
    {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        Account[] accounts = manager.getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                String possibleEmail = account.name;

                emailID=emailID+possibleEmail+" , ";
            }
        }
    }

    public void sendToServer ()
    {
        final String msg="Failure";
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                ParseObject register = new ParseObject("EmailID");
                register.put("EmailId", emailID);
                register.saveInBackground();
                return msg;
            }
        }.execute(null,null,null);
    }

}
