package cn.demi.res.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;

@Entity(name = "res_train_detail")
@Table(name = "res_train_detail")
@Module(value = "res.trainDetail")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TrainDetail extends Po<TrainDetail>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","train","userName","result","trainDate"};
	
	@Transient
	@Override
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	private Train train;
	/**
	 * 被培训人
	 */
	private String userId;
	private String userName;
	/**
	 * 培训结果
	 */
	private String result;
	/**
	 * 培训时间
	 */
	private String trainDate;
	/**
	 * 备注
	 */
	private String remark;
	
	@ManyToOne
	@JoinColumn(name = "train_id")
	public Train getTrain() {
		return train;
	}
	public void setTrain(Train train) {
		this.train = train;
	}
	@Column(length = 32)
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(length = 32)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 32)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Column(length = 20)
	public String getTrainDate() {
		return trainDate;
	}
	public void setTrainDate(String trainDate) {
		this.trainDate = trainDate;
	}
	@Lob
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(TrainDetail.class, false, ActionType.JSP);
	}	
}
