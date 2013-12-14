package cn.ppm123.contact;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

public class LaunchActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch);

        LinearLayout bg = (LinearLayout) findViewById(R.id.launch_bg);
        bg.setBackgroundColor(Color.rgb(24, 136, 194));

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(LaunchActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }

        }, 2500);
    }
}
