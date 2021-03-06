package gjk.kepler;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.Toast;

public class SettingsActivity extends BaseActivity {

    public static final String ARG_ASK = "ask";

    @Override protected int getLayoutResource() {
        return R.layout.activity_settings;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set Content View se provede v BaseActivity

        getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();

        if(getIntent().getBooleanExtra(ARG_ASK, false)){ //default=false
            Toast.makeText(this, "Nastavte prosím svou třídu.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Zpětná navigace
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* od API 11 výše se vytváří settings uvnitř Fragmentu a ne přímo v Aktivitě */
    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Načti strukturu nastavení z preferences.xml
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}

