package com.qfix.bugfix.saver;

import android.os.Build;
import android.text.TextUtils;

import com.qfix.bugfix.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by caiyongjian on 16-7-20.
 */
public class SystemMessage implements MessageSource {
    private static final String UNKNOWN_MID = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    private final static String TAG_MACHINE_ID = "mid";
    private final static String TAG_SDK = "sdk";
    private final static String TAG_MODEL = "model";
    private final static String TAG_BRAND = "brand";
    private final static String TAG_PRODUCT = "product";

    private String mMachineId;
    private int mSdkInt;

    private String mModel;
    private String mBrand;
    private String mProduct;

    public SystemMessage(String machineId) {
        mMachineId = machineId;

        if (TextUtils.isEmpty(mMachineId)) {
            machineId = UNKNOWN_MID;
        }
        mSdkInt = Build.VERSION.SDK_INT;
        mModel = Build.MODEL;
        mBrand = Build.BRAND;
        mProduct = Build.PRODUCT;
    }

    public String getMachineId() {
        return mMachineId;
    }

    public int getSdkInt() {
        return mSdkInt;
    }

    public String getSdkString() {
        return String.valueOf(mSdkInt);
    }

    public String getModel() {
        return mModel;
    }

    public String getBrand() {
        return mBrand;
    }

    public String getProduct() {
        return mProduct;
    }

    @Override
    public void print(JSONObject jsonObject) throws JSONException {
        Utils.sparePrint(jsonObject, TAG_MACHINE_ID, mMachineId);
        Utils.sparePrint(jsonObject, TAG_SDK, getSdkString());
        Utils.sparePrint(jsonObject, TAG_MODEL, mModel);
        Utils.sparePrint(jsonObject, TAG_BRAND, mBrand);
        Utils.sparePrint(jsonObject, TAG_PRODUCT, mProduct);
    }
}
