package com.rxy.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

public class ImageActivity extends ActionBarActivity {

    private static final String KEY_USER="KEY_USER";

    public static Intent newIntent(Context context,String msg){
        Intent intent=new Intent(context,ImageActivity.class);
        intent.putExtra(KEY_USER,msg);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        String url = "http://imgsrc.baidu.com/forum/pic/item/c5a85edf8db1cb138e12e1e5dd54564e93584bca.jpg";
        String url1 = "http://image.photophoto.cn/nm-6/018/030/0180300244.jpg";
//        Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/fresco-logo.png");
//        Uri uri1 = Uri.parse(url);
        Uri uri2 = Uri.parse(url1);
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        draweeView.setImageURI(uri2);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setTapToRetryEnabled(true)
                .setOldController(draweeView.getController())
                .setControllerListener(mListener)
                .build();
        draweeView.setController(controller);
//        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
//        GenericDraweeHierarchy hierarchy = builder.
//                setFailureImage(getResources().getDrawable(R.drawable.girl))
//                .build();
//        draweeView.setHierarchy(hierarchy);
        String msg=getIntent().getStringExtra(KEY_USER);
        Log.e("ImageActivity",msg);
    }

    private ControllerListener mListener=new ControllerListener() {
        @Override
        public void onSubmit(String s, Object o) {

        }

        @Override
        public void onFinalImageSet(String s, Object o, Animatable animatable) {

        }

        @Override
        public void onIntermediateImageSet(String s, Object o) {

        }

        @Override
        public void onIntermediateImageFailed(String s, Throwable throwable) {

        }

        @Override
        public void onFailure(String s, Throwable throwable) {

        }

        @Override
        public void onRelease(String s) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
