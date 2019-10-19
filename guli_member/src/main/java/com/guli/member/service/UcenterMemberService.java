package com.guli.member.service;

import com.guli.member.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author xiaoluo
 * @since 2019-08-28
 */
public interface UcenterMemberService extends IService<UcenterMember> {

	Integer countRegisterNum(String day);
}
