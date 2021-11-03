package com.example.board;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BoardService {
    @GET("sellist")
    Call<List<BoardVO>> selBoardList();
    @POST("ins")
    Call<Void> insBoard(@Body BoardVO param); // 객체를 json화 시키겠다.
    @GET("sel")
    Call<BoardVO> selBoardDetail(@Query("iboard") int iboard); // Call<>리턴타입을 잘 확인해서, 뭘 받아야 하는지 생각하자
    @GET("del")
    Call<Void> delBoard(@Query("iboard") int iboard);
    @POST("upd")
    Call<Void> updBoard(@Body BoardVO param);
}
