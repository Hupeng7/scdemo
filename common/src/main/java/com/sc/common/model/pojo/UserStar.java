package com.sc.common.model.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author hp
 * @time 2018/10/16.
 */
@Data
@Table(name = "user_star")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserStar {
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


}
