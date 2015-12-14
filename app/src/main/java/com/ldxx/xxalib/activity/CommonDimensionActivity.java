package com.ldxx.xxalib.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ldxx.xxalib.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommonDimensionActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.density)
    TextView densityTextView;
    @Bind(R.id.density_dpi)
    TextView densityDpiTextView;
    @Bind(R.id.font_size)
    TextView fontSize;
    @Bind(R.id.font_size_s)
    TextView fontSizeS;
    @Bind(R.id.width_size)
    TextView widthSize;
    @Bind(R.id.width_size_s)
    TextView widthSizeS;
    @Bind(R.id.text_one)
    TextView textOne;
    @Bind(R.id.text_two)
    TextView textTwo;
    @Bind(R.id.text_three)
    TextView textThree;
    @Bind(R.id.text_four)
    TextView textFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_dimension);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        densityTextView.setText("getDisplayMetrics().density:" + getResources().getDisplayMetrics().density);
        densityDpiTextView.setText("getDisplayMetrics().densityDpi:" + getResources().getDisplayMetrics().densityDpi);
        fontSize.setText("getDimension:" + getResources().getDimension(R.dimen.dimen_font_size));
        fontSizeS.setText("getDimensionPixelSize:" + getResources().getDimensionPixelSize(R.dimen.dimen_font_size));

        widthSize.setText("getDimension:" + getResources().getDimension(R.dimen.dimen_width));
        widthSizeS.setText("getDimensionPixelSize:" + getResources().getDimensionPixelSize(R.dimen.dimen_width));

        textTwo.setTextSize(textOne.getTextSize());
        textThree.setTextSize(18);

        textFour.setText("一楼和三楼的字体大小是否相等：" + (textOne.getTextSize() == textThree.getTextSize()));
    }

    @OnClick(R.id.fab)
    public void onClicke(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
