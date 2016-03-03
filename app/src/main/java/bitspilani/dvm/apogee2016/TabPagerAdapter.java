package bitspilani.dvm.apogee2016;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public CharSequence getPageTitle(int position) {

        if (position == 0)
        {
            return "25th Feb";
        }
        if (position == 1)
        {
            return "26th Feb";
        }
        if (position == 2)
        {
            return "27th Feb";
        }
        if (position == 3)
        {
            return "28th Feb";
        }

        return null;
    }

    @Override
    public Fragment getItem(int i) {
        Tab1 tab1=new Tab1();
        Bundle bundle=new Bundle();
        switch (i) {
            case 0:
                bundle.putString("date","25th Feb");
                bundle.putLong("start",1456338600000l);
                bundle.putLong("end",1456425000000l);
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                bundle.putString("date","26th Feb");
                bundle.putLong("start",1456425000000l);
                bundle.putLong("end",1456511400000l);
                tab1.setArguments(bundle);
                return tab1;
            case 2:
                bundle.putString("date","27th Feb");
                bundle.putLong("start",1456511400000l);
                bundle.putLong("end",1456597800000l);
                tab1.setArguments(bundle);
                return tab1;
            case 3:
                bundle.putString("date","28th Feb");
                bundle.putLong("start",1456597800000l);
                bundle.putLong("end",1456684200000l);
                tab1.setArguments(bundle);
                return tab1;

        }
        return null;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 4;
    }

}
