package com.library.base.function;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.library.base.R;
import com.library.base.view.clipImage.ClipImageLayout;

import java.io.ByteArrayOutputStream;

/**
 * 图片剪裁
 *
 * @author zhangyb@ifenguo.com
 * @createDate 2015年5月25日
 */
public class ClipImageActivity extends AppCompatActivity {

    public static final String PHOTO_RESULT = "photo_result";
    public static final String PHOTO_CLIP_SCALE = "photo_clip_scale";
    public static final String URL = "url";

    private ClipImageLayout mClipImageLayout;
    private Toolbar mToolbar;

    /**
     * @Fields url : 图片的URL
     */
    private String url;

    private boolean canScale;

    public static void open(Activity activity, String url, boolean canScale, int requestCode) {
        Intent intent = new Intent(activity, ClipImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(PHOTO_CLIP_SCALE, canScale);
        bundle.putString(URL, url);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_image);
        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);
        receiveFromParent();
        initComponent();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_save) {
            Bitmap bitmap = mClipImageLayout.clip();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] datas = baos.toByteArray();
            Intent intent = new Intent();
            intent.putExtra(PHOTO_RESULT, datas);
            setResult(RESULT_OK, intent);
            finish();
            bitmap.recycle();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected void receiveFromParent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            url = bundle.getString(URL);
            canScale = bundle.getBoolean(PHOTO_CLIP_SCALE);
            mClipImageLayout.init(canScale, url);
        }
    }

    protected void initComponent() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("剪裁");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    /*
     * 重写onBackPressed事件
     * 
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra(PHOTO_RESULT, "");
        setResult(RESULT_OK, intent);
        finish();
    }
}
