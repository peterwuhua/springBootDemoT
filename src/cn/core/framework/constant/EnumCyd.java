package cn.core.framework.constant;

import java.util.ArrayList;
import java.util.List;

import cn.core.framework.utils.StrUtils;

/**
 * 采样单模板
 * @author QuJunLong
 *
 */
public enum EnumCyd {

	HJ_W("水和废水采样单","cyd_w","地下水,地表水,生产废水,生活废水,生活饮用水",null,"环境"),
	HJ_YQ("有组织废气采样单","cyd_q_y","有组织废气",null,"环境"),
	HJ_WQ("无组织废气采样单","cyd_q_w","无组织废气",null,"环境"),
	HJ_S_JT("交通噪声采样单","cyd_s_jt","噪声","交通","环境"),
	HJ_S("声通用采样单","cyd_s","噪声",null,"环境"),
	HJ_S_GSJ("厂界、环境噪声采样单","cyd_s_gsj","噪声","厂界,社会生活,建筑施工","环境"),
	HJ_G("固废底质采样单","cyd_g","固体废弃物,生活垃圾,底泥,沉积物",null,"环境"),
	HJ_T("土壤采样单","cyd_t","土壤",null,"环境"),
	
	//粉尘定点采样单//
	ZW_FC_DD("粉尘定点采样记录单","cyd_fc_dd","总粉尘,呼吸性粉尘,粉尘分散度","职业卫生,公共卫生"),
	
	ZW_HX_DD("有毒物质定点采样单","cyd_hx_dd",null,"职业卫生,公共卫生"),
	ZW_HX_DD_HT("一氧化碳/二氧化碳定点采样单","cyd_hx_ht","一氧化碳,二氧化碳","职业卫生,公共卫生"),
	ZW_HX_EYHG("游离二氧化硅含量采样单","cyd_hx_eyhg","游离二氧化硅含量","职业卫生,公共卫生"),
	
	////物理因素采样记录单(合并) begin ////
	ZW_WL_JG("激光辐射采样记录单","cyd_wl_jg","激光辐射","职业卫生,公共卫生"),
	ZW_WL_CG("采光、照度采样记录单","cyd_wl_cg","采光,照度","职业卫生,公共卫生"),
	ZW_WL_GP("工频电场采样记录单","cyd_wl_gp","工频电场","职业卫生,公共卫生"),
	ZW_WL_ZD("手传振动采样记录单","cyd_wl_zd","手传振动","职业卫生,公共卫生"),
	ZW_WL_WB("微波辐射采样记录单","cyd_wl_wb","微波辐射","职业卫生,公共卫生"),
	ZW_WL_ZF("紫外辐射采样记录单","cyd_wl_zf","紫外辐射","职业卫生,公共卫生"),

	//ZW_WL_QX("气象条件采样记录单","cyd_wl_qx","气象条件","职业卫生,公共卫生"),
	ZW_WL_CP("超高频辐射采样记录单","cyd_wl_cp","超高频辐射","职业卫生,公共卫生"),
	ZW_WL_GD("高频电磁场采样记录单","cyd_wl_gd","高频电磁场","职业卫生,公共卫生"),
	ZW_WL_GW("高温(常用)采样记录单","cyd_wl_gw","高温","职业卫生,公共卫生"),
	ZW_WL_GW_B("高温(受热不均匀)采样记录单","cyd_wl_gw_b","高温","职业卫生,公共卫生"),
	ZW_WL_ZS("噪声(定点、周计权)采样记录单","cyd_wl_zs","噪声","职业卫生,公共卫生"),
	ZW_WL_ZS_B("噪声(个体、脉冲)采样记录单","cyd_wl_zs_b","噪声","职业卫生,公共卫生"),
	ZW_WL_ZS_C("噪声(频谱)采样记录单","cyd_wl_zs_c","噪声","职业卫生,公共卫生"),
	////物理因素采样记录单 end ////
 
	ZW_FS("放射防护检测采样记录单","cyd_fs","空气比释动能率","职业卫生,公共卫生"),
	ZW_FS_X("X射线采样记录单","cyd_fs_x","X射线","职业卫生,公共卫生")
	;
	//环境采样单集合
	public static List<EnumCyd> getList4Hj(){
		List<EnumCyd> cydList=new ArrayList<EnumCyd>();
		cydList.add(HJ_W);
		cydList.add(HJ_YQ);
		cydList.add(HJ_WQ);
		cydList.add(HJ_S);
		cydList.add(HJ_S_JT);
		cydList.add(HJ_S_GSJ);
		cydList.add(HJ_G);
		cydList.add(HJ_T);
		return cydList;
	}
	//职卫采样单集合
	public static List<EnumCyd> getList4Zw(){
		List<EnumCyd> cydList=new ArrayList<EnumCyd>();
		cydList.add(ZW_FC_DD);
		cydList.add(ZW_HX_DD);
		cydList.add(ZW_HX_DD_HT);
		cydList.add(ZW_HX_EYHG);
		cydList.add(ZW_WL_CG);
		cydList.add(ZW_WL_GP);
		cydList.add(ZW_WL_WB);
		cydList.add(ZW_WL_CP);
		cydList.add(ZW_WL_GD);
		cydList.add(ZW_WL_GW_B);
		cydList.add(ZW_WL_GW);
		cydList.add(ZW_WL_JG);
		cydList.add(ZW_WL_ZD);
		cydList.add(ZW_WL_ZF);
		cydList.add(ZW_WL_ZS_B);
		cydList.add(ZW_WL_ZS_C);
		cydList.add(ZW_WL_ZS);
		cydList.add(ZW_FS);
		cydList.add(ZW_FS_X);
		return cydList;
	}
	/**
	 * 获取默认采样单
	 * @param sampType 样品类别
	 * @param item 项目
	 * @return
	 */
	public  static EnumCyd getCyd4Hj(String sampType,String items) {
		List<EnumCyd> cydList=getList4Hj();
		EnumCyd cyd=null;
		if(!StrUtils.isBlankOrNull(sampType)&&!StrUtils.isBlankOrNull(items)) {
			for (EnumCyd enumcyd : cydList) {
				if(null!=enumcyd.getSampType()&&enumcyd.getSampType().contains(sampType)
						&&enumcyd.getItem()!=null) {
					String item[]=enumcyd.getItem().split(",");
					for (String it : item) {
						if(!StrUtils.isBlankOrNull(it)&&items.contains(it)) {
							cyd=enumcyd;
							break;
						}
					}
				}
			}
		}
		if(cyd==null&&!StrUtils.isBlankOrNull(items)) {
			for (EnumCyd enumcyd : cydList) {
				if(enumcyd.getItem()!=null) {
					String item[]=enumcyd.getItem().split(",");
					for (String it : item) {
						if(!StrUtils.isBlankOrNull(it)&&items.contains(it)) {
							cyd=enumcyd;
							break;
						}
					}
				}
			}
		}
		if(cyd==null&&!StrUtils.isBlankOrNull(sampType)) {
			for (EnumCyd enumcyd : cydList) {
				if(null!=enumcyd.getSampType()&&enumcyd.getSampType().contains(sampType)) {
					cyd=enumcyd;
					break;
				}
			}
		}
		if(cyd==null) {
			for (EnumCyd enumcyd : cydList) {
				if(null==enumcyd.getSampType()&&enumcyd.getItem()==null) {
					cyd=enumcyd;
					break;
				}
			}
		}
		return cyd;
	} 
	/**
	 * 职卫 获取采样单
	 * @param item
	 * @return
	 */
	public static EnumCyd getCyd4Zw(String items) {
		List<EnumCyd> cydList=getList4Zw();
		EnumCyd cyd=null;
		if(!StrUtils.isBlankOrNull(items)) {
			for (EnumCyd enumcyd : cydList) {
				if(enumcyd.getItem()!=null) {
					String item[]=enumcyd.getItem().split(",");
					for (String it : item) {
						if(!StrUtils.isBlankOrNull(it)&&items.contains(it)) {
							cyd=enumcyd;
							break;
						}
					}
				}
			}
		}
		if(cyd==null) {
			for (EnumCyd enumcyd : cydList) {
				if(enumcyd.getItem()==null) {
					cyd=enumcyd;
					break;
				}
			}
		}
		return cyd;
	}
	private String name;//模板名称
	private String code;//模板编号
	private String sampType;//样品类别
	private String item;//使用项目
	private String type;//大类  环境，职业卫生，公共卫生
	
	private EnumCyd(String name, String code,String item, String type) {
		this.name = name;
		this.code = code;
		this.item = item;
		this.type = type;
	}
	private EnumCyd(String name, String code,String sampType,String item, String type) {
		this.name = name;
		this.code = code;
		this.sampType = sampType;
		this.item = item;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSampType() {
		return sampType;
	}
	public void setSampType(String sampType) {
		this.sampType = sampType;
	}
	
	
}
