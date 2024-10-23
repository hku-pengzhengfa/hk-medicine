package com.hk.music.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hk.music.api.entity.MusicInfo;
import com.hk.music.api.vo.MusicInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pengzhengfa
 */
public interface MusicInfoMapper extends BaseMapper<MusicInfo> {

    List<MusicInfo> selectMusicInfoListToLikeNum();

    IPage<MusicInfoVo> selectSingerMusicPage(@Param("page") Page<MusicInfoVo> page,@Param("singerId") Long singerId,@Param("userId") Long userId);
}
