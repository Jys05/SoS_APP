package com.example.mrsu.sos.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrsu.sos.R;
import com.example.mrsu.sos.frame.FrameActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Mr.Su on 2017-3-11.
 */

public class ActivityMessage extends FrameActivity {

    @InjectView(R.id.JingDu)
    TextView JingDu;
    @InjectView(R.id.WeiDu)
    TextView WeiDu;
    @InjectView(R.id.Country)
    TextView Country;
    @InjectView(R.id.State)
    TextView State;
    @InjectView(R.id.City)
    TextView City;
    @InjectView(R.id.Street)
    TextView Street;
    @InjectView(R.id.send)
    TextView send;
    @InjectView(R.id.home)
    TextView home;
    private LocationManager mLocationManager;

    @Override
    protected int layoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initComponent() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!isGpsAble(mLocationManager)) {
            Toast.makeText(this, "请打开GPS~", Toast.LENGTH_SHORT).show();
            openGPS2();
        }
        //从GPS获取最近的定位信息
        checkSelfPermission();
        Location lc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        updateShow(lc);
        //设置间隔两秒获得一次GPS定位信息
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 8, locationListener);
    }

    //监听权限
    private void checkSelfPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        checkSelfPermission();
        mLocationManager.removeUpdates(locationListener);
    }

    @OnClick(R.id.home)
    public void onHome() {
        //回到主页面
        finish();
    }

    @OnClick(R.id.send)
    public void onSend() {

    }

    //判断是否开启GPS
    private boolean isGpsAble(LocationManager lm) {
        return lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER) ? true : false;
    }


    //打开设置页面让用户自己设置
    private void openGPS2() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, 0);
    }

    //定义一个更新显示的方法
    private void updateShow(Location location) {
        if (location != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("当前的位置信息：\n");
            sb.append("经度：" + location.getLongitude() + "\n");
            sb.append("纬度：" + location.getLatitude() + "\n");
            sb.append("高度：" + location.getAltitude() + "\n");
            sb.append("速度：" + location.getSpeed() + "\n");
            sb.append("方向：" + location.getBearing() + "\n");
            sb.append("定位精度：" + location.getAccuracy() + "\n");
            JingDu.setText("经度："+location.getLongitude());
            WeiDu.setText("纬度："+location.getLatitude());
            Toast.makeText(this, ""+sb.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // 当GPS定位信息发生改变时，更新定位
            updateShow(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            // 当GPS LocationProvider可用时，更新定位
            checkSelfPermission();
            updateShow(mLocationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onProviderDisabled(String provider) {
            updateShow(null);
        }
    };
}
