package com.junyi;

import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @time: 2020/8/14 21:16
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@RestController
public class MyController {

    public static final Integer LOOP = 100000;

    @GetMapping("write")
    public String write() {
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synchronizeArrayList = Collections.synchronizedList(new ArrayList<>());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Write into List: CopyOnWriteArrayList");
        IntStream.rangeClosed(1, LOOP).parallel().forEach(__ -> copyOnWriteArrayList.add(ThreadLocalRandom.current().nextInt(LOOP)));
        stopWatch.stop();

        stopWatch.start("Write into List: SynchronizeArrayList");
        IntStream.rangeClosed(1, LOOP).parallel().forEach(__ -> synchronizeArrayList.add(ThreadLocalRandom.current().nextInt(LOOP)));
        stopWatch.stop();

        return stopWatch.prettyPrint();
    }

    private void addList(List<Integer> list) {
        list.addAll(IntStream.rangeClosed(1, LOOP).boxed().collect(Collectors.toList()));
    }

    @GetMapping("read")
    public String read() {
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synchronizeArrayList = Collections.synchronizedList(new ArrayList<>());
        addList(copyOnWriteArrayList);
        addList(synchronizeArrayList);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Read from List: CopyOnWriteArrayList");
        IntStream.rangeClosed(1, LOOP).parallel().forEach(__ -> copyOnWriteArrayList.get(ThreadLocalRandom.current().nextInt(LOOP)));
        stopWatch.stop();

        stopWatch.start("Read from List: SynchronizeArrayList");
        IntStream.rangeClosed(1, LOOP).parallel().forEach(__ -> synchronizeArrayList.get(ThreadLocalRandom.current().nextInt(LOOP)));
        stopWatch.stop();

        return stopWatch.prettyPrint();
    }
}
