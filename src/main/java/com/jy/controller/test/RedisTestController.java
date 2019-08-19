package com.jy.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.redis.RedisClientTemplate;
import com.jy.controller.base.BaseController;
@RequestMapping("/redistest/")
@Controller
public class RedisTestController extends BaseController {
	
	@Autowired
	RedisClientTemplate redisClientTemplate;

	@RequestMapping("test1")
	@ResponseBody
	public String tets1() {
		redisClientTemplate.setex("test1", 60,"hello world.");
		return redisClientTemplate.get("test1");
	}
	
}
