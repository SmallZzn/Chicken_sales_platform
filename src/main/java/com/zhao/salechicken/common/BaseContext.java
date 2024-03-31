package com.zhao.salechicken.common;

/**
 * 基于ThreadLocal封装的工具类，用于保存和获取当前登录用户的id
 * 因为客户端每次发送http请求，对应的服务端都会分配一个新的线程来处理
 * 在MyMetaObjectHandler中没办法通过session来获取id，可以通过该类来存放id
 */
public class BaseContext {
    /**
     * ThreadLocal能够为每一个线程提供一份存储空间，具有线程隔离的效果
     * 在线程内才可以获取相应的值，线程外无法获取，因此不会造成冲突
     */
    private static ThreadLocal<Integer> threadLocal=new ThreadLocal<>();

    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(Integer id){
        threadLocal.set(id);
    }

    /**
     * 获取值
     * @return
     */
    public static Integer getCurrentId(){
        return threadLocal.get();
    }


    /**
     * 清除用户信息
     */
    public static void removeUser() {
        threadLocal.remove();
    }
}
