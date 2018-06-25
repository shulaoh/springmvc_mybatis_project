package com.web.service;

import com.web.data.mapper.FileContentMapper;
import com.web.data.pojo.FileContent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("fileContentService")
public class FileContentServiceImpl implements IFileContentService {

    @Resource
    private FileContentMapper fileContentMapper;

	@Override
	public FileContent getFileContentById(String fileId) {
		// TODO Auto-generated method stub
		return fileContentMapper.selectByFileId(fileId);
	}

	@Override
	public int insert(FileContent fc) {
		// TODO Auto-generated method stub
		return fileContentMapper.insert(fc);
	}

	@Override
	public int deleteByFileId(String fileId) {
		// TODO Auto-generated method stub
		return this.fileContentMapper.deleteByFileId(fileId);
	}

	@Override
	public int updateByFileId(FileContent fc) {
		// TODO Auto-generated method stub
		return fileContentMapper.updateByFileId(fc);
	}

    
}
