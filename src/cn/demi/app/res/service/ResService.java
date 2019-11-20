package cn.demi.app.res.service;

import cn.core.framework.exception.GlobalException;
import cn.demi.app.res.vo.*;
import cn.demi.app.sys.vo.ObjVo;
import cn.demi.res.po.Consumable;
import cn.demi.res.po.Standard;
import net.sf.json.JSONArray;

import java.util.List;

public interface ResService {
    /**
     * app 获取试剂列表
     *
     * @param v
     * @param searchValue
     * @return
     * @throws GlobalException
     */
    public List<AppAgentia> agentialist(ObjVo v, String searchValue) throws GlobalException;

    /**
     * app 标准品列表
     *
     * @param v
     * @param searchValue
     * @return
     * @throws GlobalException
     */
    public List<AppStandard> Standardlist(ObjVo v, String searchValue) throws GlobalException;

    /**
     * app 耗材列表
     *
     * @param v
     * @param searchValue
     * @return
     * @throws GlobalException
     */
    public List<AppConsumable> consumablelist(ObjVo v, String searchValue) throws GlobalException;

    /**
     * app 获取试剂详情
     *
     * @param id
     * @return
     * @throws GlobalException
     */
    public AppAgentia findAgentia(String id) throws GlobalException;

    /**
     * app 获取标准品详情
     *
     * @param id
     * @return
     * @throws GlobalException
     */
    public AppStandard findStandard(String id) throws GlobalException;

    /**
     * app 获取耗材详情
     *
     * @param id
     * @return
     * @throws GlobalException
     */
    public AppConsumable findConsumable(String id) throws GlobalException;

    /**
     * app 获取试剂所有
     */
    public JSONArray AgentiaList() throws GlobalException;

    /**
     * app 获取标准品所有
     */
    public JSONArray StandardList() throws GlobalException;

    /**
     * app 获取耗材所有
     */
    public JSONArray ConsumableList() throws GlobalException;

    /**
     * 修改试剂入库信息
     *
     * @param id
     * @param inNum
     * @param keeper
     * @param date
     * @param remark
     * @return
     * @throws GlobalException
     */
    public String updateaddAgentia(String id, Double inNum, String keeper, String date, String remark)
            throws GlobalException;

    /**
     * 修改试剂出库库信息
     *
     * @param id
     * @param outNum
     * @param leadingPerson
     * @param date
     * @param remark
     * @return
     * @throws GlobalException
     */
    public String updatesubtractAgentia(String id, Double outNum, String leadingPerson, String date, String remark)
            throws GlobalException;

    /**
     * 修改标准品入库信息
     *
     * @param id
     * @param inNum
     * @param keeper
     * @param date
     * @param remark
     * @return
     * @throws GlobalException
     */
    public String updateAddStandard(String id, Double inNum, String keeper, String date, String remark)
            throws GlobalException;

    /**
     * 修改标准品出库信息
     *
     * @param id
     * @param outNum
     * @param leadingPerson
     * @param date
     * @param remark
     * @return
     * @throws GlobalException
     */
    public String updatesubtractStandard(String id, Double outNum, String leadingPerson, String date, String remark)
            throws GlobalException;

    /**
     * 修改耗材入库信息
     *
     * @param id
     * @param inNum
     * @param keeper
     * @param date
     * @param remark
     * @return
     * @throws GlobalException
     */
    public String updateAddConsumable(String id, Double inNum, String keeper, String date, String remark)
            throws GlobalException;

    /**
     * 修改耗材出库信息
     *
     * @param id
     * @param outNum
     * @param leadingPerson
     * @param date
     * @param remark
     * @return
     * @throws GlobalException
     */
    public String updatesubtractConsumable(String id, Double outNum, String leadingPerson, String date, String remark)
            throws GlobalException;

    /**
     * 增加试剂
     *
     * @param agentia
     * @return
     * @throws GlobalException
     */
    public AppAgentia add4dateAgentia(AppAgentia agentia) throws GlobalException;

    /**
     * 增加标准品
     *
     * @param standard
     * @return
     * @throws GlobalException
     */
    public AppStandard add4dateStandard(AppStandard standard) throws GlobalException;

    /**
     * 增加耗材
     *
     * @param consumable
     * @return
     * @throws GlobalException
     */
    public AppConsumable add4dateConsumable(AppConsumable consumable) throws GlobalException;

    /**
     * 试剂新增修改
     *
     * @param id
     * @param no
     * @param name
     * @param type
     * @param purity
     * @param grade
     * @param amount
     * @param safeAmount
     * @param exp
     * @param keeper
     * @param keepId
     * @param ename
     * @param sname
     * @param unit
     * @param supplier
     * @param supplierId
     * @param bnum
     * @param saveCode
     * @param purpose
     * @param mfg
     * @param price
     * @return
     */
    public String saveOrUpdateAgentia(String id, String no, String name, String type, String purity, String grade,
                                      Double amount, Double safeAmount, String exp, String keeper, String keepId, String ename, String sname,
                                      String unit, String supplier, String supplierId, String bnum, String saveCode, String purpose, String mfg,
                                      String price);

    /**
     * 试剂标准品修改
     *
     * @param standard
     * @return
     * @throws GlobalException
     */
    public String saveOrUpdateStandard(Standard standard);

    /**
     * 试剂耗材修改
     *
     * @param consumable
     * @return
     * @throws GlobalException
     */
    public String saveOrUpdateConsumable(Consumable consumable);

    /**
     * app 获取试剂出入库列表
     *
     * @param v
     * @param type
     * @param searchValue
     * @return
     * @throws GlobalException
     */


    public List<AppAgentiaOut> reagentOutList(ObjVo v, int type, String searchValue) throws GlobalException;

    /**
     * app 获取标准品出入库列表
     *
     * @param v
     * @param type
     * @param searchValue
     * @return
     * @throws GlobalException
     */

    public List<AppStandardOut> standardOutList(ObjVo v, int type, String searchValue) throws GlobalException;

    /**
     * app 获取耗材出入库列表
     *
     * @param v
     * @param typem
     * @param searchValue
     * @return
     * @throws GlobalException
     */
    public List<AppConsumableOut> consumableOutList(ObjVo v, int typem, String searchValue) throws GlobalException;

    /**
     * app 供应商列表
     *
     * @param v
     * @return
     * @throws GlobalException
     */
    public List<AppSupplier> SupplierList(ObjVo v) throws GlobalException;

    /**
     * app 字典项内容
     *
     * @param code
     * @return
     * @throws GlobalException
     */
    public JSONArray codeContent(String code) throws GlobalException;
}
