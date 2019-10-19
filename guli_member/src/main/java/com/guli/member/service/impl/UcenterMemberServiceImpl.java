package com.guli.member.service.impl;

import com.guli.member.entity.UcenterMember;
import com.guli.member.mapper.UcenterMemberMapper;
import com.guli.member.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author xiaoluo
 * @since 2019-08-28
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

	@Override
	public Integer countRegisterNum(String day) {
		Integer count =  baseMapper.countRegisterNum(day);
		return count;
	}
}
