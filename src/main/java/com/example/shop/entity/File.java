package com.example.shop.entity;

import com.example.shop.util.FileUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 文件实体类
 */
@Entity
@Table(name = "file")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class File implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;         // 主键
    private Integer sid;        // 外键
    @Column(length = 255)
    private String name;        // 图片名字
    @Column(length = 255)
    private String url;         // 图片url
    @Column(length = 255)
    private String ipAddress;

    @Transient
    private String imageUrl;    // 图片的完整url

    public String getIpAddress() {
        return FileUtil.ipHttpAddress();
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = FileUtil.ipHttpAddress();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getImageUrl() {
        return this.getIpAddress() + this.getUrl();
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
