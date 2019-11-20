package cn.demi.doc.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.core.framework.common.po.Po;
import cn.core.framework.utils.code.annotation.Module;

/**
 * Create on : 2016年11月16日 上午10:59:35 <br>
 * Description : 文件 表 <br>
 * 
 * @version v 1.0.0 <br>
 * @author Dave Yu<br>
 */
@Entity(name = "doc_document")
@Table(name = "doc_document")
@Module(value = "doc.document")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Document extends Po<Document> {

	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP = { "id", "sort", "name","time", "title", "size", "sign", "describtion", "path",
			"relativePath", "type", "category", "replaceId", "state" ,"lastUpdUser","lastUpdTime","originalSize","roleNames"};
	/**
	 * 文档名称
	 */
	private String name;
	/**
	 * 文档标题
	 */
	private String title;
	/**
	 * 上传时间
	 */
	private String time;
	/**
	 * 文档大小
	 */
	private String size;
	/**
	 * 原始长度
	 */
	private long originalSize;
	/**
	 * 文档标记
	 */
	private String sign;
	/**
	 * 文档说明
	 */
	private String describtion;
	/**
	 * 节点路径
	 */
	private String path;
	/**
	 * 文件存放路径(相对路径)
	 */
	private String relativePath;
	/**
	 * 文件类型
	 */
	private String type;
	/**
	 * 文档所属类型
	 */
	private Category category;
	/**
	 * 取代文件id
	 */
	private String replaceId;
	/**
	 * 文件状态 :现行/作废
	 */
	public String state;
	/**
	 * 文件一览表角色
	 */
	private String roleNames;
	/**
	 * 是否所有人可见
	 */
	public String isAllVisible;
	/**
	 * 是否同步
	 */
	
	public String isSync;
	public String getIsSync() {
		return isSync;
	}

	public void setIsSync(String isSync) {
		this.isSync = isSync;
	}

	@Column(length=64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length=64)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(length=64)
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Column(length=64)
	public long getOriginalSize() {
		return originalSize;
	}

	public void setOriginalSize(long originalSize) {
		this.originalSize = originalSize;
	}

	@Column(length=64)
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Column(length=64)
	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}
	
	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	@Column(length=64)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	@Column(length=64)
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Column(length=256)
	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	@Column(length=64)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ManyToOne(targetEntity = Category.class)
	@JoinColumn(name = "category_id")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Column(length=64)
	public String getReplaceId() {
		return replaceId;
	}

	public void setReplaceId(String replaceId) {
		this.replaceId = replaceId;
	}

	@Column(length=64)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(length=64)
	public String getIsAllVisible() {
		return isAllVisible;
	}

	public void setIsAllVisible(String isAllVisible) {
		this.isAllVisible = isAllVisible;
	}

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}

}
