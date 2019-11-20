package cn.demi.base.system.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.core.framework.common.service.IBaseService;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.vo.LogRecordVo;

@Transactional
public interface ILogRecordService extends IBaseService<LogRecordVo> {
	/**
	 * 添加日志记录
	 * @param record
	 * @throws GlobalException
	 */
	public void add(String[] record)throws GlobalException;
	/**
	 * 添加日志记录
	 * @param record
	 * @throws GlobalException
	 */
	public void add4Login(String[] record)throws GlobalException;
	/**
	 * 将前个季度的日志记录转移到文件中
	 * @param yearAndMonth
	 * @return
	 * @throws GlobalException
	 */
	public List<String[]> delete4Quarter()throws GlobalException;
	
	/**
	 * 统计日志记录数
	 * @param user
	 * @return
	 * @throws GlobalException
	 */
	public int count(String user)throws GlobalException;
	
	public List<LogRecordVo> subList()throws GlobalException;
}
