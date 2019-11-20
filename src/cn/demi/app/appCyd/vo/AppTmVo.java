package cn.demi.app.appCyd.vo;

public class AppTmVo {
	/**
	 * 条码
	 */
  private String id;
  private String cyDate;
  
  private String imgKey1;
  private String imgKey2;
  private String sampCode;
  private String itemNames;
  private String ly;
  private String gdj;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCyDate() {
    return cyDate;
  }

  public void setCyDate(String cyDate) {
    this.cyDate = cyDate;
  }
 

  public String getImgKey1() {
    return imgKey1;
  }

  public void setImgKey1(String imgKey1) {
    this.imgKey1 = imgKey1;
  }

  public String getImgKey2() {
    return imgKey2;
  }

  public void setImgKey2(String imgKey2) {
    this.imgKey2 = imgKey2;
  }

  public String getSampCode() {
    return sampCode;
  }

  public void setSampCode(String sampCode) {
    this.sampCode = sampCode;
  }

  public String getItemNames() {
    return itemNames;
  }

  public void setItemNames(String itemNames) {
    this.itemNames = itemNames;
  }

  public String getLy() {
    return ly;
  }

  public void setLy(String ly) {
    this.ly = ly;
  }

  public String getGdj() {
    return gdj;
  }

  public void setGdj(String gdj) {
    this.gdj = gdj;
  }
}
