package org.zxtc.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {

    private PinyinUtil(){
        
    }
    
    public static String getPingYin(String inputString) {  
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();  
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        format.setVCharType(HanyuPinyinVCharType.WITH_V);  
        String output = "";  
        if (inputString != null && inputString.length() > 0  
                && !"null".equals(inputString)) {  
            char[] input = inputString.trim().toCharArray();  
            try {  
                for (int i = 0; i < input.length; i++) {  
                    if (java.lang.Character.toString(input[i]).matches(  
                            "[\\u4E00-\\u9FA5]+")) {  
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(  
                                input[i], format);  
                        output += temp[0];  
                    } else  
                        output += java.lang.Character.toString(input[i]);  
                }  
            } catch (BadHanyuPinyinOutputFormatCombination e) {  
                e.printStackTrace();  
            }  
        } else {  
            return "*";  
        }
        return output;  
    }
    
    public static String getPinYinHeadChar(String str) {
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母

            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }
}
