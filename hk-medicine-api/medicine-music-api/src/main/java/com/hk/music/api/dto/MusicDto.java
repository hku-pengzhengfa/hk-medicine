package com.hk.music.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author pengzhengfa
 */
@Data
public class MusicDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Integer musicType;

    private Integer pageNo;

    private Integer pageSize;

    private Long singerId;
}
