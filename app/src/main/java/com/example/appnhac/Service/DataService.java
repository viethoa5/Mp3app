package com.example.appnhac.Service;

import com.example.appnhac.Model.Album;
import com.example.appnhac.Model.BaiHat;
import com.example.appnhac.Model.ChuDe;
import com.example.appnhac.Model.PlayList;
import com.example.appnhac.Model.QuangCao;
import com.example.appnhac.Model.TheLoai;
import com.example.appnhac.Model.Theloaitrongngay;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    @GET("songbanner.php")
    Call<List<QuangCao>> GetDataBanner();
    @GET("playlistforcurrentday.php")
    Call<List<PlayList>> GetPlaylistCurrentDay();
    @GET("chudevatheloaitrongngay.php")
    Call<Theloaitrongngay> GetCategoryCurrentDay();
    @GET("albumhot.php")
    Call<List<Album>> GetAlbumHot();
    @GET("Songslikemost.php")
    Call<List<BaiHat>> GetSongLikedMost();
    @FormUrlEncoded
    @POST("songlist.php")
    Call<List<BaiHat>> GetSongListbanner(@Field("idquangcao") String idquangcao);
    @FormUrlEncoded
    @POST("songlist.php")
    Call<List<BaiHat>> GetPlaylistSongListbanner(@Field("idplaylist") String idplaylist);
    @GET("listplaylist.php")
    Call<List<PlayList>> GetFullPlaylist();
    @FormUrlEncoded
    @POST("songlist.php")
    Call<List<BaiHat>> GetCategorySongListbanner(@Field("idtheloai") String idtheloai);
    @GET("alltopic.php")
    Call<List<ChuDe>> GetFullTopic();
    @FormUrlEncoded
    @POST("categoryfollowtopic.php")
    Call<List<TheLoai>> GetCategoryFollowTopicList(@Field("idchude") String idtheloai);
    @GET("fullalbum.php")
    Call<List<Album>> GetFullAlbum();
    @FormUrlEncoded
    @POST("songlist.php")
    Call<List<BaiHat>> GetAlbumSongListbanner(@Field("idalbum") String idalbum);
    @FormUrlEncoded
    @POST("updatelike.php")
    Call<String> UpdateLike(@Field("luotthich") String luotthich,@Field("idbaihat") String idbaihat);
    @FormUrlEncoded
    @POST("SearchSong.php")
    Call<List<BaiHat>> GetSearchSong(@Field("word") String word);
}
