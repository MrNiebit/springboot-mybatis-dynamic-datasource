package cn.lacknb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Desc:
 * @Author: gitsilence
 * @Date: 2021/1/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String name;
    private String gender;
    private Integer age;

}
