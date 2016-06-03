package com.frama.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class that maps the return used after exception raising.
 */
public class ErrorInfo {

   private String errCode;
   
   private String errMsg;
   
   private String errTime;

   public String getErrCode() {
      return errCode;
   }

   public void setErrCode(String errCode) {
      this.errCode = errCode;
   }

   public String getErrMsg() {
      return errMsg;
   }

   public void setErrMsg(String errMsg) {
      this.errMsg = errMsg;
   }

   public String getErrTime() {
      return errTime;
   }

   public void setErrTime(String errTime) {
      this.errTime = errTime;
   }

   public ErrorInfo(String errCode, String errMsg) {
      this.errCode = errCode;
      this.errMsg = errMsg;
      this.errTime = 
            new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
   }
}
