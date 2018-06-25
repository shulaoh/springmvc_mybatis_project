package com.web.utils;

public class DataDesc
{
  private boolean isPaging = false;
  private Page page = null;

  public boolean isPaging()
  {
    return this.isPaging; }

  public void setPaging(boolean isPaging) {
    this.isPaging = isPaging; }

  public Page getPage() {
    return this.page; }

  public void setPage(Page page) {
    this.page = page;
  }
}