package com.example.board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardListActivity extends AppCompatActivity {
    private RecyclerView rvList;
    private BoardListAdapter adapter;
    private Button btnWrite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boardl_list);
        rvList = findViewById(R.id.rvList);
        btnWrite = findViewById(R.id.btnWrite);
        adapter = new BoardListAdapter();
        rvList.setAdapter(adapter);
        network();
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardListActivity.this,BoardWriteActivity.class);
                startActivity(intent);
            }
        });
    }
    public void onStart(){
        super.onStart();
        network();
    }
    public void network(){
//        Retrofit retrofit = RetrofitObj.getInstance()
        BoardService bsv = RetrofitObj.getInstance().create(BoardService.class);
        Call<List<BoardVO>> call = bsv.selBoardList();
        call.enqueue(new Callback<List<BoardVO>>() {
            @Override
            public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> res) {
                if(res.isSuccessful()){
                    List<BoardVO> list = res.body();
//                    for(BoardVO vo : list){
//                        Log.i("myLog",""+vo.getIboard());
//                    }
                    Collections.reverse(list);
//                    for(BoardVO vo : list){
//                        Log.i("myLog",""+vo.getIboard());
//                    }
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<BoardVO>> call, Throwable t) {
                Log.i("myLog","????????????2");
            }
        });
    }
}
class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.MyViewHolder>{
    private List<BoardVO> list;
    public void setList(List<BoardVO> list){
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_boardvo,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BoardVO vo = list.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int iboard = vo.getIboard();
                Intent intent = new Intent(v.getContext(),BoardDetailActivity.class);
                intent.putExtra("iboard",iboard);
                v.getContext().startActivity(intent);//v.getContext() View v ??? ?????????????????? ???????????? ??????
            }
        });
        holder.setItem(list.get(position));
        //??? ???(??????)??? ?????? ?????? ???????????? ????????????
    }

    @Override
    public int getItemCount() { //???????????????????????? ????????? ???????????? ??????????????????
        return list==null?0:list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvIboard,tvTitle,tvWriter,tvRDT;
        public MyViewHolder(View v){
            super(v);
            tvIboard = v.findViewById(R.id.tvIboard);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvWriter = v.findViewById(R.id.tvWriter);
            tvRDT = v.findViewById(R.id.tvRDT);
        }
        public void setItem(BoardVO param){
            tvIboard.setText(String.valueOf(param.getIboard()));
            // ???????????? ???????????? ????????? values??? String??? id?????? ???????????? ????????? ????????? String??? ??????????????????
            tvTitle.setText(param.getTitle());
            tvWriter.setText(param.getWriter());
            tvRDT.setText(param.getRdt());
        }
    }
}