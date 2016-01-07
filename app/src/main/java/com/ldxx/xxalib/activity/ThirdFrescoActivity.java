package com.ldxx.xxalib.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.ldxx.xxalib.R;

public class ThirdFrescoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init Fresco
        Fresco.initialize(this);

        setContentView(R.layout.activity_third_fresco);
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

        Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/fresco-logo.png");
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        draweeView.setImageURI(uri);

        Uri uri1 = Uri.parse("http://static.oschina.net/uploads/img/201510/13184840_xC8r.png");
        SimpleDraweeView one = (SimpleDraweeView) findViewById(R.id.image_one);
        //one.setImageURI(uri1);

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(pjpegConfig)
                .build();

        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(uri1)
                .setProgressiveRenderingEnabled(true)
                .build();
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(one.getController());

        one.setController(controller.build());


    }
    ProgressiveJpegConfig pjpegConfig = new ProgressiveJpegConfig() {
        @Override
        public int getNextScanNumberToDecode(int scanNumber) {
            return scanNumber + 2;
        }

        public QualityInfo getQualityInfo(int scanNumber) {
            boolean isGoodEnough = (scanNumber >= 5);
            return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
        }
    };




}
