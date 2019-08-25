package com.advance.reptile.login.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2019-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String pwd;

    private String phone;

    private String sex;

    private String email;

    private LocalDateTime registerTime;

    private String logDevice;

    private String dataStatus;

    private String openId;

    private String nickName;

    private String avaUrl;

    private String country;

    private String province;

    private String city;

    private String sessionKey;


}
