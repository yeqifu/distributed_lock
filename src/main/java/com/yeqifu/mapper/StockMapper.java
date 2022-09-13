package com.yeqifu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeqifu.pojo.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author: yeqifu-
 * @date: 2022/8/30 21:30
 */
public interface StockMapper extends BaseMapper<Stock> {
    /**
     * 一条sql更新库存
     *
     * @param count 商品数量
     * @param id    主键
     * @return
     */
    @Update("update db_stock set count = count - #{count} where id = #{id} and count >= #{count}")
    int updateStock(@Param("id") Integer id, @Param("count") Integer count);

    /**
     * 使用悲观锁更新库存
     *
     * @param productCode 商品编号
     * @return
     */
    @Select("select * from db_stock where product_code = #{productCode,jdbcType=VARCHAR} for update")
    List<Stock> updateStockForUpdate(@Param("productCode") String productCode);
}
