package cn.zhibanxia.zbxserver.bo;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 推荐回收员微信模板消息bo
 * Created by zzy on  2019/04/06 14:02
 *
 * {{first.DATA}}
 * 业务类型：{{keyword1.DATA}}
 * 订单编号：{{keyword2.DATA}}
 * 提醒时间：{{keyword3.DATA}}
 * {{remark.DATA}}
 *
 */
public class WxRecommHuishouNotifyReqBo {
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
     * 业务类型：纸板/塑料瓶
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
     * 订单编号：回收请求id
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
     * 提醒时间：当前时间
     *
     * @param value
     */
    public void setKeyword3(String value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", value);
        jsonObject.put("color", "#173177");
        data.put("keyword3", jsonObject);
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
