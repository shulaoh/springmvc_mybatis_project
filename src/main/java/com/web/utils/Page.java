package com.web.utils;

import org.apache.log4j.Logger;

public class Page
{
  private int showCount;
  private int totalPage;
  private int totalResult;
  private int currentPage;
  private int currentResult;
  private static Logger logger = Logger.getLogger(Page.class);

  public Page()
  {
    try
    {
      this.showCount = Integer.parseInt(Tools.readTxtFile("admin/config/PAGE.txt"));
    } catch (Exception e) {
      this.showCount = 15;
    }
  }

  public boolean caculatePageing()
  {
    this.totalPage = getTotalPage();

    return ((this.totalPage != 0) && (this.totalPage != getCurrentPage()));
  }

  public int getTotalPage()
  {
    if (this.totalResult % this.showCount == 0)
      this.totalPage = (this.totalResult / this.showCount);
    else
      this.totalPage = (this.totalResult / this.showCount + 1);
    return this.totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public int getTotalResult() {
    return this.totalResult;
  }

  public void setTotalResult(int totalResult) {
    this.totalResult = totalResult;
  }

  public int getCurrentPage() {
    if (this.currentPage <= 0)
      this.currentPage = 1;
    if (this.currentPage > getTotalPage())
      this.currentPage = getTotalPage();
    return this.currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getShowCount()
  {
    return this.showCount;
  }

  public void setShowCount(int showCount)
  {
    this.showCount = showCount;
  }

  public int getCurrentResult() {
    this.currentResult = ((getCurrentPage() - 1) * getShowCount());
    if (this.currentResult < 0)
      this.currentResult = 0;
    return this.currentResult;
  }

  public void setCurrentResult(int currentResult) {
    this.currentResult = currentResult;
  }
}