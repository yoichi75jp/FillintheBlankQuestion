package com.aufthesis.fill_in_the_blankquestion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/**
/ * Created by yoichi75jp2 on 2018/02/26.
 */

public class AchievementActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        LinearLayout matrixLayout = findViewById(R.id.view_matrix);
    }
    @Override
    public void onPause() {
//        if (m_AdView != null) {
//            m_AdView.pause();
//        }
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
//        if (m_AdView != null) {
//            m_AdView.resume();
//        }
    }
    @Override
    public void onDestroy()
    {
//        if (m_AdView != null) {
//            m_AdView.destroy();
//        }
        super.onDestroy();
        setResult(RESULT_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
