package ar.com.twoboot.panico;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class BackAct extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}