package gjk.kepler;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class Food extends Activity {

    private String url_obedy = "http://gjk.cz/?id=4332";
    private TextView text_obedy;
    private HTML_Loader myHTML;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        text_obedy = (TextView) findViewById(R.id.text_obedy);

        myHTML = new HTML_Loader(this);
        this.getPage(url_obedy); //načti jídelníček při prvním spuštění
    }

    private void getPage(String myURL){
        if(myHTML.checkConnection()){
            Toast.makeText(this, "Aktualizuji...", Toast.LENGTH_LONG).show();
            new DownloadWebpageTask().execute(myURL);
        }else{
            //není připojení
            Toast.makeText(this, "Nejste připojeni k internetu.", Toast.LENGTH_LONG).show();
        }
    }
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        //stáhne webovou stránku v novém vlákně
        @Override
        protected String doInBackground(String... urls) {
            return myHTML.getHTML(urls);
        }
        @Override
        protected void onPostExecute(String result) {
            if(result == null) {
                Toast.makeText(Food.this, "Nelze získat webovou stránku. Chybná URL?", Toast.LENGTH_LONG).show();
            }else{
                show(result);
            }
        }
    }

    private void show(String s){
        text_obedy.setText(s);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //vytvoří položky v horní liště (action bar)
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Akce po kliknutí na jednotlivé položky v horní liště (action bar)
        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                this.getPage(url_obedy);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}