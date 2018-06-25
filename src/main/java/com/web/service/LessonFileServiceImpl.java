package com.web.service;

import com.web.data.mapper.LessonFileMapper;
import com.web.data.pojo.LessionFile;
import com.web.utils.Page;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("lessonFileService")
public class LessonFileServiceImpl
  implements ILessonFileService
{

  @Resource
  private LessonFileMapper lessonFileMapper;

  public List<LessionFile> selectLessonFileListPage(Page page, String lessonId, String schid)
  {
    HashMap map = new HashMap();
    map.put("page", page);
    map.put("lessonId", lessonId);
    if ((schid != null) && (schid.trim().length() > 0))
    {
      map.put("schid", schid);
    }
    return this.lessonFileMapper.selectLessonFileListPage(map);
  }

  public int insert(LessionFile file)
  {
    return this.lessonFileMapper.insert(file);
  }
}