
package youten.redo.jetty.hello;

import youten.redo.jetty.server.JServer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
    JServer mServer = new JServer(8081);
    MenuItem mStopMenuItem;
    MenuItem mStartMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mStartMenuItem = menu.findItem(R.id.action_start);
        mStopMenuItem = menu.findItem(R.id.action_stop);
        setStartMenuEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_start) {
            mServer.start(getApplicationContext());
            setStartMenuEnabled(false);
            return true;
        } else if (item.getItemId() == R.id.action_stop) {
            mServer.stop();
            setStartMenuEnabled(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mServer.stop();
    }

    private void setStartMenuEnabled(boolean startMenuEnabled) {
        mStartMenuItem.setEnabled(startMenuEnabled);
        mStopMenuItem.setEnabled(!startMenuEnabled);
    }
}
