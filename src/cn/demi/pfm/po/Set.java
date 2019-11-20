package cn.demi.pfm.po;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;


@Entity(name = "pfm_rules")
@Table(name = "pfm_rules")
@Module(value = "pfm.rules")
public class Set extends Po<Set> {
    private static final long serialVersionUID = 1L;

    public String[] PROPERTY_TO_MAP = {"id", "sort", "workName", "code", "value", "remarks", "time"};

    private String workName;

    private String code;

    private String value;

    private String remarks;

    private float time;

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    @Lob
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    @Transient
    @Override
    public String[] getPropertyToMap() {
        return PROPERTY_TO_MAP;
    }
}
