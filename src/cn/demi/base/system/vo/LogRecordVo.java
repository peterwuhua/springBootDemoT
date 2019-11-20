package cn.demi.base.system.vo;

import cn.core.framework.common.vo.Vo;

public class LogRecordVo extends Vo<LogRecordVo> {
	//时间
		private String ctime;
		//IP地址
		private String ip;
		//操作人
		private String user;
		//模块
		private String module;
		//操作
		private String opt;
		//描述
		private String content;
		//操作方法
		private String method;
		//参数
		private String params;
		public String getCtime() {
			return ctime;
		}
		public void setCtime(String ctime) {
			this.ctime = ctime;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getModule() {
			return module;
		}
		public void setModule(String module) {
			this.module = module;
		}
		public String getOpt() {
			return opt;
		}
		public void setOpt(String opt) {
			this.opt = opt;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}
		public String getParams() {
			return params;
		}
		public void setParams(String params) {
			this.params = params;
		}
		
}

