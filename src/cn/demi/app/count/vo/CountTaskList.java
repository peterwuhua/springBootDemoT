package cn.demi.app.count.vo;

/**
 * 任务查询
 */
public class CountTaskList {
    private String id;//任务id
    private String no;//任务编号
    private String sampName;//样品名称
    private String custName;
    private String status;
    private String finishDate;//要求完成时间  出报告日期
    private String pointName;//点位
    private String taskType;//测试类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getSampName() {
        return sampName;
    }

    public void setSampName(String sampName) {
        this.sampName = sampName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
