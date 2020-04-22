package com.mgx.blake2b_demo;

import android.os.Bundle;
import android.util.Log;

import com.mgx.blake2b.Blake2bUtil;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        byte[] input = HexUtils.toBytes("043af77aed9bbba24ce1769b2c4f8304dbfd271af4f900d3eb73e5f4504acbb9eb3dcf6d51b143f1c54242850f4cd5bbf9c9b02f3b2f27eb9a183cd49d10557629");

        byte[] result = Blake2bUtil.blake2b(input, 20);     //等同于Blake2b160

        byte[] newData = new byte[21];
        newData[0] = 1;
        System.arraycopy(result, 0, newData, 1, result.length);

        //校验位 4位
        byte[] digest = Blake2bUtil.blake2b(newData, 4);

        byte[] addressData = new byte[result.length + digest.length];
        System.arraycopy(result, 0, addressData, 0, result.length);
        System.arraycopy(digest, 0, addressData, result.length, digest.length);

        String address = Base32.encode(addressData);
        Log.e("输出地址：",address);

    }
}
