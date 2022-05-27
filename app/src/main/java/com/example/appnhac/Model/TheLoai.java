package com.example.appnhac.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TheLoai implements Serializable {
    @SerializedName("IdTheloai")
    @Expose
    private String idTheloai;
    @SerializedName("IdKeychude")
    @Expose
    private String idKeychude;
    @SerializedName("TenTheLoai")
    @Expose
    private String tenTheLoai;
    @SerializedName("HinhTheLoai")
    @Expose
    private String hinhTheLoai;

    public String getIdTheloai() {
        return idTheloai;
    }

    public void setIdTheloai(String idTheloai) {
        this.idTheloai = idTheloai;
    }

    public String getIdKeychude() {
        return idKeychude;
    }

    public void setIdKeychude(String idKeychude) {
        this.idKeychude = idKeychude;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    public String getHinhTheLoai() {
        return hinhTheLoai;
    }

    public void setHinhTheLoai(String hinhTheLoai) {
        this.hinhTheLoai = hinhTheLoai;
    }
}
