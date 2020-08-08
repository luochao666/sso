package com.booway.login_oss.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 罗超
 * @company 杭州声讯科技有限公司
 * @createtime 2020-08-08-14:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)  //开启链式调用
public class User {
    private Integer userId;
    private String userName;
    private String password;
}
