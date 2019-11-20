package cn.demi.pfm.vo;

import cn.core.framework.common.vo.Vo;


public class SRecordVo extends Vo<SRecordVo> {

    private String orgName;

    private String userName;

    private String scoreExplain;

    private String orgId;

    private String userId;

    private String recordTime;

    private String code;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getScoreExplain() {
        return scoreExplain;
    }

    public void setScoreExplain(String scoreExplain) {
        this.scoreExplain = scoreExplain;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
