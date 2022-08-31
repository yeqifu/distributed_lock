package com.yeqifu.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: 落亦-
 * @Date: 2022/8/30 19:49
 */
@TableName(value = "db_stock")
@Data
public class Stock {

    private Long id;

    private String productCode;

    private String warehouse;

    private Integer count;

}
