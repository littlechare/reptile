package com.advance.reptile.mongoDb.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * MongoDb章节
 */
@Document
@Data
public class ChapterMogo implements Serializable{

//    @Id
//    private String id;

    @Field("title")
    private String title;//章节标题

    @Field("txt")
    private String txt;//内容

    @Field("url")
    private String url;//连接

    @Field("charpterNum")
    private Integer charpterNum; // 章节数

    @Field("dataStatus")
    private String dataStatus;//数据状态

    @Field("chapterId")
    private String chapterId; //对应的Mysql章节ID

    @Field("preId")
    private String preId; //上一节ID

    @Field("preTitle")
    private String preTitle; //上一节标题

    @Field("nextId")
    private String nextId;//下一节ID

    @Field("nextTitle")
    private String nextTitle;//下一节标题

    @Field("dataTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataTime;//数据操作时间

}
