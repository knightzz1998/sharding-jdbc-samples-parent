package cn.knightzz.sharding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("t_user")
@Data
public class User {
    private Long id;
    private String uname;
}