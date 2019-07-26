package com.advance.reptile.mongoDb.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 章节
 */
@Document
@Data
public class Chapter implements Serializable{

    @Id
    private String id;

    @Field("title")
    private String title;//章节标题

    @Field("txt")
    private String txt;//内容

    @Field("url")
    private String url;//连接

    @Field("dataStatus")
    private String dataStatus;//数据状态

    @Field("bookId")
    private String bookId;

    @Field("dataTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataTime;//数据操作时间

}
