package cn.core.framework.utils.msg;

/**
 * 仪器预约短信模板
 * @author QuJunLong
 *
 */
public class TempInst {

	public static StringBuffer getTemp(String userName,String appName,String times,String reason) {
		StringBuffer msg=new StringBuffer();
		msg.append(userName);
		msg.append("，您在仪器中心预约平台预约的");
		msg.append(appName);
		msg.append("使用时段（");
		msg.append(times);
		msg.append("），因");
		msg.append(reason);
		msg.append("被取消。如有疑问，请电联浙大电镜中心，谢谢！");
		return msg;
	}
}
