package cn.demi.app.count.vo;

import java.util.List;

public class CountTaskInfo {
    //客户信息
    private String custName; //单位名称
    private String custTel; //联系电话
    private String custUser; //联系人
    private String custEmail; //邮箱
    private String custZip; //邮编
    private String fax; //传真
    private String custAddress; //地址

    //监测要求
    private String taskType; //监测类别
    private String sampTypeName; //样品类别
    private String source; //样品来源
    private String taskSource; //任务来源 ，采送样单位
    private int reportNum; //报告数量
    private String reportWay; //取报告方式
    private String jj; //加急
    private String finishDate; //要求完成时间  出报告日期
    private String pj; //是否判定
    private float price; //检测费用
    private String fb; //是否分包
    private String userName;//申请人
    private String date;//申请日期
    private String fileName;
    private String jcct;//监测内容
    private String remark;

    //点位信息
    private List<ItemPoint> points;


    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustTel() {
        return custTel;
    }

    public void setCustTel(String custTel) {
        this.custTel = custTel;
    }

    public String getCustUser() {
        return custUser;
    }

    public void setCustUser(String custUser) {
        this.custUser = custUser;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getCustZip() {
        return custZip;
    }

    public void setCustZip(String custZip) {
        this.custZip = custZip;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getSampTypeName() {
        return sampTypeName;
    }

    public void setSampTypeName(String sampTypeName) {
        this.sampTypeName = sampTypeName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTaskSource() {
        return taskSource;
    }

    public void setTaskSource(String taskSource) {
        this.taskSource = taskSource;
    }

    public int getReportNum() {
        return reportNum;
    }

    public void setReportNum(int reportNum) {
        this.reportNum = reportNum;
    }

    public String getReportWay() {
        return reportWay;
    }

    public void setReportWay(String reportWay) {
        this.reportWay = reportWay;
    }

    public String getJj() {
        return jj;
    }

    public void setJj(String jj) {
        this.jj = jj;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getPj() {
        return pj;
    }

    public void setPj(String pj) {
        this.pj = pj;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getJcct() {
        return jcct;
    }

    public void setJcct(String jcct) {
        this.jcct = jcct;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ItemPoint> getPoints() {
        return points;
    }

    public void setPoints(List<ItemPoint> points) {
        this.points = points;
    }
}

