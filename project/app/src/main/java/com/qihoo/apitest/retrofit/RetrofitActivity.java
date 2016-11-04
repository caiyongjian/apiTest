package com.qihoo.apitest.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.qihoo.apitest.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RetrofitActivity extends Activity {

    @BindView(R.id.requestOutput)
    protected TextView mOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.doRequest)
    protected void doRequest(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        retrofit.Call<List<Repo>> call = service.loadRepo("caiyongjian");
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Response<List<Repo>> response) {
                String output = response.body().toString();
                mOutput.setText(output);
            }

            @Override
            public void onFailure(Throwable t) {
                mOutput.setText(String.format("ERROR:%s", Log.getStackTraceString(t)));
            }
        });
    }
}
