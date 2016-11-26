package me.brucezz.sample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.title) TextView mTitle;
    @BindView(R.id.image) ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add("GET");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getData();
        return super.onOptionsItemSelected(item);
    }

    private void getData() {
        HttpClient.getInstance()
            .firstAndroidData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe(this::showLoading)
            .doOnUnsubscribe(this::hideLoading)
            .subscribe(data -> {
                // 加载数据
                mTitle.setText(data.desc);
                if (data.images.size() > 0) {
                    Glide.with(MainActivity.this).load(data.images.get(0)).crossFade().into(mImage);
                }
            }, Throwable::printStackTrace);
    }

    private ProgressDialog mLoading;

    public void showLoading() {
        if (mLoading == null || !mLoading.isShowing()) {
            mLoading = ProgressDialog.show(this, null, "Loading", true, false);
        }
    }

    public void hideLoading() {
        if (mLoading != null && mLoading.isShowing()) {
            mLoading.dismiss();
        }
    }
}
