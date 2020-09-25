#### java.time包下的API，推荐使用

LocalDate:表示日期，无时区属性

LocalTime:表示时间，无时区属性

LocalDateTime: LocalDate +LocalTime，无时区属性

lnstant:时间戳，类似Date

ZonedDateTime:表示日期和时间，有时区属性，类似GregorianCalendar(Calendar的一种)

Duration:一段时间

Period:一段日期，基于日历系统的日期区间

DateTimeFormatter:可以格式化日期时间，线程安全

zoneld / ZoneOffset:时区



#### 遗留历史，不建议再使用

Date:时间戳，表示日期和时间，不支持运算，不支持格式化

Calendar:表示日期和时间，支持有限的运算，不支持格式化，有时区属性

SimpleDateFormat:格式化Date，可以用来转化成指定时区，非线程安全

TimeZone:时区