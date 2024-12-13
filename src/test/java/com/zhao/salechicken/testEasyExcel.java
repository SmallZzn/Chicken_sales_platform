package com.zhao.salechicken;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhao.salechicken.mapper.EdiMapper;
import com.zhao.salechicken.pojo.Edi;
import com.zhao.salechicken.service.EdiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: 小赵
 * @DateTime: 2024/9/8 21:52
 */
@SpringBootTest
public class testEasyExcel {

    private final ExecutorService executorService = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() << 1,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    @Autowired
    private EdiService ediService;

    @Test
    public void testExport() throws InterruptedException {
//        selectEdi(500);
//        selectEdi(1000);
//        selectEdi(5000);
        selectEdi(10000);
        selectEdi(30000);
//        selectEdi(50000);
    }

    private int count = 600000;
    private int pageSize = 100000;

    public void selectEdi(int num) throws InterruptedException {
        //需要写入的sheet数
        final int sheetNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;

        String fileName = "C:\\Users\\86180\\Desktop\\" + num + ".xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName, Edi.class).build();
        CountDownLatch countDownLatch = new CountDownLatch(sheetNum);

        long start = System.currentTimeMillis();
        for (int i = 0; i < sheetNum; i++) {
            int finalI = i;
            executorService.submit(() -> {
                // 计算一个sheet需要写入多少次
                int lastNum = (count - finalI * pageSize) % num == 0 ? 0 : 1;
                int sheetThreadCount = finalI == (sheetNum - 1) ?
                        (count - finalI * pageSize) / num + lastNum : pageSize / num;
                List<Edi> ediList = new CopyOnWriteArrayList<>();
                CountDownLatch countDownLatch1 = new CountDownLatch(sheetThreadCount);
                for (int j = 0; j < sheetThreadCount; j++) {
                    int finalJ = j;
                    executorService.submit(() -> {
                        List<Edi> list = ediService.selectEdiList(finalI * pageSize + finalJ * num, num);
                        ediList.addAll(list);
                        countDownLatch1.countDown();
                    });
                }
                try {
                    countDownLatch1.await();
                    synchronized (excelWriter) {
                        WriteSheet writeSheet = EasyExcel.writerSheet(finalI, "Sheet" + finalI).build();
                        excelWriter.write(ediList, writeSheet);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        // 千万别忘记finish，会帮忙关闭流
        excelWriter.finish();
        long end = System.currentTimeMillis();
        System.out.println("time" + num + " " + (end - start));

//        EasyExcel.write().registerWriteHandler(new RowWriteHandler() {
//            @Override
//            public void afterRowDispose(RowWriteHandlerContext context) {
//                if (context.getRow().getRowNum() == 3) { // 第四行，索引从0开始
//                    Cell cell = context.getRow().getCell(4); // 第五列，索引从0开始
//                    if (cell == null) {
//                        cell = context.getRow().createCell(4);
//                    }
//                    cell.setCellValue(1);
//                }
//            }
//        })
    }


    @Autowired
    private EdiMapper ediMapper;

    private int size = 10000;

    @Test
    public void testCheckData(){
        String fileName = "C:\\Users\\86180\\Desktop\\" + "校验信息导出表" + ".xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName, Edi.class).build();

        LambdaQueryWrapper<Edi> queryWrapper = Wrappers.lambdaQuery();
        Integer count = ediMapper.selectCount(queryWrapper);

        int num = count / size == 0 ? 1 : count / size;

//        CountDownLatch countDownLatch = new CountDownLatch(num);
        for (int i = 0; i < num; i++) {
            executorService.submit(() -> {
                List<Edi> ediList = ediMapper.selectEdiList((num - 1) * size, size);
//                countDownLatch.countDown();
                synchronized (excelWriter) {
                    EasyExcel.write(fileName, Edi.class).sheet().doWrite(ediList);
                }
            });
        }
    }

//    public void exportTable() {
//        /**
//         * 分sheet页
//         * divide into sheets with 10M data per sheet
//         */
//        int sheetCount = (rowCount / (ROW_SIZE * ROW_PAGE)) + 1;
//        String[] paths = new String[sheetCount];
//        ByteArrayInputStream[] ins = new ByteArrayInputStream[sheetCount];
//
//        CountDownLatch threadSignal = new CountDownLatch(sheetCount);
//        for (int i = 0; i < sheetCount; i++) {
//            int finalI = i;
//            String finalTable = table;
//            Datasource finalDs = ds;
//            String finalOrder = order;
//            int finalRowCount = rowCount;
//            threadExecutor.submit(() -> {
//                // excel文件流
//                ByteArrayOutputStream singleOutputStream = new ByteArrayOutputStream();
//                ExcelWriter excelWriter = EasyExcel.write(singleOutputStream).build();
//                // 单sheet页写入数
//                int sheetThreadCount = finalI == (sheetCount - 1) ? (finalRowCount - finalI * (ROW_SIZE * ROW_PAGE)) / ROW_SIZE + 1 : ROW_PAGE;
//                CountDownLatch sheetThreadSignal = new CountDownLatch(sheetThreadCount);
//                for (int j = 0; j < sheetThreadCount; j++) {
//                    int page = finalI * ROW_PAGE + j + 1;
//                    // 最后一页数据
//                    int pageSize = j == (sheetThreadCount - 1) && finalI == (sheetCount - 1) ? finalRowCount % ROW_SIZE : ROW_SIZE;
//                    threadExecutor.submit(() -> {
//                        try {
//                            writeExcel(dataSetTableRequest, datasetTable, finalTable, qp,
//                                    datasetTableFields, exportTable, page, pageSize, finalDs, datasourceProvider,
//                                    fieldArray, fields, head, map, headRow, excelWriter, mergeIndex, finalOrder);
//                            sheetThreadSignal.countDown();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    });
//                }
//                try {
//                    sheetThreadSignal.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                // 关闭写入流
//                excelWriter.finish();
//                paths[finalI] = (finalI + 1) + "-" + fileName + ".xlsx";
//                // 单文件
//                if (sheetCount == 1) {
//                    // xlsx
//                    // 将sign存入redis并设置过期时间
//                }
//                threadSignal.countDown();
//            });
//        }
//        threadSignal.await();
//    }



}
