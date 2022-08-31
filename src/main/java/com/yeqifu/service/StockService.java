package com.yeqifu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yeqifu.mapper.StockMapper;
import com.yeqifu.pojo.Stock;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: yeqifu-
 * @date: 2022/8/30 21:32
 */
@Service
//@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StockService {

    @Resource
    private StockMapper stockMapper;

    ReentrantLock reentrantLock = new ReentrantLock();

    @Transactional(rollbackFor = Exception.class)
    public synchronized void reduce() {
        //reentrantLock.lock();
        try {
            QueryWrapper<Stock> stockQueryWrapper = new QueryWrapper<>();
            stockQueryWrapper.eq("product_code", "1001");
            Stock stock = stockMapper.selectOne(stockQueryWrapper);
            if (null != stock && stock.getCount() > 0) {
                stock.setCount(stock.getCount() - 1);
                stockMapper.updateById(stock);
            }
        } finally {
            //reentrantLock.unlock();
        }
    }

}
