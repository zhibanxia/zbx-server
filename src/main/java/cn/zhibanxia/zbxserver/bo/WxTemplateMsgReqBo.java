package cn.zhibanxia.zbxserver.bo;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zzy on  2018/12/02 13:11
 */
public class WxTemplateMsgReqBo implements Serializable {
    private static final long serialVersionUID = 7187276076746977486L;
    /**
     * 接受者openId
     */
    private String touser;
    /**
     * 模板id
     */
    private String template_id;
    /**
     * 跳转url
     */
    private String url;
    /**
     * 顶部颜色
     */
    private String topcolor = "#FF0000";

    private Map<String, JSONObject> data = new LinkedHashMap<>();

    public void setFirst(String value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", value);
        jsonObject.put("color", "#173177");
        data.put("first", jsonObject);
    }

    /**
     * 回收小区
     *
     * @param value
     */
    public void setKeyword1(String value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", value);
        jsonObject.put("color", "#173177");
        data.put("keyword1", jsonObject);
    }

    /**
     * 回收类型
     *
     * @param value
     */
    public void setKeyword2(String value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", value);
        jsonObject.put("color", "#173177");
        data.put("keyword2", jsonObject);
    }

    /**
     * 重量
     *
     * @param value
     */
    public void setKeyword3(String value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", value);
        jsonObject.put("color", "#173177");
        data.put("keyword3", jsonObject);
    }

    /**
     * 上门时间
     *
     * @param value
     */
    public void setKeyword4(String value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", value);
        jsonObject.put("color", "#173177");
        data.put("keyword4", jsonObject);
    }

    public void setRemark(String value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", value);
        jsonObject.put("color", "#173177");
        data.put("remark", jsonObject);
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }

    public Map<String, JSONObject> getData() {
        return data;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
