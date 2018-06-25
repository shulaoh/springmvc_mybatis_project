package com.web.service;

import com.web.data.pojo.Inform;
import com.web.data.pojo.PicDef;
import com.web.utils.Page;
import java.util.List;

public abstract interface IInformService
{
  public abstract List<Inform> getInforms(Page paramPage, String paramString);

  public abstract List<PicDef> selectDefPics(String paramString1, String paramString2);
}