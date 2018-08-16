package com.njz.letsgoapp.map;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.njz.letsgoapp.R;

/**
 * Created by LGQ
 * Time: 2018/7/25
 * Function: map activity
 */

public class MapActivity extends AppCompatActivity {

    MapView mapView;
    AMap aMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        TextView tvTitle = (TextView) findViewById(R.id.title_tv);
        tvTitle.setText("地图");
        ImageView ivLetf = (ImageView) findViewById(R.id.left_iv);
        ivLetf.setVisibility(View.VISIBLE);
        ivLetf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mapView = (MapView) findViewById(R.id.map);//找到地图控件
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();//初始化地图控制器对象

        setStartLocation(28.202099, 112.861404);
        setMarker(28.202099, 112.861404, "长沙市", "麓谷新世界");

    }

    //初始位置
    private void setStartLocation(double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);//构造一个位置
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    //设置标记
    private void setMarker(double lat, double lng, String title, String content) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(new LatLng(lat, lng));
        markerOption.title(title).snippet(content);//snippet 点标记的内容
        markerOption.draggable(false);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.map32)));
        aMap.addMarker(markerOption);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

}
