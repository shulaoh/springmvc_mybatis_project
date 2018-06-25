package com.web.form;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

public class UserForm
  implements Serializable
{
  private String userName;
  private String password;
  private MultipartFile image;
  private String fileDesc;

  public String getUserName()
  {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public MultipartFile getImage() {
    return this.image;
  }

  public void setImage(MultipartFile image) {
    this.image = image;
  }

  public String getFileDesc() {
    return this.fileDesc;
  }

  public void setFileDesc(String fileDesc) {
    this.fileDesc = fileDesc;
  }
}