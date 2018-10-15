package com.sc.common.model.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author hp
 * @version 1.0
 * @description: ${description}
 * @date: 11:45 2018/10/15 0015
 */
@Data
@Table(name = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    private Integer id;

    private String nickname;

    private String smallavatar;

    private String avatar;

    private Integer driver;

    private Integer role;

    private Integer status;

    private Integer sex;

    private Integer birthday;

    private Integer exp;

    private Integer expid;

    private Integer scoreid;

    private String antuid;

    private String itucode;

    private String phone;

    private String phonetype;


}
