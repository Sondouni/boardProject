package com.example.board;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BoardModActivity extends AppCompatActivity {
    private EditText etTitle,etCtnt,etWriter;
    private Button btnWrite;
    private BoardService service;
    private int iboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);
        etTitle = findViewById(R.id.etTitle);
        etCtnt = findViewById(R.id.etCtnt);
        etWriter = findViewById(R.id.etWriter);

        Intent intent = getIntent();
        iboard = intent.getIntExtra("iboard",0);

        Retrofit retrofit = RetrofitObj.getInstance();
        service = retrofit.create(BoardService.class);
        getBoardDetail(iboard);
    }
    private void getBoardDetail(int iboard) {
        Call<BoardVO> call = service.selBoardDetail(iboard);
        call.enqueue(new Callback<BoardVO>() {
            @Override
            public void onResponse(Call<BoardVO> call, Response<BoardVO> res) {
                if(res.isSuccessful()) {
                    BoardVO vo = res.body();
                    Log.i("myLog",vo.getTitle());
                    etTitle.setText(vo.getTitle());
                    etCtnt.setText(vo.getCtnt());
                    etWriter.setText(vo.getWriter());
                } else {
                    Log.i("myLog", "통신 오류");
                }
            }

            @Override
            public void onFailure(Call<BoardVO> call, Throwable t) {
                Log.i("myLog", "통신 자체 실패");
            }
        });
    }
    public void clkReg(View v){
        String title = etTitle.getText().toString();
        String ctnt = etCtnt.getText().toString();
        String writer = etWriter.getText().toString();
        BoardVO data = new BoardVO();
        data.setTitle(title);
        data.setCtnt(ctnt);
        data.setWriter(writer);
        data.setIboard(iboard);
        Call<Void> call = service.updBoard(data);
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
}