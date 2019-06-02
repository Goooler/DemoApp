package io.goooler.demoapp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserBean {
    private int id;
    private String username;

    @Generated(hash = 1554268057)
    public UserBean(int id, String username) {
        this.id = id;
        this.username = username;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
