package com.example.appnhac.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
public class Theloaitrongngay {
    @SerializedName("Theloai")
    @Expose
    private List<TheLoai> theloai = null;
    @SerializedName("ChuDe")
    @Expose
    private List<ChuDe> chuDe = null;

    public List<TheLoai> getTheloai() {
        return theloai;
    }

    public void setTheloai(List<TheLoai> theloai) {
        this.theloai = theloai;
    }

    public List<ChuDe> getChuDe() {
        return chuDe;
    }

    public void setChuDe(List<ChuDe> chuDe) {
        this.chuDe = chuDe;
    }
}
