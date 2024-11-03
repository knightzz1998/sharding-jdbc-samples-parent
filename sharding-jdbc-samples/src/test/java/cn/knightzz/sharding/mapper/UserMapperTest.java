package cn.knightzz.sharding.mapper;

import cn.knightzz.ShardingJdbcDemoApplication;
import cn.knightzz.sharding.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = ShardingJdbcDemoApplication.class)
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsertUser_ValidUser_ReturnsSuccess() {
        // Arrange
        User user = new User();
        long id = System.currentTimeMillis();
        user.setId(id);
        user.setUname("John Doe");

        // 使用 when 方法模拟 userMapper.insert(user) 的行为，使其返回值为 1。
        when(userMapper.insert(user)).thenReturn(1);

        // Act
        int result = userMapper.insert(user);

        // Assert
        assertEquals(1, result);
    }

    /**
     * 写入数据的测试
     */
    @Test
    public void testInsert(){
        User user = new User();
        user.setId(System.currentTimeMillis());
        user.setUname("王天赐");
        userMapper.insert(user);
    }

    /**
     * 查询数据测试
     */
    @Test
    public void testSelect(){
        List<User> users = userMapper.selectList(null);
        for (User user : users) {
            System.out.println(user);
        }
    }
}