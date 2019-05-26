package com.example.appforthem.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.appforthem.R;

import java.util.List;

public class PatternLockActivity extends AppCompatActivity {

    com.andrognito.patternlockview.PatternLockView mPatternLockView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_lock);
        mPatternLockView = findViewById(R.id.pattern_lock_view);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);
    }

    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            System.out.println("ON STARTED"+getClass().getName() + " Pattern drawing started");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            System.out.println("ON PROGRESS" + getClass().getName()+ " Pattern progress: " +
                    PatternLockUtils.patternToString(mPatternLockView, progressPattern));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            System.out.println("ON COMPLETE " +getClass().getName()+ " Pattern complete: " +
                    PatternLockUtils.patternToString(mPatternLockView, pattern));
            pattern.clear();
        }

        @Override
        public void onCleared() {
            System.out.println("ON CLEARED" +getClass().getName()+ " Pattern has been cleared");
        }
    };
}
