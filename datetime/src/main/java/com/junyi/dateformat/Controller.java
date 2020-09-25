package com.junyi.dateformat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2020/9/25 13:45
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@RestController
public class Controller {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static ThreadLocal<SimpleDateFormat> threadSafeSimpleDateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    private static DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR)
            .appendLiteral("/")
            .appendValue(ChronoField.MONTH_OF_YEAR)
            .appendLiteral("/")
            .appendValue(ChronoField.DAY_OF_MONTH)
            .appendLiteral(" ")
            .appendValue(ChronoField.HOUR_OF_DAY)
            .appendLiteral(":")
            .appendValue(ChronoField.MINUTE_OF_HOUR)
            .appendLiteral(":")
            .appendValue(ChronoField.SECOND_OF_MINUTE)
            .appendLiteral(".")
            .appendValue(ChronoField.MILLI_OF_SECOND)
            .toFormatter();

    @GetMapping("test")
    private  void test() {
        System.out.println("==test==");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        System.out.println(simpleDateFormat.format(calendar.getTime()));
        System.out.println(dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())));
        System.out.println(dateTimeFormatter.format(LocalDateTime.now()));
    }


    /** 三个问题，YYYY、SimpleDateFormat线程不变安全、不合法格式
     *  针对年份的日期格式化，应该一律使用 “y” 而非 “Y”。 */
    @GetMapping("wrong1")
    private  void wrong1() throws ParseException {
        System.out.println("==wrong1==");
        Locale.setDefault(Locale.FRANCE);
        System.out.println("defaultLocale:" + Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.DECEMBER, 29, 0, 0, 0);
        SimpleDateFormat YYYY = new SimpleDateFormat("YYYY-MM-dd");
        System.out.println("格式化: " + YYYY.format(calendar.getTime()));
        System.out.println("weekYear:" + calendar.getWeekYear());
        System.out.println("firstDayOfWeek:" + calendar.getFirstDayOfWeek());
        System.out.println("minimalDaysInFirstWeek:" + calendar.getMinimalDaysInFirstWeek());

        /** illegal string format wrong result */
        String dateString = "20160901";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        System.out.println("result:" + dateFormat.parse(dateString));

    }
    @GetMapping("wrong1fix")
    private  void wrong1fix() throws ParseException {
        System.out.println("==wrong1fix==");
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.DECEMBER, 29, 23, 24, 25);
        System.out.println("格式化: " + yyyy.format(calendar.getTime()));

        String dateString = "20160901";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        System.out.println("result:" + dateFormat.parse(dateString));
    }

    @GetMapping("better")
    private  void better() {
        System.out.println("==better==");
        LocalDateTime localDateTime = LocalDateTime.parse("2020/1/2 12:34:56.789", dateTimeFormatter);
        System.out.println(localDateTime.format(dateTimeFormatter));

        String dt = "20160901";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMM");
        /** DateTimeFormatter parse will call Exception, instead of parsing randomly */
        try {
            System.out.println("result:" + dateTimeFormatter.parse(dt));
        } catch (DateTimeParseException e) {
            System.out.println("parse fail: " + e.getMessage());
        }
    }

    /** simpleDateFormat is not safe in multi thread */
    @GetMapping("wrong2")
    private  void wrong2() throws InterruptedException {
        System.out.println("==wrong2==");
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 20; i++) {
            threadPool.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        System.out.println(simpleDateFormat.parse("2020-01-01 11:12:13"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);

    }

    /** use ThreadLocal */
    @GetMapping("wrong2fix")
    private  void wrong2fix() throws InterruptedException {
        System.out.println("==wrong2fix==");
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 20; i++) {
            threadPool.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        System.out.println(threadSafeSimpleDateFormat.get().parse("2020-01-01 11:12:13"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }
}
