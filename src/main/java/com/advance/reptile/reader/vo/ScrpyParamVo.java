package com.advance.reptile.reader.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ScrpyParamVo
 * @Description TODO
 * @Author Lenovo
 * @Date 2019/7/31 17:20
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrpyParamVo {

    private String baseUrl;

    private String author;

    private String name;

    private String startUrl;

}
