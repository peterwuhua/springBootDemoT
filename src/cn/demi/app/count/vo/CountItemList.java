package cn.demi.app.count.vo;

/**
 * 项目统计
 */
public class CountItemList {
    private String itemId;
    private String no;//任务编号
    private String taskType;//测试类型

    private String itemName;

    private String custName;
    private String sampName;//样品名称
    private String pointNames;

    private String testMan;
    private String testTime;
    private String checkMan;

    private String value; //气 实测值
    private String value2;//气 计算
    private String sl;//速率

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getSampName() {
        return sampName;
    }

    public void setSampName(String sampName) {
        this.sampName = sampName;
    }

    public String getPointNames() {
        return pointNames;
    }

    public void setPointNames(String pointNames) {
        this.pointNames = pointNames;
    }

    public String getTestMan() {
        return testMan;
    }

    public void setTestMan(String testMan) {
        this.testMan = testMan;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    public String getCheckMan() {
        return checkMan;
    }

    public void setCheckMan(String checkMan) {
        this.checkMan = checkMan;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }
}
