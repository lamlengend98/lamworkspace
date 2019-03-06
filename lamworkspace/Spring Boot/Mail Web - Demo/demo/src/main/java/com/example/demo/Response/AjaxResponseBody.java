package com.example.demo.Response;

import java.util.ArrayList;
import java.util.List;

public class AjaxResponseBody {
    private String msg;
    private boolean status;
    private List<Integer> list;

    public AjaxResponseBody(String msg, boolean status, List<Integer> list) {
        this.msg = msg;
        this.status = status;
        this.list = list;
    }

    public AjaxResponseBody() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }
}
