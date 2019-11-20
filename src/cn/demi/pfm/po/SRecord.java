package cn.demi.pfm.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;


@Entity(name = "pfm_record")
@Table(name = "pfm_record")
@Module(value = "pfm.record")
public class SRecord extends Po<SRecord> {
    private static final long serialVersionUID = 1L;

    public String[] PROPERTY_TO_MAP = {"id", "sort", "orgName", "userName", "scoreExplain","recordTime","code","project","value"};
    //部分
    private String orgId;
    private String orgName;
    //人
    private String userId;
    private String userName;
    //扣分说明
    private String scoreExplain;
    //扣分时间
    private String recordTime;
    //扣分项目代码
    private String code;
    //扣分项目
    private String project;
  //扣分值
    private String value;

    @Column(length = 32)
    public String getOrgName() {
        return orgName;
    }
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    @Column(length = 32)
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Column(length = 1000)
    public String getScoreExplain() {
        return scoreExplain;
    }
    public void setScoreExplain(String scoreExplain) {
        this.scoreExplain = scoreExplain;
    }
    @Column(length = 32)
    public String getOrgId() {
        return orgId;
    }
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    @Column(length = 32)
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    @Column(length = 20)
    public String getRecordTime() {
        return recordTime;
    }
    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
    @Column(length = 32)
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    @Column(length = 32)
    public String getProject() {
        return project;
    }
    public void setProject(String project) {
        this.project = project;
    }
    @Column(length = 32)
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    @Transient
    @Override
    public String[] getPropertyToMap() {
        return PROPERTY_TO_MAP;
    }
}
