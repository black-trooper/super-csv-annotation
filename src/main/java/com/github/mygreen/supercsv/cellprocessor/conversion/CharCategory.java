package com.github.mygreen.supercsv.cellprocessor.conversion;


/**
 * 日本語の全角・半角に変換する際の文字の種類を表す列挙型。
 * 
 * @since 2.0
 * @author T.TSUCHIE
 *
 */
public enum CharCategory {
    
    /** 数字 */
    Number,
    
    /** 英字のアルファベット */
    Alpha,
    
    /** 空白 */
    Space,
    
    /** 記号 */
    Symbol,
    
    /** 片仮名 */
    Katakana,
    ;
    
}
