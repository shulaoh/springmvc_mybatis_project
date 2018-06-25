package com.web.utils;

public class Result
{
  private int retcode = -1;
  private String retmsg = "";

  public int getRetcode()
  {
    return this.retcode; }

  public void setRetcode(int retcode) {
    this.retcode = retcode; }

  public String getRetmsg() {
    return this.retmsg; }

  public void setRetmsg(String retmsg) {
    this.retmsg = retmsg;
  }
}