package cn.demi.base.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.po.Po;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.constant.Constants;
import cn.core.framework.exception.GlobalException;
import cn.demi.base.system.dao.IFilesDao;
import cn.demi.base.system.po.Files;
import cn.demi.base.system.service.IFilesService;
import cn.demi.base.system.vo.FilesVo;

@Service("sys.filesService")
public class FilesServiceImpl extends BaseServiceImpl<FilesVo,Files> implements
		IFilesService {

	@Autowired
	private IFilesDao filesDao;
	@Override
	public List<FilesVo> listByBusId(String busId) throws GlobalException {
		String jpql="FROM "+filesDao.getEntityName(Files.class)+" WHERE busId='"+busId+"'";
		List<Files> pList=filesDao.list(jpql);
		List<FilesVo> fileList=toVoList(pList);
		return fileList;
	}
	@Override
	public List<FilesVo> listByUserId(String busId, String loginName) throws GlobalException {
		String jpql="FROM "+filesDao.getEntityName(Files.class)+" WHERE busId='"+busId+"' AND createUser like '%【"+loginName+"】' ";
		List<Files> pList=filesDao.list(jpql);
		return toVoList(pList);
	}
	@Override
	public void saveFiles(List<FilesVo> filesList)  throws GlobalException{
		if(null!=filesList) {
			for (FilesVo fileVo : filesList) {
				List<Files> fileList=filesDao.list("FROM "+filesDao.getEntityName(Files.class)+" WHERE isDel='"+Po.N+"' AND fileName='"+fileVo.getFileName()+"' AND busId='"+fileVo.getBusId()+"'");
				if(null!=fileList&&fileList.size()>0) {
					Files file=fileList.get(0);
					file=file.toPo(fileVo, file);
					filesDao.update(file);
				}else {
					Files file=new Files();
					file=file.toPo(fileVo, file);
					filesDao.add(file);
				}
			}
		}
	}

	@Override
	public void deleteByBusId(String busId)  throws GlobalException{
		String jpql="DELETE FROM "+filesDao.getEntityName(Files.class)+" WHERE busId='"+busId+"'";
		filesDao.query(jpql).executeUpdate();
	}

	@Override
	public void deleteById(String id)  throws GlobalException{
		filesDao.delete(filesDao.findById(id));
	}

	@Override
	public List<FilesVo> listByBusId(String busId, String busType) throws GlobalException {
		String jpql="FROM "+filesDao.getEntityName(Files.class)+" WHERE busId='"+busId+"' AND busType='"+busType+"' ";
		List<Files> pList=filesDao.list(jpql);
		return toVoList(pList);
	}

	@Override
	public List<FilesVo> listTemp(String busId) throws GlobalException {
		String jpql="FROM "+filesDao.getEntityName(Files.class)+" WHERE busId='"+busId+"' AND busType='"+Constants.FILE_TYPE_ITEM+"' ";
		List<Files> pList=filesDao.list(jpql);
		List<FilesVo> fileList=toVoList(pList);
		FilesVo blank=new FilesVo();
		blank.setFileName("空白模板.xls");
		blank.setFileType(".xls");
		blank.setFilePath("static/upload/item/blank.xls");
		fileList.add(blank);
		return fileList;
	}


}
