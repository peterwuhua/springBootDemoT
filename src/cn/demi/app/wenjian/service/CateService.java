package cn.demi.app.wenjian.service;


import cn.core.framework.exception.GlobalException;
import cn.demi.app.wenjian.vo.AppCate;

import java.util.List;

public interface CateService {


    /**
     * 文件夹
     *
     * @param id
     * @return
     * @throws GlobalException
     */
    public List<AppCate> list(String id) throws GlobalException;


}
