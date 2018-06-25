package com.web.service;

import com.web.data.pojo.FileContent;

public interface IFileContentService {
	
    FileContent getFileContentById(String fileId);
    
    int insert(FileContent fc);
    
    int deleteByFileId(String fileId);
    
    int updateByFileId(FileContent fc);
    
   
}
