package com.hk.music.api.service;

import com.hk.music.api.entity.MusicArtist;

/**
 * @author pengzhengfa
 */
public interface MusicArtistService {

    MusicArtist selectSingerInfo(Long singerId);
}
