package com.atguigu.mp;

import com.atguigu.mp.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.atguigu.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectUserList(){
        List<User> users = userMapper.selectList(null);
        users.forEach(System.err::println);

    }

    @Test
    public void insertUser(){
        User user = new User();
        user.setName("赵云");
        user.setAge(32);
        user.setEmail("zhaoyun@qq.com");
        userMapper.insert(user);
        System.out.println(user);
    }

    @Test
    public void updateUser(){
        User user = userMapper.selectById(1161593781269749761L);
        user.setName("赵子龙");
        user.setVersion(user.getVersion() -1);
        userMapper.updateById(user);
    }

    @Test
    public void getUser(){
        User user = userMapper.selectById(1161593781269749761L);
        System.out.println(user);
    }

    @Test
    public void batchUser(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3, 4, 5));
        users.forEach(System.err::println);

    }

    @Test
    public void testSelectByMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","常山赵子龙");
        map.put("age",32);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    @Test
    public void testPagination(){
        Page<User> userPage = new Page<>(1, 3);
        IPage<User> userIPage = userMapper.selectPage(userPage, null);
        System.out.println(userIPage.toString());

    }

    @Test
    public void testDelete(){
        userMapper.deleteById(1161593781269749761L);
    }

    @Test
    public void testWrapper(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.gt("age",18);
        userMapper.selectList(queryWrapper);
    }
}
