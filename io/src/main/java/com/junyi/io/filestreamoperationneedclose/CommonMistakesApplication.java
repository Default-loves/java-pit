package com.junyi.io.filestreamoperationneedclose;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.*;

/**
 * 使用Files类静态方法进行文件操作注意释放文件句柄
 */
@Slf4j
public class CommonMistakesApplication {

    public static void main(String[] args) throws IOException {
        init();
//        readLargeFileWrong();
//        readLargeFileRight();
//        wrong();
    }

    /**
     * 对于大文件，Files.readAllLines 会一次性读取到内存中，容易OOM
     * @throws IOException
     */
    private static void readLargeFileWrong() throws IOException {
        log.info("lines {}", Files.readAllLines(Paths.get("large.txt")).size());
    }

    /**
     * 读取大文件的正确方式
     * @throws IOException
     */
    private static void readLargeFileRight() throws IOException {
        AtomicLong atomicLong = new AtomicLong();
        Files.lines(Paths.get("large.txt")).forEach(line -> atomicLong.incrementAndGet());
        log.info("lines {}", atomicLong.get());
    }

    /**
     * Files.lines 是按需读取文件，而不是一次性读取到内存，不过使用这个有个问题，文件的句柄不会自动关闭
     */
    private static void linesTest() throws IOException {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start("read 200000 lines");
        log.info("lines {}", Files.lines(Paths.get("large.txt")).limit(200000).collect(Collectors.toList()).size());
        stopWatch.stop();

        stopWatch.start("read 2000000 lines");
        log.info("lines {}", Files.lines(Paths.get("large.txt")).limit(2000000).collect(Collectors.toList()).size());
        stopWatch.stop();

        log.info(stopWatch.prettyPrint());
    }

    private static void init() throws IOException {

        String payload = IntStream.rangeClosed(1, 1000)
                .mapToObj(__ -> "a")
                .collect(Collectors.joining("")) + UUID.randomUUID().toString();
        Files.deleteIfExists(Paths.get("large.txt"));
        IntStream.rangeClosed(1, 10).forEach(__ -> {
            try {
                Files.write(Paths.get("large.txt"),
                        IntStream.rangeClosed(1, 500000).mapToObj(i -> payload).collect(Collectors.toList())
                        , UTF_8, CREATE, APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Files.write(Paths.get("demo.txt"),
                IntStream.rangeClosed(1, 10).mapToObj(i -> UUID.randomUUID().toString()).collect(Collectors.toList())
                , UTF_8, CREATE, TRUNCATE_EXISTING);
    }

    /**
     * 会存在大量的没有关闭的句柄，会报错：Too many open files
     */
    private static void wrong() {
        //ps aux | grep CommonMistakesApplication
        //lsof -p 63937
        LongAdder longAdder = new LongAdder();
        IntStream.rangeClosed(1, 1000000).forEach(i -> {
            try {
                Files.lines(Paths.get("demo.txt")).forEach(line -> longAdder.increment());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        log.info("total : {}", longAdder.longValue());
    }

    /**
     * 应该使用try-with-resource来使用 Files.lines，使其自动关闭句柄
     */
    private static void right() {
        //https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html
        LongAdder longAdder = new LongAdder();
        IntStream.rangeClosed(1, 1000000).forEach(i -> {
            try (Stream<String> lines = Files.lines(Paths.get("demo.txt"))) {
                lines.forEach(line -> longAdder.increment());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        log.info("total : {}", longAdder.longValue());
    }
}

