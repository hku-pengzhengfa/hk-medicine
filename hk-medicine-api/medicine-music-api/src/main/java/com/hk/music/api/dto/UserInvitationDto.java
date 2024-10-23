package com.hk.music.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author pengzhengfa
 */
@Data
public class UserInvitationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pageNo;

    private Integer pageSize;

}
