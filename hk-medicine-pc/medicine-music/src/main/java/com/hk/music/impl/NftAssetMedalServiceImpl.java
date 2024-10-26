package com.hk.music.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.common.core.util.RestTemplateUtil;
import com.hk.common.core.util.TimeUtils;
import com.hk.music.api.entity.NftAssetMedal;
import com.hk.music.api.mapper.NftAssetMedalMapper;
import com.hk.music.api.service.NftAssetMedalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengzhengfa
 */
@Slf4j
@Service
public class NftAssetMedalServiceImpl extends ServiceImpl<NftAssetMedalMapper, NftAssetMedal>
        implements NftAssetMedalService {

    @Value("${nft.apiUrl}")
    private String apiUrl;

    @Value("${nft.apiKey}")
    private String apiKey;

    @Value("${nft.domainUrl}")
    private String requestUrl;

    /**
     * 查询用户是否存在nft资产
     * @param walletAddress
     * @param userId
     * @return
     */
    @Override
    public boolean selectNftAssetMedal(String walletAddress, Long userId) {
        QueryWrapper<NftAssetMedal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("wallet_address",walletAddress);
        queryWrapper.eq("user_id",userId);
        List<NftAssetMedal> nftAssetMedalList = baseMapper.selectList(queryWrapper);
        if (nftAssetMedalList !=null && nftAssetMedalList.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public void insertNftAssetMedal(String walletAddress, Long userId) {
        QueryWrapper<NftAssetMedal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        NftAssetMedal info = baseMapper.selectOne(queryWrapper);
        if (info!=null) {
            String selectNftList = RestTemplateUtil.selectNftList(requestUrl, apiUrl, walletAddress, apiKey);
            if (StringUtils.isNotBlank(selectNftList)) {
                JSONObject jsonObject = JSON.parseObject(selectNftList);
                JSONArray dataArray = jsonObject.getJSONArray("data");
                if (dataArray != null && !dataArray.isEmpty()) {
                    JSONObject dataObject = dataArray.getJSONObject(0);
                    JSONArray assetsArray = dataObject.getJSONArray("assets");
                    if (assetsArray != null && !assetsArray.isEmpty()) {
                        String currentTime = TimeUtils.currentTime();
                        NftAssetMedal nftAssetMedal = new NftAssetMedal();
                        nftAssetMedal.setUserId(userId);
                        nftAssetMedal.setWalletAddress(walletAddress);
                        nftAssetMedal.setStatus(1);
                        nftAssetMedal.setCreateTime(currentTime);
                        nftAssetMedal.setUpdateTime(currentTime);
                        save(nftAssetMedal);
                    }
                }
            }
        } else {
            log.error("你的资产信息不存在");
        }
    }
}
