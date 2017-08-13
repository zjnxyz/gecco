package com.geccocrawler.gecco.seed;

import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpPostRequest;
import com.geccocrawler.gecco.request.HttpRequest;

import java.util.Map;

/**
 * Created by riverzu on 2017/8/12.
 * <p>
 * 种子类
 */
public class Seed {

    private String url;

    private String cron;

    /**
     * 使用的编码
     */
    private String charset;

    /**
     * refer
     */
    private String refer;

    private int craw;

    /**
     * 爬虫线程数
     */
    private int threadNum;

    /**
     * 请求头
     */
    private Map<String, String> headers;

    /**
     * 请求参数
     */
    private Map<String, String> params;

    /**
     * cookies信息
     */
    private Map<String, String> cookies;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public int getCraw() {
        return craw;
    }

    public void setCraw(int craw) {
        this.craw = craw;
    }

    public int getThreadNum() {
        return threadNum == 0 ? 1 : threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    /**
     * 是否需要爬取
     *
     * @return true
     */
    public boolean isNeedCraw() {
        return craw == 1;
    }


    public HttpRequest toRequest() {
        if (params == null || params.isEmpty()) {
            HttpGetRequest get = new HttpGetRequest(this.getUrl());
            get.setCharset(charset);
            get.setCookies(this.getCookies());
            get.setHeaders(this.getHeaders());
            get.refer(this.getRefer());
            return get;
        } else {
            HttpPostRequest post = new HttpPostRequest(this.getUrl());
            post.setCharset(charset);
            post.setParameters(this.getParams());
            post.setCookies(this.getCookies());
            post.setHeaders(this.getHeaders());
            post.refer(this.getRefer());
            return post;
        }
    }
}
