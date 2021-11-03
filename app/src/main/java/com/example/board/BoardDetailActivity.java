package com.example.board;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BoardDetailActivity extends AppCompatActivity {
    private TextView tvTitle,tvCtnt,tvWriter,tvRdt;
    private BoardService service;
    private int iboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);
        tvTitle = findViewById(R.id.tvTitle);
        tvCtnt = findViewById(R.id.tvCtnt);
        tvWriter = findViewById(R.id.tvWriter);
        tvRdt = findViewById(R.id.tvRdt);

        Retrofit rf = RetrofitObj.getInstance();
        service = rf.create(BoardService.class);

        Intent intent = getIntent();
        iboard = intent.getIntExtra("iboard",0);
        Log.i("myLog",""+iboard);
        getBoardDetail(iboard);
    }
    public void onStart(){
        super.onStart();
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
                    tvTitle.setText(vo.getTitle());
                    tvCtnt.setText(vo.getCtnt());
                    tvWriter.setText(vo.getWriter());
                    tvRdt.setText(vo.getRdt());
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
    public void clkMod(View v){
        Intent intent = new Intent(v.getContext(),BoardModActivity.class);
        intent.putExtra("iboard",iboard);
        v.getContext().startActivity(intent);
    }
    public void clkDel(View v){
        AlertDialog.Builder ad = new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Are you sure to Delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Log.i("myLog","yes"+iboard);
                        Call<Void> call = service.delBoard(iboard);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                })
                .setNegativeButton("No", null);
        ad.create().show();
    }
}