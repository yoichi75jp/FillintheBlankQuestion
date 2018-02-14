package com.aufthesis.fill_in_the_blankquestion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
// * Created by a2035210 on 2018/02/14.
 */

public class YojijukugoActivity extends Activity implements Runnable{

    private TextView m_timerText = null;

    private long m_startTime;
    private long m_pauseTime;
    private long m_diffTime;
    private SimpleDateFormat m_dataFormat = new SimpleDateFormat("mm:ss.SSS", Locale.getDefault());

    private Thread m_thread = null;
    private final Handler m_handler = new Handler();
    private volatile boolean m_stopRun = false;

    // 10 m_sec order
    private int m_period = 10;
    private int m_limitMinutes = 1;

    // 効果音用
    final int SOUND_POOL_MAX = 6;
    private SoundPool m_soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yojijukugo);

        m_stopRun = false;
        m_thread = new Thread(this);
        m_limitMinutes = 1;
        m_period = 10;

        this.startDialog();
    }


    //開始時のダイアログ
    private void startDialog()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.start_title));
        dialog.setMessage(getString(R.string.start_message, "穴埋め " + getString(R.string.q_idiom), m_limitMinutes));
        dialog.setPositiveButton(getString(R.string.start_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                m_thread.start();
                m_startTime = System.currentTimeMillis();
            }
        });
        dialog.setNegativeButton(getString(R.string.start_cancel),new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }


    public void run() {

        while (!m_stopRun) {
            // sleep: period m_sec
            try {
                Thread.sleep(m_period);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                m_stopRun = true;
            }

            m_handler.post(new Runnable() {
                @Override
                public void run() {
                    long endTime = System.currentTimeMillis();
                    // カウント時間 = 経過時間 - 開始時間
                    m_diffTime = (endTime - m_startTime);
                    m_timerText.setText(m_dataFormat.format(m_diffTime));
                }
            });
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        // 予め音声データを読み込む
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            m_soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }
        else
        {
            AudioAttributes attr = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            m_soundPool = new SoundPool.Builder()
                    .setAudioAttributes(attr)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();
        }
//        m_correctSound = m_soundPool.load(getApplicationContext(), R.raw.correct2, 0);
//        m_incorrectSound = m_soundPool.load(getApplicationContext(), R.raw.incorrect1, 0);
//        m_cheer2SoundID = m_soundPool.load(getApplicationContext(), R.raw.cheer_long, 0);
//        m_clearSoundID = m_soundPool.load(getApplicationContext(), R.raw.cheer, 0);
//        m_failedSoundID = m_soundPool.load(getApplicationContext(), R.raw.tin, 0);
//        m_laughSoundID = m_soundPool.load(getApplicationContext(), R.raw.laugh, 0);
    }

    @Override
    public void onPause() {
        super.onPause();
        m_pauseTime = System.currentTimeMillis();
        m_soundPool.release();
    }
    @Override
    protected void onRestart() {
        super.onRestart();

        long endTime = System.currentTimeMillis();
        m_startTime = m_startTime - (endTime - m_pauseTime);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        setResult(RESULT_OK);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            m_stopRun = true;
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(getString(R.string.pause_title));
            dialog.setMessage(getString(R.string.pause_message, "穴埋め " + getString(R.string.q_idiom)));
            dialog.setPositiveButton(getString(R.string.pause_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    AdRequest adRequest = new AdRequest.Builder().build();
//                    m_InterstitialAd.loadAd(adRequest);
//                    if (m_InterstitialAd.isLoaded()) {
//                        m_InterstitialAd.show();
//                    }
                    finish();
                }
            });
            dialog.setNegativeButton(getString(R.string.pause_cancel), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_stopRun = false;
                }
            });
            dialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
        switch (requestCode) {
            case 1:
                break;
            case 2:
                break;
            default:break;
        }
    }
}
