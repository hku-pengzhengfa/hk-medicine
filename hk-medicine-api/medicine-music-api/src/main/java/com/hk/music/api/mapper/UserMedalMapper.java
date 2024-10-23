package com.hk.music.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.music.api.entity.UserMedal;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Set;

/**
 * @author pengzhengfa
 */
public interface UserMedalMapper extends BaseMapper<UserMedal> {

    /**
     * 查询用户勋章数据集合
     *
     * @param total
     * @return
     */
    List<Long> selectUserMedalList(@Param("total") Integer total,@Param("userIdList") Set<Long> userIdList);

    /**
     * 查询用户勋章分页列表
     */
    IPage<UserMedal> selectUserMedalPage(@Param("page") IPage<UserMedal> page,
                                                   @Param("userId") Long userId);
}