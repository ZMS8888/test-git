
package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BatchImportThreadProgress {
    //是否结束
    private volatile boolean isStop = false;
    //多个导入请求会同时访问此类，防止多个导入互相更改导入成功订单数量，所以设置为Map类型，
    // 使一个tradeId对应一个导入成功订单数量，tradeId对应successCntMap中的key,表示本次请求导入成功的数量
    private volatile Map<String, Integer> successCntMap = new HashMap<String, Integer>();

    /**
     * 模拟导入
     *
     * @param tradeId
     */
    public void doImport(final String tradeId) {
        try {
            final List<Integer> dataList = new ArrayList<Integer>();
            //模拟导入订单
            int dataSize = 9;
            for (int i = 0; i < dataSize; i++) {
                dataList.add(i);
            }
            //初始化
            successCntMap.put(tradeId, 0);
            //多线程导入
            ExecutorService executorService = Executors.newFixedThreadPool(9);
            int step = 2;
            int cysSum = (dataSize % step == 0 ? dataSize / step : (dataSize / step + 1));
//            System.out.println("循环次数===" + cysSum);
            final CountDownLatch countDownLatch = new CountDownLatch(cysSum);
            long startTime1 = System.currentTimeMillis();
            for (int j = 0; j < dataSize; j = j + step) {
                final int start = j;
                final int perCount = (dataSize - start) < step ? (dataSize - start) : step;
                //            logger.info("n==" + n);
                executorService.execute(new Runnable() {
                    public void run() {
                        try {
//                            System.out.println(new StringBuilder(tradeId).append(" === start == ").append(start).append(" , count").append(perCount).toString());
                            for (int n = 0; n < perCount; n++) {
                                System.out.println(new StringBuilder(tradeId).append(" === 导入数据 == ").append(dataList.get(start + n)).toString());
                                Thread.sleep(300);
                                synchronized (tradeId) {
                                    successCntMap.put(tradeId, successCntMap.get(tradeId) + 1);
                                }
                                //把导入成功的订单数记录到Redis中()
                                //redis.put(tradeId, successCntMap.get(tradeId));
                            }
                            countDownLatch.countDown();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            //等所有线程都结束，才返回结果
            countDownLatch.await();
            System.out.println(new StringBuilder(tradeId).append(" == 线程池循环耗时 === ").append((System.currentTimeMillis() - startTime1))
                    .append(" ， 成功导入数据条数：").append(successCntMap.get(tradeId)).toString());
            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final BatchImportThreadProgress batchImportThreadProgress = new BatchImportThreadProgress();
        //模拟多个（count）用户，同时导入订单
        int count = 2;
        final CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            final String tradeId = "tradeId" + i;
            new Thread(new Runnable() {
                public void run() {
                    batchImportThreadProgress.doImport(tradeId);
                    countDownLatch.countDown();
                }
            }).start();

        }
        //获得导入进度 - -web项目中不需要这样监控，需要单独写一个接口，jsp页面根据tradeId每隔一秒（settime函数）请求一次该接口，
        // 接口从redis里缓存的进度返回给jsp,并刷新结果
        new Thread(new Runnable() {
            public void run() {
                System.out.println("-------获得导入进度--------");
                while (!batchImportThreadProgress.isStop) {
                    try {
                        String tradeId = "";
                        for (int i = 0; i < count; i++) {
                            tradeId = "tradeId" + i;
                            System.out.println("---batchImportThreadProgress---" + tradeId + "---获得导入进度--------" + batchImportThreadProgress.successCntMap.get(tradeId));
                        }
                        Thread.sleep(500);//每隔0.5秒显示一次进度
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //等待线程池结束
        countDownLatch.await();
        batchImportThreadProgress.isStop = true;
    }
}