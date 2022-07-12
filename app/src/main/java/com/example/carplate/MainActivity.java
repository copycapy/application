package com.example.carplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnCreate;
    Button btnDelete;
    EditText editTextCarName;
    String carName;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://121.131.89.92")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RetrofitService retrofitService = retrofit.create(RetrofitService.class);
    DeleteService deleteService = retrofit.create(DeleteService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.InitializeView();
        this.SetListener();
    }
    public void InitializeView()
    {
        btnCreate = (Button)findViewById(R.id.btnCreate);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        editTextCarName = (EditText)findViewById(R.id.editTextCarName);
    }

    @Override
    public void onClick(View view) {
        carName = String.valueOf(editTextCarName.getText());
        if (carName.toString().equals("") ){
            Toast.makeText(this.getApplicationContext(), "빈칸을 입력해주세요", Toast.LENGTH_SHORT).show();
        }
        else {
            carName = carName.toString().replace(" ", "");
            switch (view.getId()) {
                case R.id.btnCreate:
                    retrofitService.getData(carName.toString()).enqueue(new Callback<PostResult>() {
                        @Override
                        public void onResponse(Call<PostResult> call, Response<PostResult> response) {
                            PostResult data = response.body();
                            Log.d("Test", String.valueOf(data.getStatus()));
                            Log.d("Test", data.getData());
                            if(data.getStatus() == 200){
                                Toas("등록이 완료되었습니다.");
                            }
                            else if(data.getStatus() == 201) {
                                Toas("이미 등록된 차량입니다.");
                            }
                            else {
                                Toas("Error");
                            }

                        }

                        @Override
                        public void onFailure(Call<PostResult> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                    break;
                case R.id.btnDelete:
                    deleteService.getData(carName.toString()).enqueue(new Callback<PostResult>() {
                        @Override
                        public void onResponse(Call<PostResult> call, Response<PostResult> response) {
                            PostResult data = response.body();
                            Log.d("Test", String.valueOf(data.getStatus()));
                            Log.d("Test", data.getData());
                            if(data.getStatus() == 200){
                                Toas("삭제가 완료되었습니다.");
                            }
                            else if(data.getStatus() == 202) {
                                Toas("이미 등록된 차량입니다.");
                            }
                            else {
                                Toas("Error");
                            }

                        }

                        @Override
                        public void onFailure(Call<PostResult> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                    System.out.println(carName);
                    break;

            }
        }

    }
    public void Toas(String s){
        Toast.makeText(this.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
    public void SetListener()
    {
        btnCreate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }
}