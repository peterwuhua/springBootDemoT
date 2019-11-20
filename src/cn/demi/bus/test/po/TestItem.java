package cn.demi.bus.test.po;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.ActionType;
import cn.core.framework.utils.code.CreateCodeUtils;
import cn.core.framework.utils.code.annotation.Module;
import cn.demi.bus.pjt.po.Cust;
import cn.demi.bus.task.po.Task;
import cn.demi.bus.task.po.TaskPoint;

/**
 * 维度：点位-采样日期-测试项目
 * 项目检测信息
 * 现场平行样、全程序空白 在采样安排和现场采样环节添加
 * 室内平行样 加标回收样 在分析环节添加
 * @author QuJunLong
 */
@Entity(name = "bus_test_item")
@Table(name = "bus_test_item")
@Module(value = "bus.testItem")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TestItem extends Po<TestItem>{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = {"id","sort","createUser","isDel","itemId","itemName","value","isBack","type","status"};
	
	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	/**
	 * 任务
	 */
	private Task task; 

	private Cust cust;//客户信息
	/**
	 * 点位
	 */
	private TaskPoint point;
	/**
	 * 子任务
	 */
	private TaskItem tim;
	/**
	 * 采样日期
	 */
	private String cyDate;
	 /**
     * 父项目
     */
	private TestItem parent;
	/**
	 * 检测项目
	 */
	private String itemId;
	private String itemName;
	private int level;//级别
	/**
	 * 项目类型
	 * 0实验室项目
	 * 1现场项目
	 * 2重测项目
	 */
	private String type;
	private String value; //值         均值(多结果结构)
	private String value2;//值         均值(多结果结构)
	private String sl;    //速率     均值(多结果结构)
	/**
	 * PC-TWA
	 * 时间加权平均容器浓度
	 * 以时间加权规定的8小时，40小时平均容器浓度
	 */
	private String twa;
	/**
	 * PC-TWA
	 * 在PC-TWA前提下，容许短时间（15min）接触浓度
	 */
	private String stel;
	/**
	 * 最高容许浓度
	 */
	private String mac;
	/**
	 * 超限倍数
	 */
	private String lmt;
	/**
	 * 单项判定
	 * 合格 不合格
	 */
	private String result;
    private String isBack;
    private String status;

	@ManyToOne
	@JoinColumn(name = "cust_id")
	public Cust getCust() {
		return cust;
	}
	public void setCust(Cust cust) {
		this.cust = cust;
	}
	@ManyToOne
	@JoinColumn(name = "task_id")
    public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	@ManyToOne
	@JoinColumn(name = "point_id")
	public TaskPoint getPoint() {
		return point;
	}
	public void setPoint(TaskPoint point) {
		this.point = point;
	}
	@ManyToOne
	@JoinColumn(name = "tim_id")
	public TaskItem getTim() {
		return tim;
	}
	public void setTim(TaskItem tim) {
		this.tim = tim;
	}
	@ManyToOne
	@JoinColumn(name = "parent_id")
	public TestItem getParent() {
		return parent;
	}
	public void setParent(TestItem parent) {
		this.parent = parent;
	}
	@Column(length = 32)
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	@Column(length = 32)
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@Column(length = 32)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(length = 32)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Column(length = 32)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	@Column(length = 2)
	public String getIsBack() {
		return isBack;
	}
	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	@Column(length = 32)
	public String getSl() {
		return sl;
	}
	public void setSl(String sl) {
		this.sl = sl;
	}
	@Column(length = 2)
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	@Column(length = 32)
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	@Column(length = 32)
	public String getTwa() {
		return twa;
	}
	public void setTwa(String twa) {
		this.twa = twa;
	}
	@Column(length = 32)
	public String getStel() {
		return stel;
	}
	public void setStel(String stel) {
		this.stel = stel;
	}
	@Column(length = 32)
	public String getLmt() {
		return lmt;
	}
	public void setLmt(String lmt) {
		this.lmt = lmt;
	}
	@Column(length = 32)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(length = 20)
	public String getCyDate() {
		return cyDate;
	}
	public void setCyDate(String cyDate) {
		this.cyDate = cyDate;
	}
	@Column(length = 32)
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public static void main(String[] args) {
		CreateCodeUtils.autoCreateCode(TestItem.class, true, ActionType.JSP);
	}
}
