package com.example.schoolerp;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentManager;
        import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerRouteAdapter extends FragmentPagerAdapter {

    public ViewPagerRouteAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RouteFragment(); // existing fragment
            case 1:
                return new TrackFragment(); // existing fragment
            case 2:
                return new StopFragment(); // new Map fragment
            default:
                return new RouteFragment(); // default fragment
        }
    }

    @Override
    public int getCount() {
        return 3; // total number of tabs
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Route";
            case 1:
                return "Track";
            case 2:
                return "Stop";
            default:
                return null;
        }
    }
}
