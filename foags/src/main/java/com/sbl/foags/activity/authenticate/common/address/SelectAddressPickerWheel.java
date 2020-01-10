package com.sbl.foags.activity.authenticate.common.address;

import android.content.Context;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.jzxiang.pickerview.config.PickerConfig;
import com.jzxiang.pickerview.wheel.OnWheelChangedListener;
import com.jzxiang.pickerview.wheel.WheelView;
import com.sbl.foags.MyApplication;
import com.sbl.foags.R;
import com.sbl.foags.activity.authenticate.common.address.data.ChinaAddressBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


class SelectAddressPickerWheel {

    private Context mContext;

    private WheelView province;
    private WheelView city;
    private WheelView county;

    private SelectAddressWheelAdapter mProvinceAdapter;
    private SelectAddressWheelAdapter mCityAdapter;
    private SelectAddressWheelAdapter mCountyAdapter;

    private SelectAddressPickerConfig mPickerConfig;

    private OnWheelChangedListener provinceListener = (wheel, oldValue, newValue) -> updateCity();
    private OnWheelChangedListener cityListener = (wheel, oldValue, newValue) -> updateCounty();


    private static Disposable allProvinceDisposable;
    private static Disposable findCityDisposable;
    private static Disposable findCountyDisposable;


    public SelectAddressPickerWheel(View view, SelectAddressPickerConfig pickerConfig) {
        mPickerConfig = pickerConfig;

        mContext = view.getContext();
        initialize(view);
    }

    private void initialize(View view) {
        initView(view);
        updateProvince();
    }

    private void initView(View view) {
        province = view.findViewById(R.id.province);
        city = view.findViewById(R.id.city);
        county = view.findViewById(R.id.county);

        province.setShowLeftLine(false);
        city.setShowLeftLine(false);
        county.setShowLeftLine(false);

        province.addChangingListener(provinceListener);
        city.addChangingListener(cityListener);
    }


    private void updateProvince() {

        if(allProvinceDisposable != null){
            allProvinceDisposable.dispose();
            allProvinceDisposable = null;
        }

        allProvinceDisposable = Observable.just("province.json")
                .map(s -> {
                    InputStream is  = MyApplication.instance.getAssets().open(s);
                    InputStreamReader streamReader = new InputStreamReader(is);
                    JsonReader reader = new JsonReader(streamReader);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<ChinaAddressBean>>(){}.getType();
                    ArrayList<ChinaAddressBean> bean = gson.fromJson(reader,listType);
                    streamReader.close();
                    is.close();
                    return bean;
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(chinaAddressBeans -> {

                    mProvinceAdapter = new SelectAddressWheelAdapter(mContext, chinaAddressBeans);
                    mProvinceAdapter.setConfig(mPickerConfig);
                    province.setViewAdapter(mProvinceAdapter);
                    province.setCurrentItem(0);

                    updateCity();
                });
    }


    private void updateCity() {

        if(findCityDisposable != null){
            findCityDisposable.dispose();
            findCityDisposable = null;
        }

        findCityDisposable = Observable.just(getCurrentProvince().getId())
                .map(s -> {
                    InputStream is  =  MyApplication.instance.getAssets().open("city.json");
                    InputStreamReader streamReader = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(streamReader);

                    String line;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = reader.readLine()) != null){
                        stringBuilder.append(line);
                    }

                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray(s);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<ChinaAddressBean>>(){}.getType();
                    ArrayList<ChinaAddressBean> bean = gson.fromJson(jsonArray.toString(), listType);

                    reader.close();
                    streamReader.close();
                    is.close();
                    return bean;
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(chinaAddressBeans -> {

                    mCityAdapter = new SelectAddressWheelAdapter(mContext, chinaAddressBeans);
                    mCityAdapter.setConfig(mPickerConfig);
                    city.setViewAdapter(mCityAdapter);
                    city.setCurrentItem(0, false);

                    updateCounty();
                });
    }


    private void updateCounty() {

        if(findCountyDisposable != null){
            findCountyDisposable.dispose();
            findCountyDisposable = null;
        }

        findCountyDisposable = Observable.just(getCurrentCity().getId())
                .map(s -> {
                    InputStream is  =  MyApplication.instance.getAssets().open("county.json");
                    InputStreamReader streamReader = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(streamReader);

                    String line;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = reader.readLine()) != null){
                        stringBuilder.append(line);
                    }


                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray(s);
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<ChinaAddressBean>>(){}.getType();
                    ArrayList<ChinaAddressBean> bean = gson.fromJson(jsonArray.toString(), listType);

                    ArrayList<ChinaAddressBean> tmp = new ArrayList();
                    tmp.addAll(bean);
                    for(int a = 0; a < bean.size(); a ++){
                        if(bean.get(a).getName().equals("市辖区")){
                            tmp.remove(a);
                        }
                    }

                    reader.close();
                    streamReader.close();
                    is.close();

                    return tmp;
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(chinaAddressBeans -> {

                    mCountyAdapter = new SelectAddressWheelAdapter(mContext, chinaAddressBeans);
                    mCountyAdapter.setConfig(mPickerConfig);
                    county.setViewAdapter(mCountyAdapter);
                    county.setCurrentItem(0, true);
                });
    }


    public ChinaAddressBean getCurrentProvince() {
        return mProvinceAdapter.getCurrentAddressId(province.getCurrentItem());
    }

    public ChinaAddressBean getCurrentCity() {
        return mCityAdapter.getCurrentAddressId(city.getCurrentItem());
    }

    public ChinaAddressBean getCurrentCounty() {
        return mCountyAdapter.getCurrentAddressId(county.getCurrentItem());
    }


    public void onDismiss(){

        if(allProvinceDisposable != null){
            allProvinceDisposable.dispose();
            allProvinceDisposable = null;
        }
        if(findCityDisposable != null){
            findCityDisposable.dispose();
            findCityDisposable = null;
        }
        if(findCountyDisposable != null){
            findCountyDisposable.dispose();
            findCountyDisposable = null;
        }
    }
}
