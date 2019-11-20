package cn.core.framework.utils.output.word2003XmlTemplate;

public class Arab2CnForInt {
	
	public static void main(String[] args) {
		String s = Arab2CnForInt.convert(22);
		System.out.println(s);
	}

	/* 基本数字单位 */
	private static final String[] units = {"千", "百", "十", ""};// 个位
	/* 大数字单位 */
	private static final String[] bigUnits = {"万", "亿"};
	/* 中文数字 */
	private static final char[] numChars = {'一', '二', '三', '四', '五', '六', '七', '八', '九'};
	private static char numZero = '零';

	public static String convert(int num) {
		String cnNum = _convert(num);
		if ((cnNum.length() == 2 || cnNum.length() == 3) && cnNum.startsWith("一十")) {
			return cnNum.substring(1);
		}
		return cnNum;
	}
	
	private static String _convert(int num) {
		String tempNum = num + "";
		// 传说中的分页算法;
		int numLen = tempNum.length();
		int start = 0;
		int end = 0;
		int per = 4;
		int total = (numLen + per - 1) / per;
		int inc = numLen % per;
		/*
		 * 123,1234,1234 四位一段,进行处理;
		 */
		String[] numStrs = new String[total];
		for (int i = total - 1; i >= 0; i--) {
			start = (i - 1) * per + inc;
			if (start < 0) {
				start = 0;
			}
			end = i * per + inc;
			numStrs[i] = tempNum.substring(start, end);
		}
		String tempResultNum = "";
		int rempNumsLen = numStrs.length;
		for (int i = 0; i < rempNumsLen; i++) {
			// 小于1000补零处理;
			if (i > 0 && Integer.parseInt(numStrs[i]) < 1000) {
				tempResultNum += numZero
						+ arabK2CN(Integer.parseInt(numStrs[i]));
			} else {
				tempResultNum += arabK2CN(Integer.parseInt(numStrs[i]));
			}
			// 加上单位(万,亿....)
			if (i < rempNumsLen - 1) {
				tempResultNum += bigUnits[rempNumsLen - i - 2];
			}
		}
		// 去掉未位的零
		tempResultNum = tempResultNum.replaceAll(numZero + "$", "");
		return tempResultNum;
	}

	private static String arabK2CN(int num) {
		char[] numChars = (num + "").toCharArray();
		String tempStr = "";
		int inc = units.length - numChars.length;
		for (int i = 0; i < numChars.length; i++) {
			if (numChars[i] != '0') {
				tempStr += arab2CN(numChars[i]) + units[i + inc];
			} else {
				tempStr += arab2CN(numChars[i]);
			}
		}
		// 将连续的零保留一个
		tempStr = tempStr.replaceAll(numZero + "+", numZero + "");
		// 去掉未位的零
		tempStr = tempStr.replaceAll(numZero + "$", "");
		return tempStr;
	}
	
	private static char arab2CN(char arab) {
		if (arab == '0') {
			return numZero;
		} else if (arab > '0' && arab <= '9') {
			return numChars[arab - '0' - 1];
		} else {
			return arab;
		}
	}
}
