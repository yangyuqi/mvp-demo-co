package com.wxjz.module_base.apppickimagev3.model;

/**
 * 图片实体
 * Created by Nereo on 2015/4/7.
 */
public class Image {
    public static final String MEDIA_TYPE = "video/mp4";
    public String path;
    public String name;
    public long time;
    public long id;
    public int duration;
    public String mediaType;

    public Image(String path, String name, long time, int id){
        this.path = path;
        this.name = name;
        this.time = time;
        this.id = id;
    }

    public Image(String path, String name, long time, int id , int duration , String type){
        this.path = path;
        this.name = name;
        this.time = time;
        this.id = id;
        this.duration = duration;
        this.mediaType = type;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return this.path.equalsIgnoreCase(other.path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
