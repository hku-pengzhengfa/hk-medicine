package com.hk.music.api.vo;

import com.hk.music.api.entity.MusicInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * @author pengzhengfa
 */
@Data
public class MusicInfoVo extends MusicInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean isFavorite;
}
