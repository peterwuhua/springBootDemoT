package cn.core.framework.common.po;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * <strong>Create on : 2016年11月2日 上午11:28:50 </strong> <br>
 * <strong>树形Po（Persistence Object - 持久化对象） </strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong>Carson Liu</strong><br>
 */
@MappedSuperclass
public abstract class TreePo<P extends TreePo<?>> extends Po<P> {

	private static final long serialVersionUID = 1L;
	/**
	 * PARENT_NAME = "parent"
	 */
	public static final String PARENT_NAME = "parent";//父节点属性key
	/**
	 * String NAME = "name";
	 */
	public static final String NAME = "name";//节点名称属性key
	/**
	 * PROPERTY_TO_MAP
	 */
	public String[] PROPERTY_TO_MAP= {"id","parent","level","name","path","sort"};
	/**
	 * 节点名称
	 */
	private String name;
	/**
	 * 上级节点
	 */
	private P parent;
	/**
	 * 节点路径
	 */
	private String path;
	/**
	 * 节点深度 
	 */
	private int level;
	
	@ManyToOne
	@JoinColumn(name = "pid")
	public P getParent() {
		return parent;
	}

	public void setParent(P parent) {
		this.parent = parent;
	}

	@Column(length = 64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(length = 8)
	public void setLevel(int level) {
		this.level = level;
	}
	@Column(length = 255)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getLevel() {
		return level;
	}

	/**
	 * <strong>Create on : 2016年11月2日 上午11:30:05 </strong> <br>
	 * <strong>Description : 判断指定对象是否为TreePo（Tree Persistence Object - 树形可持久化对象） </strong> <br>
	 * @param pClazz pClazz
	 * @return  是否为继承自treePo
	 */
	public static boolean isTreePo(Class<?> pClazz) {
		return TreePo.class.isAssignableFrom(pClazz);
	}
	
	@Override
	@Transient
	public void onAdd() {
		setPath(getPath(this, name));
		if(this.getParent()!=null) {
			level=this.getParent().getLevel()+1;
		}else {
			level=0;
		}
		setLevel(level);
		super.onAdd();
	}
	
	@Override
	@Transient
	public void onUpdate() {
		setPath(getPath(this, name));
		if(this.getParent()!=null) {
			level=this.getParent().getLevel()+1;
		}else {
			level=0;
		}
		setLevel(level);
		super.onUpdate();
	}
	
	@Transient
	public String getPath(TreePo<?> po,String path) {
		if(null==po.getParent()){
			return po.getName();
		}else{
			return getPath(po.getParent(),path)+"/"+po.getName();
		}
	}

}
