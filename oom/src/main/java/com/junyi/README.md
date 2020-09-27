### OOM Reason:

1. Tomcat parameter configuration is unreasonable
2. Too many same object in memory
3. Wrong to use WeakHashMap

### When OOM

我们可以根据错误日志中的异常信息，再结合 jstat 等命令行工具观察内存使用情况，以及程序的 GC 日志，来大致定位出现 OOM 的内存区块和类型。其实，我们遇到的 90% 的 OOM 都是堆 OOM，对 JVM 进程进行堆内存 Dump，或使用 jmap 命令分析对象内存占用排行，一般都可以很容易定位到问题。

建议为生产系统的程序配置 JVM 参数启用详细的 GC 日志，方便观察垃圾收集器的行为，并开启 HeapDumpOnOutOfMemoryError，以便在出现 OOM 时能自动 Dump 留下第一问题现场。可以这样设置：

```
XX:+HeapDumpOnOutOfMemoryError 
-XX:HeapDumpPath=. 
-XX:+PrintGCDateStamps 
-XX:+PrintGCDetails 
-Xloggc:gc.log 
-XX:+UseGCLogFileRotation 
-XX:NumberOfGCLogFiles=10 
-XX:GCLogFileSize=100M
```
