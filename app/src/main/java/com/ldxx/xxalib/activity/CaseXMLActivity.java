package com.ldxx.xxalib.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ldxx.xxalib.R;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;

public class CaseXMLActivity extends AppCompatActivity {
    private EditText cityName;
    private EditText cityNum;
    private Button btnOne;
    private Button btnTwo;
    private TextView resultName;
    private TextView resultCode;

    private final String TAG = "[CaseXMLActivity]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_xml);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        cityName = (EditText) findViewById(R.id.city_name);
        cityNum = (EditText) findViewById(R.id.city_num);
        btnOne = (Button) findViewById(R.id.btn_one);
        btnTwo = (Button) findViewById(R.id.btn_two);
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XMLAsyncTask(CaseXMLActivity.this, true).execute(cityName.getText().toString());
            }
        });

        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XMLAsyncTask(CaseXMLActivity.this, false).execute(cityNum.getText().toString());
            }
        });

        resultCode = (TextView) findViewById(R.id.result_one);
        resultName = (TextView) findViewById(R.id.result_two);
    }

    class XMLAsyncTask extends AsyncTask<String, Void, String> {
        private Context context;
        private boolean flag;

        private ProgressDialog dialog;

        public XMLAsyncTask(Context context, boolean flag) {
            this.context = context;
            this.flag = flag;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(context, "", "数据查询中...");
        }

        @Override
        protected String doInBackground(String... params) {
            if (!flag) {
                return getCityNameByCode(params[0]);
            } else {
                return getCodeByCityName(params[0]);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            if (!flag) {
                resultName.setText(s);
            } else {
                resultCode.setText(s);
            }
        }

        private InputStream getXMLFile() throws IOException {
            return context.getResources().getAssets().open("citycode/citycode.xml");
        }

        private String getCityNameByCode(String code) {
            //XmlPullParser
            try {
                InputStream input = getXMLFile();
                //由android.util.Xml创建一个XmlPullParser实例
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(input, "UTF-8");
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String name = parser.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if (name.equals("entry")) {
                                if (parser.getAttributeValue(0).equals(code)) {
                                    eventType = parser.next();
                                    return parser.getText();
                                }
                            }
                            break;
                    }
                    eventType = parser.next();
                }

            } catch (Exception e) {
                Log.e(TAG, "getCityNameByCode: " + e.getMessage(), e);
            }
            return code;
        }

        private String getCodeByCityName(String name) {
            try {
                InputStream input = getXMLFile();
                //由android.util.Xml创建一个XmlPullParser实例
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(input, "UTF-8");
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String str = parser.getName();
                    String code;
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if (str.equals("entry")) {
                                code = parser.getAttributeValue(0);
                                eventType = parser.next();
                                if (parser.getText().equals(name)) {

                                    return code;
                                }
                            }
                            break;
                    }
                    eventType = parser.next();
                }

            } catch (Exception e) {
                Log.e(TAG, "getCodeByCityName: " + e.getMessage(), e);
            }
            return name;
        }
    }

}
