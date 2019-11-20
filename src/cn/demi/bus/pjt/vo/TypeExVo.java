package cn.demi.bus.pjt.vo;

import java.util.List;

/**
 * 
 * @ClassName:  TypeExVo   
 * @Description:类型 
 * @author: 吴华 
 * @date:   2019年4月24日 下午3:04:59       
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved. 
 *
 */
public class TypeExVo {
 private String type;//类型
 
 private List<CustMaterialVo> materialList;//物料集合
 
public List<CustMaterialVo> getMaterialList() {
	return materialList;
}

public void setMaterialList(List<CustMaterialVo> materialList) {
	this.materialList = materialList;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}
 
 
 
}
