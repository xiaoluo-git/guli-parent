package com.guli.member.mapper;

import com.guli.member.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author xiaoluo
 * @since 2019-08-28
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

	Integer countRegisterNum(String day);
}
