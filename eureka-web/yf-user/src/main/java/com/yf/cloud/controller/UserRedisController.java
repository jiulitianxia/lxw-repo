package com.yf.cloud.controller;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.RestController;

import com.yf.info.UserTable;
import com.yf.redis.RedisKeyUtil;
import com.yf.redis.RedisService;
@RestController
public class UserRedisController {

	@Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ValueOperations<String,Object> valueOperations;

    @Autowired
    private HashOperations<String, String, Object> hashOperations;

    @Autowired
    private ListOperations<String, Object> listOperations;

    @Autowired
    private SetOperations<String, Object> setOperations;

    @Autowired
    private ZSetOperations<String, Object> zSetOperations;

    @Resource
    private RedisService redisService;

	    public void testObj() throws Exception{
	        UserTable userVo = new UserTable();
	        userVo.setUsername("jiulitianxia");
	        userVo.setName("测试dfas");
	        userVo.setAge(123);
	        ValueOperations<String,Object> operations = redisTemplate.opsForValue();
	        redisService.expireKey("name",20, TimeUnit.SECONDS);
	        String key = RedisKeyUtil.getKey(UserTable.Table,"name",userVo.getName());
	        UserTable vo = (UserTable) operations.get(key);
	        System.out.println(vo);
	    }

	    public void testValueOption( )throws  Exception{
	    	UserTable userVo = new UserTable();
	    	userVo.setUsername("lyt");
	        userVo.setName("jantent");
	        userVo.setAge(23);
	        valueOperations.set("test",userVo);

	        System.out.println(valueOperations.get("test"));
	    }

	    
	    public void testSetOperation() throws Exception{
	    	UserTable userVo = new UserTable();
	    	userVo.setUsername("yutian");
	        userVo.setName("jantent");
	        userVo.setAge(23);
	        UserTable auserVo = new UserTable();
	        auserVo.setUsername("tianxia");
	        auserVo.setName("antent");
	        auserVo.setAge(23);
	        setOperations.add("user:test",userVo,auserVo);
	        Set<Object> result = setOperations.members("user:test");
	        System.out.println(result);
	    }

	    

	   
}
