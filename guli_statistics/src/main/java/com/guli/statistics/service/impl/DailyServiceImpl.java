package com.guli.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.common.Result;
import com.guli.statistics.entity.Daily;
import com.guli.statistics.mapper.DailyMapper;
import com.guli.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.statistics.service.feign.MemberServiceFeign;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author xiaoluo
 * @since 2019-08-28
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {
	@Autowired
	private MemberServiceFeign memberServiceFeign;

	@Override
	public boolean createData(String day) {
		Daily daily = new Daily();
		Result result = memberServiceFeign.getRegisterNum(day);
		Integer registerNum = (Integer)result.getData().get("count");

		daily.setRegisterNum(registerNum);
		daily.setCourseNum(RandomUtils.nextInt(100,200));
		daily.setDateCalculated(day);
		daily.setLoginNum(RandomUtils.nextInt(100,200));
		daily.setVideoViewNum(RandomUtils.nextInt(100,200));

		QueryWrapper<Daily> wrapper = new QueryWrapper<>();
		wrapper.eq("date_calculated",day);
		baseMapper.delete(wrapper);
		return baseMapper.insert(daily) == 1;
	}

	@Override
	public Map<String, Object> showData(String begin, String end, String type) {
		QueryWrapper<Daily> wrapper = new QueryWrapper<>();
		wrapper.select("date_calculated",type);
		wrapper.between("date_calculated",begin,end);
		List<Daily> list = baseMapper.selectList(wrapper);
		ArrayList<String> dateList = new ArrayList<>();
		ArrayList<Integer> dataList = new ArrayList<>();
		for (Daily daily : list) {
			dateList.add(daily.getDateCalculated());
			switch (type){
				case "login_num" :
					dataList.add(daily.getLoginNum());
					break;
				case "register_num" :
					dataList.add(daily.getRegisterNum());
					break;
				case "video_view_num" :
					dataList.add(daily.getVideoViewNum());
					break;
				case "course_num" :
					dataList.add(daily.getCourseNum());
					break;
				default:
					break;
			}
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("dataList",dataList);
		map.put("dateList",dateList);
		return map;
	}
}
