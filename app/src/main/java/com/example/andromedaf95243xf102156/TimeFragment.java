package com.example.andromedaf95243xf102156;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeFragment extends Fragment {

    private TextView mTimeTextView;
    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time, container, false);

        mTimeTextView = view.findViewById(R.id.time_text_view);

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                updateTime();
                mHandler.postDelayed(this, 1000);
            }
        };

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTime();
        mHandler.postDelayed(mRunnable, 1000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        mTimeTextView.setText(currentTime);
    }
}
