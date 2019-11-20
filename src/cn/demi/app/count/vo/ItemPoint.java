package cn.demi.app.count.vo;

public class ItemPoint {
    private String sampId;//样品名称
    private String sampName;//样品名称
    private int sampNum;//样品数量
    private String pointName;//点位名称
    private String pointCode;//点位代码
    private String itemIds;//测试项目
    private String itemNames;

    public String getSampId() {
        return sampId;
    }

    public void setSampId(String sampId) {
        this.sampId = sampId;
    }

    public String getSampName() {
        return sampName;
    }

    public void setSampName(String sampName) {
        this.sampName = sampName;
    }

    public int getSampNum() {
        return sampNum;
    }

    public void setSampNum(int sampNum) {
        this.sampNum = sampNum;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getPointCode() {
        return pointCode;
    }

    public void setPointCode(String pointCode) {
        this.pointCode = pointCode;
    }

    public String getItemIds() {
        return itemIds;
    }

    public void setItemIds(String itemIds) {
        this.itemIds = itemIds;
    }

    public String getItemNames() {
        return itemNames;
    }

    public void setItemNames(String itemNames) {
        this.itemNames = itemNames;
    }
}
