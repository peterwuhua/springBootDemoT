package cn.demi.app.sys.vo;

import java.util.Map;

public class QuestionsVo {

    private String id;
    private String userId;
    private String userName;//提问人
    private String date;//提问时间
    private String contact;//联系方式
    private String title;//标题
    private String content;//内容
    private String moduleName;//模块

    //构造函数
    public QuestionsVo() {
        super();
    }

    public QuestionsVo(Map<String, String[]> map) {
        this.id = map.get("id") != null ? map.get("id")[0] : null;
        this.userId = map.get("userId") != null ? map.get("userId")[0] : null;
        this.userName = map.get("userName") != null ? map.get("userName")[0] : null;
        this.date = map.get("date") != null ? map.get("date")[0] : null;
        this.contact = map.get("contact") != null ? map.get("contact")[0] : null;
        this.title = map.get("title") != null ? map.get("title")[0] : null;
        this.content = map.get("content") != null ? map.get("content")[0] : null;
        this.moduleName = map.get("moduleName") != null ? map.get("moduleName")[0] : null;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
