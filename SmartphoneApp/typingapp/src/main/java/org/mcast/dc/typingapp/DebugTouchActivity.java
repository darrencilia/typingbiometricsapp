package org.mcast.dc.typingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class DebugTouchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_touch);

        // Shift layout based on keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // Set is focused
        isFocused = true;

        // Set debug edit texts
        tv_x = (TextView) findViewById(R.id.tv_deb_x);
        tv_y = (TextView) findViewById(R.id.tv_deb_y);
        tv_rawX = (TextView) findViewById(R.id.tv_deb_rawX);
        tv_rawY = (TextView) findViewById(R.id.tv_deb_rawY);
        tv_size = (TextView) findViewById(R.id.tv_deb_size);
        tv_precX = (TextView) findViewById(R.id.tv_deb_precX);
        tv_precY = (TextView) findViewById(R.id.tv_deb_precY);
        tv_ellipse = (TextView) findViewById(R.id.tv_deb_ellipseaxis);
    }

    static TextView tv_x, tv_y, tv_rawX, tv_rawY, tv_size, tv_precX, tv_precY, tv_ellipse;

    public static void UpdateTouchData(float x, float y, float rawX, float rawY, float size, float precX, float precY, float ellipse)
    {
        if (isFocused) {
            tv_x.setText("" + x);
            tv_y.setText("" + y);
            tv_rawX.setText("" + rawX);
            tv_rawY.setText("" + rawY);
            tv_size.setText("" + size);
            tv_precX.setText("" + precX);
            tv_precY.setText("" + precY);
            tv_ellipse.setText("" + ellipse);
        }
    }

    static boolean isFocused = false;
    public static boolean isFocused() { return isFocused; }

    @Override
    protected void onPause() {
        super.onPause();
        isFocused = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isFocused = true;
    }
}
