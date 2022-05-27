package com.example.appnhac.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BaiHat implements Parcelable {

@SerializedName("IdBaiHat")
@Expose
private String idBaiHat;
@SerializedName("IdAlbum")
@Expose
private String idAlbum;
@SerializedName("IdTheLoai")
@Expose
private String idTheLoai;
@SerializedName("IdPlayList")
@Expose
private String idPlayList;
@SerializedName("TenBaiHat")
@Expose
private String tenBaiHat;
@SerializedName("CaSi")
@Expose
private String caSi;
@SerializedName("HinhBaiHat")
@Expose
private String hinhBaiHat;
@SerializedName("LinkBaiHat")
@Expose
private String linkBaiHat;
@SerializedName("LuotThich")
@Expose
private String luotThich;

    protected BaiHat(Parcel in) {
        idBaiHat = in.readString();
        idAlbum = in.readString();
        idTheLoai = in.readString();
        idPlayList = in.readString();
        tenBaiHat = in.readString();
        caSi = in.readString();
        hinhBaiHat = in.readString();
        linkBaiHat = in.readString();
        luotThich = in.readString();
    }

    public static final Creator<BaiHat> CREATOR = new Creator<BaiHat>() {
        @Override
        public BaiHat createFromParcel(Parcel in) {
            return new BaiHat(in);
        }

        @Override
        public BaiHat[] newArray(int size) {
            return new BaiHat[size];
        }
    };

    public String getIdBaiHat() {
return idBaiHat;
}

public void setIdBaiHat(String idBaiHat) {
this.idBaiHat = idBaiHat;
}

public String getIdAlbum() {
return idAlbum;
}

public void setIdAlbum(String idAlbum) {
this.idAlbum = idAlbum;
}

public String getIdTheLoai() {
return idTheLoai;
}

public void setIdTheLoai(String idTheLoai) {
this.idTheLoai = idTheLoai;
}

public String getIdPlayList() {
return idPlayList;
}

public void setIdPlayList(String idPlayList) {
this.idPlayList = idPlayList;
}

public String getTenBaiHat() {
return tenBaiHat;
}

public void setTenBaiHat(String tenBaiHat) {
this.tenBaiHat = tenBaiHat;
}

public String getCaSi() {
return caSi;
}

public void setCaSi(String caSi) {
this.caSi = caSi;
}

public String getHinhBaiHat() {
return hinhBaiHat;
}

public void setHinhBaiHat(String hinhBaiHat) {
this.hinhBaiHat = hinhBaiHat;
}

public String getLinkBaiHat() {
return linkBaiHat;
}

public void setLinkBaiHat(String linkBaiHat) {
this.linkBaiHat = linkBaiHat;
}

public String getLuotThich() {
return luotThich;
}

public void setLuotThich(String luotThich) {
this.luotThich = luotThich;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idBaiHat);
        parcel.writeString(idAlbum);
        parcel.writeString(idTheLoai);
        parcel.writeString(idPlayList);
        parcel.writeString(tenBaiHat);
        parcel.writeString(caSi);
        parcel.writeString(hinhBaiHat);
        parcel.writeString(linkBaiHat);
        parcel.writeString(luotThich);
    }
}
