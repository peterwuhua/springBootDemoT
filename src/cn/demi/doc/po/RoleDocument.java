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
import cn.demi.base.system.po.Role;

/**
 * Create on : 2016年11月16日 下午4:53:09 <br>
 * Description : 文档授权表  <br>
 * 
 * @version v 1.0.0 <br>
 * @author Dave Yu<br>
 */
@Entity(name = "doc_role_document")
@Table(name = "doc_role_document")
@Module(value = "doc.roleDocument")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleDocument extends Po<RoleDocument> {
	private static final long serialVersionUID = 1L;
	/**
	 * 对象转换为map时，需要转换的属性
	 */
	public String[] PROPERTY_TO_MAP= {"id","sort","role","document","category","path"};
	/**
	 * 角色
	 */
	private Role role; 
	/**
	 * 文件
	 */
	private Document document;
	/**
	 * 文件夹
	 */
	private Category category;
	/**
	 * 路径
	 */	
	private String path;
	/**
	 * 授权时间
	 */
	private String perTime;
	/**
	 * 文件夹授权Id
	 */
	private String perDirId;
	
	@ManyToOne(targetEntity = Role.class)
	@JoinColumn(name = "role_id")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	@ManyToOne(targetEntity = Category.class)
	@JoinColumn(name = "category_id")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@ManyToOne(targetEntity = Document.class)
	@JoinColumn(name = "document_id")
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
	@Column(length=64)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	@Transient
	public String[] getPropertyToMap() {
		return PROPERTY_TO_MAP;
	}
	
	@Column(length=64)
	public String getPerTime() {
		return perTime;
	}

	public void setPerTime(String perTime) {
		this.perTime = perTime;
	}

	@Column(length=64)
	public String getPerDirId() {
		return perDirId;
	}

	public void setPerDirId(String perDirId) {
		this.perDirId = perDirId;
	}
}
