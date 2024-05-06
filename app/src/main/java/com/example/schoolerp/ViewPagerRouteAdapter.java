
package com.example.schoolerp;

        import androidx.annotation.NonNull;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentManager;
        import androidx.fragment.app.FragmentPagerAdapter;

        import java.text.CharacterIterator;

public class ViewPagerRouteAdapter extends FragmentPagerAdapter {


    public ViewPagerRouteAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new RouteFragment();
        } else if (position == 1) {
            return new TrackFragment();
        } else {
            return new StopFragment();
        }

    }

    @Override
    public int getCount() {
        return 3; //no. of tabs
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Route";
        } else if (position == 1) {
            return "Track";
        } else {
            return "Stop";
        }
    }
}

