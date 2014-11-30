package org.supercsv.ext;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;


/**
 * @author T.TSUCHIE
 *
 */
public class Utils {
    
    public static String join(final String[] arrays, final String seperator) {
        
        final int len = arrays.length;
        if(arrays == null || len == 0) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < len; i++) {
            sb.append(arrays[i]);
            
            if(seperator != null && (i < len-1)) {
                sb.append(seperator);
            }
        }
        
        return sb.toString();
        
    }
    
    public static String join(final Collection<?> col, final String seperator) {
        
        final int size = col.size();
        if(col == null || size == 0) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        for(Iterator<?> itr = col.iterator(); itr.hasNext();) {
            final Object item = itr.next();
            sb.append(item.toString());
            
            if(seperator != null && itr.hasNext()) {
                sb.append(seperator);
            }
        }
        
        return sb.toString();
        
    }
    
    public static boolean isEmpty(final String value) {
        if(value == null || value.isEmpty()) {
            return true;
        }
        
        return false;
    }
    
    /**
     * ������`���̃��P�[�����I�u�W�F�N�g�ɕϊ�����B
     * <p>�A���_�[�o�[�ŋ�؂���'ja_JP'�𕪉����āALocale�ɓn���B
     * @since 1.2
     * @param str
     * @return ��������̎��̓f�t�H���g���P�[����Ԃ��B
     */
    public static Locale getLocale(final String str) {
        
        if(isEmpty(str)) {
            return Locale.getDefault();
        }
        
        if(!str.contains("_")) {
            return new Locale(str);
        }
        
        final String[] split = str.split("_");
        if(split.length == 2) {
            return new Locale(split[0], split[1]);
            
        } else {
            return new Locale(split[0], split[1], split[2]);
        }
        
    }
}
