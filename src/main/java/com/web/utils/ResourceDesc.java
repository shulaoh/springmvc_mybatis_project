package com.web.utils;

public class ResourceDesc
{
  private int service_version = 1;
  private String input_charset = "UTF-8";
  private String tid = "";

  public String getTid()
  {
    return this.tid; }

  public void setTid(String tid) {
    this.tid = tid; }

  public int getService_version() {
    return this.service_version; }

  public void setService_version(int service_version) {
    this.service_version = service_version; }

  public String getInput_charset() {
    return this.input_charset; }

  public void setInput_charset(String input_charset) {
    this.input_charset = input_charset;
  }
}