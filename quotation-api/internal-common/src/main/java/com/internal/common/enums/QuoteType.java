package com.internal.common.enums;

/**
 * 单位类型
 * 
 * @author every
 */
public enum QuoteType
{
    // 报价类型;[详细报价:COST,粗略报价:ROUGH,无法报价:INCAPABLE]
    COST("COST", "详细报价"),
    ROUGH("ROUGH", "粗略报价"),
    INCAPABLE("INCAPABLE", "无法报价"),
    NONE("NONE", "未知报价");

    private final String code;
    private final String info;

    QuoteType(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    /**
     * 根据code获取枚举类
     * @return
     */
    public static QuoteType getQuoteTypeByCode(String code)
    {
        for (QuoteType type : QuoteType.values())
        {
            if (type.getCode().equals(code))
            {
                return type;
            }
        }
        return NONE;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
