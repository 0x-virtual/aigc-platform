package org.virtual.common.core.factory;

import cn.hutool.core.lang.PatternPool;
import org.virtual.common.core.constant.RegexConstants;

import java.util.regex.Pattern;


public class RegexPatternPoolFactory extends PatternPool {

    /**
     * 字典类型必须以字母开头，且只能为（小写字母，数字，下滑线）
     */
    public static final Pattern DICTIONARY_TYPE = get(RegexConstants.DICTIONARY_TYPE);

    /**
     * 身份证号码（后6位）
     */
    public static final Pattern ID_CARD_LAST_6 = get(RegexConstants.ID_CARD_LAST_6);

    /**
     * QQ号码
     */
    public static final Pattern QQ_NUMBER = get(RegexConstants.QQ_NUMBER);

    /**
     * 邮政编码
     */
    public static final Pattern POSTAL_CODE = get(RegexConstants.POSTAL_CODE);

    /**
     * 注册账号
     */
    public static final Pattern ACCOUNT = get(RegexConstants.ACCOUNT);

    /**
     * 密码：包含至少8个字符，包括大写字母、小写字母、数字和特殊字符
     */
    public static final Pattern PASSWORD = get(RegexConstants.PASSWORD);

    /**
     * 通用状态（0表示正常，1表示停用）
     */
    public static final Pattern STATUS = get(RegexConstants.STATUS);

}
