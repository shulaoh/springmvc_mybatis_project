package com.web.data.mapper;

import com.web.data.pojo.Inform;
import com.web.data.pojo.PicDef;
import java.util.HashMap;
import java.util.List;

public abstract interface InformMapper
{
  public abstract List<Inform> selectInformsByUserIdListPage(HashMap<String, Object> paramHashMap);

  public abstract List<PicDef> selectDefPics(HashMap<String, Object> paramHashMap);
}