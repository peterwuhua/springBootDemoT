package cn.demi.base.system.vo;

import java.util.List;

import cn.core.framework.common.vo.Vo;

public class FavoriteVo extends Vo<FavoriteVo> {
	/**
	 * 账户
	 */
	private AccountVo accountVo; // 账户
	/**
	 * 功能
	 */
	private FunctionVo functionVo;// 功能
	/**
	 * 别名
	 */
	private String alias;// 别名
	/**
	 * 是否可用
	 */
	private String isUsed;// 是否可用
	/**
	 * 当权限被去掉时的消息
	 */
	private String message;// 当权限被去掉时的消息
	private List<FavoriteVo> favoriteList;

	public AccountVo getAccountVo() {
		return accountVo;
	}

	public void setAccountVo(AccountVo accountVo) {
		this.accountVo = accountVo;
	}

	public FunctionVo getFunctionVo() {
		return functionVo;
	}

	public void setFunctionVo(FunctionVo functionVo) {
		this.functionVo = functionVo;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<FavoriteVo> getFavoriteList() {
		return favoriteList;
	}

	public void setFavoriteList(List<FavoriteVo> favoriteList) {
		this.favoriteList = favoriteList;
	}

	@Override
	public int hashCode() {
		String functionId = this.getFunctionVo() == null ? "" : this
				.getFunctionVo().getId();
		String accountId = this.getAccountVo() == null ? "" : this
				.getAccountVo().getId();
		return functionId + accountId == "" ? super.hashCode()
				: (functionId + accountId).hashCode();
	}

}
