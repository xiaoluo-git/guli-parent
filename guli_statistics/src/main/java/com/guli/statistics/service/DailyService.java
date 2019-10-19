package com.guli.statistics.service;

import com.guli.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author xiaoluo
 * @since 2019-08-28
 */
public interface DailyService extends IService<Daily> {

	boolean createData(String day);

	Map<String,Object> showData(String begin, String end, String type);
}
