package com.hk.music.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hk.music.api.entity.MusicInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pengzhengfa
 */
public interface MusicInfoMapper extends BaseMapper<MusicInfo> {

    List<MusicInfo> selectMusicInfoListToLikeNum();

    IPage<MusicInfo> selectSingerMusicPage(@Param("page") Page<MusicInfo> page,@Param("singerId") Long singerId,@Param("userId") Long userId);

    IPage<MusicInfo> selectMusicHistoryPage(@Param("page") Page<MusicInfo> page,@Param("userId") Long userId);
}
