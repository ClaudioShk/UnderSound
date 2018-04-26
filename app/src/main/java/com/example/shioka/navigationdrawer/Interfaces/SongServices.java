package com.example.shioka.navigationdrawer.Interfaces;

import com.example.shioka.navigationdrawer.Models.Song;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Created by Shioka on 04/04/2018.
 */

public interface SongServices {
    public static final String BASE_URL = "http://192.168.43.231:3977/api/";
    String URLGETIMAGESONG = "http://192.168.43.231:3977/api/getSongImage/";
    String DOWNLOADSONG = "http://192.168.43.231:3977/api/downloadEntireSong/";

    @Headers({
            "Content-Type:application/json"
    })
    @GET("getSong/{title}")
    Call<Song> GetSong(@Path("title") String song);

    @GET("getSongList")
    Call<Song> GetSongList();

    @GET("downloadEntireSong/{audioFile}")
    @Streaming
    Call<ResponseBody> DownloadSong(@Path("audioFile")String audioFile);

}
