package com.jy.entity.scm.credential;

import java.io.Serializable;
import org.apache.ibatis.type.Alias;
import com.jy.entity.base.BaseEntity;

@Alias("baseCredential")
public class Credential extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/** 主键*/
	private String id;
	/**产品ID */
	private String productId;
	/**辅料ID */
	private String accessorieId;
	/**所在单位ID */
	private String orgId;
	/**状态(0_丢失，1_正常，9_已删除) */
	private String status;
	/**证书编号 */
	private String cerNo;
	/**认证日期 */
	private String cerDate;
	/**证书名称 */
	private String cerName;
	/**认证机构 */
	private String cerofficeId;
	/**认证机构名称 */
	private String cerofficeName;
	/**认证图片*/
	private String filePath;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getAccessorieId() {
		return accessorieId;
	}
	public void setAccessorieId(String accessorieId) {
		this.accessorieId = accessorieId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCerNo() {
		return cerNo;
	}
	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}
	
	public String getCerDate() {
		return cerDate;
	}
	public void setCerDate(String cerDate) {
		this.cerDate = cerDate;
	}
	public String getCerName() {
		return cerName;
	}
	public void setCerName(String cerName) {
		this.cerName = cerName;
	}
	public String getCerofficeId() {
		return cerofficeId;
	}
	public void setCerofficeId(String cerofficeId) {
		this.cerofficeId = cerofficeId;
	}
	public String getCerofficeName() {
		return cerofficeName;
	}
	public void setCerofficeName(String cerofficeName) {
		this.cerofficeName = cerofficeName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
