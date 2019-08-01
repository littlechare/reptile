package com.advance.reptile.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrpyNoticMessage implements Serializable {

    private String baseUrl;

    private String startUrl;

    private String bookName;

}
