package com.example.delta.entity;

public class ApiResponse<T> {
    private Integer code;
    private T data;
    private String msg;

    public ApiResponse(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, data, "成功");
    }

    public static <T> ApiResponse<T> error(String msg) {
        return new ApiResponse<>(500, null, msg);
    }
}