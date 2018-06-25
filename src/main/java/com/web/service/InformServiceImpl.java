package com.web.service;

import com.web.data.mapper.InformMapper;
import com.web.data.pojo.Inform;
import com.web.data.pojo.PicDef;
import com.web.utils.Page;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("informService")
public class InformServiceImpl
  implements IInformService
{

  @Resource
  private InformMapper informMapper;

  public List<Inform> getInforms(Page page, String userId)
  {
    HashMap map = new HashMap();
    map.put("page", page);
    map.put("userId", userId);
    return this.informMapper.selectInformsByUserIdListPage(map);
  }

  public List<PicDef> selectDefPics(String picType, String subType) {
    HashMap map = new HashMap();
    map.put("picType", picType);
    map.put("subType", subType);
    return this.informMapper.selectDefPics(map);
  }
}