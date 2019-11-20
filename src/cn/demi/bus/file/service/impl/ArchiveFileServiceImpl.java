package cn.demi.bus.file.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.core.framework.common.page.QueryCondition;
import cn.core.framework.common.service.BaseServiceImpl;
import cn.core.framework.exception.GlobalException;
import cn.core.framework.utils.DateUtils;
import cn.demi.bus.file.dao.IArchiveDao;
import cn.demi.bus.file.dao.IArchiveFileDao;
import cn.demi.bus.file.po.ArchiveFile;
import cn.demi.bus.file.service.IArchiveFileService;
import cn.demi.bus.file.vo.ArchiveFileVo;

@Service("bus.archiveFileService")
public class ArchiveFileServiceImpl extends BaseServiceImpl<ArchiveFileVo,ArchiveFile> implements
		IArchiveFileService {
 
	@Autowired 
	private IArchiveDao archiveDao;
	@Autowired 
	private IArchiveFileDao archiveFileDao;
	
	@Override
	public void add(ArchiveFileVo v) throws GlobalException {
		ArchiveFile p = vo2Po(v);
		p.setArchive(archiveDao.findById(v.getArchiveVo().getId()));
		archiveFileDao.add(p);
		v.setId(p.getId());
	}
	
	@Override
	public void update(ArchiveFileVo v) throws GlobalException {
		ArchiveFile p = archiveFileDao.findById(v.getId());
		p.setType(v.getType());
		archiveFileDao.update(p);
	}
	
	@Override
	public List<ArchiveFileVo> list(ArchiveFileVo v) throws GlobalException {
		return super.list(v);
	}
	@Override
	public List<QueryCondition> toQueryList(ArchiveFileVo v) throws GlobalException {
		return super.toQueryList(v);
	}

	@Override
	public void update4File(ArchiveFileVo v) throws GlobalException {
		ArchiveFile p = archiveFileDao.findById(v.getId());
		p.setFileName(v.getFileName());
		p.setFilePath(v.getFilePath());
		p.setType(v.getType());
		p.setOriginalSize(v.getOriginalSize());
		p.setSize(v.getSize());
		p.setTime(DateUtils.getCurrDateTimeStr());
		archiveFileDao.update(p);
		
	}
}
