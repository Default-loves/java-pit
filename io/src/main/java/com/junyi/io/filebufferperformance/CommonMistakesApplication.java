package com.junyi.io.filebufferperformance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.*;

/**
 * BufferedInputStream 和 BufferedOutputStream 内部实现了一个默认 8KB 大小的缓冲区，在使用的时候还是要配置一个额外的缓冲区
 */
@Slf4j
public class CommonMistakesApplication {

    public static void main(String[] args) throws IOException {

        StopWatch stopWatch = new StopWatch();
        init();

//        stopWatch.start("perByteOperation");
//        perByteOperation();
//        stopWatch.stop();

//        stopWatch.start("bufferOperationWith100Buffer");
//        bufferOperationWith100Buffer();
//        stopWatch.stop();

        stopWatch.start("bufferedStreamByteOperation");
        bufferedStreamByteOperation();
        stopWatch.stop();

        stopWatch.start("bufferedStreamBufferOperation");
        bufferedStreamBufferOperation();
        stopWatch.stop();

        stopWatch.start("largerBufferOperation");
        largerBufferOperation();
        stopWatch.stop();

        stopWatch.start("fileChannelOperation");
        fileChannelOperation();
        stopWatch.stop();

        log.info(stopWatch.prettyPrint());
    }

    private static void init() throws IOException {

        Files.write(Paths.get("src.txt"),
                IntStream.rangeClosed(1, 1000000).mapToObj(i -> UUID.randomUUID().toString()).collect(Collectors.toList())
                , UTF_8, CREATE, TRUNCATE_EXISTING);
    }

    /**
     * 每读取一个字节、每写入一个字节都进行一次 IO 操作，性能最差
     * @throws IOException
     */
    private static void perByteOperation() throws IOException {
        Files.deleteIfExists(Paths.get("dest.txt"));

        try (FileInputStream fileInputStream = new FileInputStream("src.txt");
             FileOutputStream fileOutputStream = new FileOutputStream("dest.txt")) {
            int i;
            while ((i = fileInputStream.read()) != -1) {
                fileOutputStream.write(i);
            }
        }
    }

    /**
     * 添加一个 100 字节的缓冲区，第五快
     * @throws IOException
     */
    private static void bufferOperationWith100Buffer() throws IOException {
        Files.deleteIfExists(Paths.get("dest.txt"));

        try (FileInputStream fileInputStream = new FileInputStream("src.txt");
             FileOutputStream fileOutputStream = new FileOutputStream("dest.txt")) {
            byte[] buffer = new byte[100];
            int len = 0;
            while ((len = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
        }
    }

    /**
     * 使用BufferedInputStream，其自带了缓冲区，没有使用额外的缓冲区，第四快
     */
    private static void bufferedStreamByteOperation() throws IOException {
        Files.deleteIfExists(Paths.get("dest.txt"));

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("src.txt"));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("dest.txt"))) {
            int i;
            while ((i = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(i);
            }
        }
    }

    /**
     * 添加一个 8KB 的缓冲区，速度是第二快
     * @throws IOException
     */
    private static void largerBufferOperation() throws IOException {
        Files.deleteIfExists(Paths.get("dest.txt"));

        try (FileInputStream fileInputStream = new FileInputStream("src.txt");
             FileOutputStream fileOutputStream = new FileOutputStream("dest.txt")) {
            byte[] buffer = new byte[8192];
            int len = 0;
            while ((len = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
        }
    }

    /**
     * 和 largerBufferOperation 性能一样，速度第三快
     * @throws IOException
     */
    private static void bufferedStreamBufferOperation() throws IOException {
        Files.deleteIfExists(Paths.get("dest.txt"));

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("src.txt"));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("dest.txt"))) {
            byte[] buffer = new byte[8192];
            int len = 0;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, len);
            }
        }
    }

    /**
     * 在一些操作系统（比如高版本的 Linux 和 UNIX）上可以实现 DMA（直接内存访问），也就是数据从磁盘经过总线直接发送到目标文件，无需经过内存和 CPU 进行数据中转
     * 速度最快
     */
    private static void fileChannelOperation() throws IOException {
        Files.deleteIfExists(Paths.get("dest.txt"));

        FileChannel in = FileChannel.open(Paths.get("src.txt"), StandardOpenOption.READ);
        FileChannel out = FileChannel.open(Paths.get("dest.txt"), CREATE, WRITE);
        in.transferTo(0, in.size(), out);
    }
}

