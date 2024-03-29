package com.junyi.io.badencodingissue;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Hex;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 文件读写需要确保字符编码一致
 */
@Slf4j
public class FileBadEncodingIssueApplication {

    public static void main(String[] args) throws IOException {
        init();
        wrong();
        right1();
        right2();
    }

    private static void init() throws IOException {
        System.out.println("===init====");
        Files.deleteIfExists(Paths.get("hello.txt"));
        Files.write(Paths.get("hello.txt"), "你好hi".getBytes(Charset.forName("GBK")));
        log.info("bytes:{}", Hex.encodeHexString(Files.readAllBytes(Paths.get("hello.txt"))).toUpperCase());
    }

    private static void wrong() throws IOException {
        System.out.println("===wrong====");
        log.info("charset: {}", Charset.defaultCharset());  // 默认的字符集是 UTF-8

        char[] chars = new char[10];
        String content = "";
        try (FileReader fileReader = new FileReader("hello.txt")) {     // 这儿使用的是FileReader，其使用当前机器默认字符集来读取文件，而默认的是UTF-8，文件使用的是GBK，结果不正确
            int count;
            while ((count = fileReader.read(chars)) != -1) {
                content += new String(chars, 0, count);
            }
        }
        log.info("result:{}", content);

        Files.write(Paths.get("hello2.txt"), "你好hi".getBytes(Charsets.UTF_8));
        log.info("bytes:{}", Hex.encodeHexString(Files.readAllBytes(Paths.get("hello2.txt"))).toUpperCase());

    }



    private static void right1() throws IOException {
        System.out.println("===right1====");

        char[] chars = new char[10];
        String content = "";
        // 正确的做法是使用 FileInputStream 配合 InputStreamReader 来读取文件
        try (FileInputStream fileInputStream = new FileInputStream("hello.txt");
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, Charset.forName("GBK"))) {
            int count;
            while ((count = inputStreamReader.read(chars)) != -1) {
                content += new String(chars, 0, count);
            }
        }

        log.info("result: {}", content);
    }

    /**
     * 简洁的做法
     * 不过，readAllLines这个方法是一次性读取所有的数据到内存，文件大的时候不适合，而应该使用 Files.lines
     */
    private static void right2() throws IOException {
        System.out.println("===right2====");
        log.info("result: {}", Files.readAllLines(Paths.get("hello.txt"), Charset.forName("GBK")).stream().findFirst().orElse(""));
    }

}


