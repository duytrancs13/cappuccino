package io.awesome.app.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.awesome.app.R;

public class ScaleDetectorTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TouchExampleView(this,null, 0));
    }
}
