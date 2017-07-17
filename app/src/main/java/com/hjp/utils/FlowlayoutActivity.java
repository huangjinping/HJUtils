package com.hjp.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kenya.view.flowlayout.FlowLayout;
import com.kenya.view.flowlayout.TagAdapter;
import com.kenya.view.flowlayout.TagFlowLayout;

/**
 * author Created by harrishuang on 2017/7/17.
 * email : huangjinping@hdfex.com
 */

public class FlowlayoutActivity extends AppCompatActivity {

    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};
    private TagFlowLayout layout_flow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowlayout);
        initView();
        final LayoutInflater mInflater = LayoutInflater.from(this);

        layout_flow.setAdapter(new TagAdapter<String>(mVals)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        layout_flow, false);
                tv.setText(s);
                return tv;
            }
        });

    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, FlowlayoutActivity.class);
        context.startActivity(intent);
    }


    private void initView() {
        layout_flow = (TagFlowLayout) findViewById(R.id.layout_flow);
    }
}
