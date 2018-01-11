package com.cherrypicks.tcc.model;

public class HomePage extends BaseModel {
	/**
	 *
	 */
	private static final long serialVersionUID = 6696859396474467482L;

	public enum HomePageType {
		COUPON(1), BANNER(2), GAME(3), CAMPAIGN(4);

		private final int value;

		HomePageType(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	/**  */
	private Long merchantId;

	/**  */
	private Long refId;

	/**  */
	private Integer type;

	/**  */
	private Integer sortOrder;

	private Integer status;

	/**
	 * 获取
	 *
	 * @return
	 */
	public Long getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置
	 *
	 * @param merchantId
	 *
	 */
	public void setMerchantId(final Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public Long getRefId() {
		return refId;
	}

	/**
	 * 设置
	 *
	 * @param refId
	 *
	 */
	public void setRefId(final Long refId) {
		this.refId = refId;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * 设置
	 *
	 * @param type
	 *
	 */
	public void setType(final Integer type) {
		this.type = type;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * 设置
	 *
	 * @param sortOrder
	 *
	 */
	public void setSortOrder(final Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public enum EditStatus {
		ON_LINE(1), DRAFT(0);

		private final int code;

		private EditStatus(final int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		@Override
		public String toString() {
			return String.valueOf(this.code);
		}
	}

	public enum HomePageRefStatus {
		PENDING(1), ACTIVE(2), EXPIRED(3), IN_ACTIVE(4);

		private final int code;

		private HomePageRefStatus(final int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		@Override
		public String toString() {
			return String.valueOf(this.code);
		}
	}

}