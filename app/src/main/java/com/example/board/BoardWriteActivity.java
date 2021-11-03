package com.example.board;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class BoardWriteActivity extends AppCompatActivity {
    private EditText  etTitle,etCtnt,etWriter;
    private Button btnWrite;
    private BoardService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);
        etTitle = findViewById(R.id.etTitle);
        etCtnt = findViewById(R.id.etCtnt);
        etWriter = findViewById(R.id.etWriter);

        Retrofit retrofit = RetrofitObj.getInstance();
        service = retrofit.create(BoardService.class);

        btnWrite = findViewById(R.id.btnWrite);
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String ctnt = etCtnt.getText().toString();
                String writer = etWriter.getText().toString();
                BoardVO data = new BoardVO();
                data.setTitle(title);
                data.setCtnt(ctnt);
                data.setWriter(writer);

                Call<Void> call = service.insBoard(data);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
                finish();

            }
        });
    }
}