package com.web.data.mapper;

import com.web.data.pojo.FileContent;
import com.web.data.pojo.LessonAdmin;
import com.web.data.pojo.SysUser;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileContentMapper {
    
    int deleteByFileId(String fileId);
    
    int insert(FileContent fc);

    FileContent selectByFileId(String fileId);
    
    int updateByFileId(FileContent fc);
}
