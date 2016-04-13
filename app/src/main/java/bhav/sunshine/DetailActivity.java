package bhav.sunshine;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailActivity extends ActionBarActivity {

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent preference = new Intent(DetailActivity.this , SettingsActivity.class);
            startActivity(preference);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

        private final static String LOG_TAG = DetailFragment.class.getSimpleName();
        private final static  String HASHTAG_SUNSHINE = "#SunshineApp";
        private String mForecastStr = "" ;

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            Intent intent = getActivity().getIntent();
            View rootView = inflater.inflate(R.layout.fragment_detail , container , false);
            TextView forecast = (TextView) rootView.findViewById(R.id.forecast_detail);
            if(intent != null && intent.hasExtra("forecast")) {
                mForecastStr = intent.getStringExtra("forecast");
            }
            forecast.setText(mForecastStr);
            return rootView;
        }

        private Intent createShareForecastIntent() {
            Intent share = new Intent(Intent.ACTION_SEND);
            //return me to my app
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, mForecastStr + HASHTAG_SUNSHINE);
            return share;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu , MenuInflater inflater) {
            inflater.inflate(R.menu.detail_fragment, menu);

            MenuItem menuItem = menu.findItem(R.id.action_share);
            ShareActionProvider mShareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(menuItem);
            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            } else{
                Log.d(LOG_TAG , "null SAP");
            }
        }

    }



    public void doShare(Intent shareIntent) {
        // When you want to share set the share intent.
        //mShareActionProvider.setShareIntent(shareIntent);

    }
}
