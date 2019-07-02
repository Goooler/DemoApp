package io.goooler.demoapp.base;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import io.goooler.demoapp.util.JsonUtil;

@Entity
public class BaseBean {
    private int id;
    private String name;

    @Generated(hash = 909048774)
    public BaseBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 1972076277)
    public BaseBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }
}
