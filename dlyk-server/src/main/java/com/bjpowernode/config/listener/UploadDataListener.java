package com.bjpowernode.config.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.bjpowernode.dao.TClueDao;
import com.bjpowernode.domain.po.TClue;
import com.bjpowernode.util.JSONUtils;
import com.bjpowernode.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Slf4j
public class UploadDataListener implements ReadListener<TClue> {

    /**
     * 这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     * Dao没有初始化，在创建UploadDataListener这个监听器的时候，通过构造方法对TClueDao进行初始化
     */
    private TClueDao tClueDao;

    //请求token
    private String token;

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    /**
     * 读取出来的BATCH_COUNT = 100条数据，临时放在该List中
     */
    private List<TClue> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param tClueDao
     */
    public UploadDataListener(TClueDao tClueDao, String token) {
        this.tClueDao = tClueDao;
        this.token = token;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. It is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(TClue data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSONUtils.toJSON(data));

        data.setCreateTime(new Date()); //创建时间
        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseJWTByUserId(token);
        data.setCreateBy(loginUserId); //创建人

        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            // 如果缓存的List中数据条数大于BATCH_COUNT=100了，就把list中的数据往数据库插入一下
            saveData();
            // 往数据库插入完成清理List
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后List中遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        tClueDao.insertClueByBatch(cachedDataList);
        log.info("存储数据库成功！");
    }
}
