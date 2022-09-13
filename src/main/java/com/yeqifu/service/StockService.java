package com.yeqifu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yeqifu.mapper.StockMapper;
import com.yeqifu.pojo.Stock;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
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

    /**
     * 将数据库的隔离级别改成读未提交之后，就可以读到另一个事务未提交的数据，即脏读
     */
    //@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
    public void reduce() {
        /*reentrantLock.lock();
        try {
            // update insert delete本身就会加锁，加的是悲观锁
            // update db_stock set count = count - 1 where id = 1 and count >= 1;
            QueryWrapper<Stock> stockQueryWrapper = new QueryWrapper<>();
            // 查询库存
            stockQueryWrapper.eq("product_code", "1001");
            Stock stock = stockMapper.selectOne(stockQueryWrapper);
            // 判断库存是否充足
            if (null != stock && stock.getCount() > 0) {
                // 更新库存
                stock.setCount(stock.getCount() - 1);
                stockMapper.updateById(stock);
            }
        } finally {
            reentrantLock.unlock();
        }*/

        stockMapper.updateStock(1, 1);

    }

    /**
     * 悲观锁
     */
    @Transactional
    public void reduceTwo() {
        // 1.查询库存
        List<Stock> stockList = this.stockMapper.updateStockForUpdate("1001");
        if (!stockList.isEmpty()) {
            // 2.选定仓库（正常操作是会进行分析的，例如从你当前位置最近的仓库调货）
            Stock stock = stockList.get(0);
            stock.setCount(stock.getCount() - 1);
            // 3.修改库存
            this.stockMapper.updateById(stock);
        }
    }

    /**
     * 乐观锁
     */
    //@Transactional
    public void reduceThree() {
        // 1.查询库存
        List<Stock> stockList = this.stockMapper.selectList(new QueryWrapper<Stock>().eq("product_code", "1001"));
        // 2.取第一个仓库的数据
        Stock stock = stockList.get(0);
        // 3.判断库存是否充足
        if (null != stock && stock.getCount() > 0) {
            // 扣减库存，版本号加一
            Integer version = stock.getVersion();
            stock.setVersion(version + 1);
            stock.setCount(stock.getCount() - 1);
            if (this.stockMapper.update(stock, new UpdateWrapper<Stock>().eq("id", stock.getId()).eq("version", version)) == 0) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reduceThree();
            }
        }

    }

}
