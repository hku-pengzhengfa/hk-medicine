package com.hk.common.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author pengzhengfa
 */
public class RestTemplateUtil {


    public static String selectNftList(String requestUrl, String apiUrl, String address,String apiKey) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("X-API-KEY",apiKey);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String url = requestUrl + apiUrl + address +
                "?erc_type=&show_attribute=true&sort_field=&sort_direction=";
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );
        int status = response.getStatusCodeValue();
        if (status==200){
            String body = response.getBody();
            JSONObject nft = JSONObject.parseObject(body);
            JSONArray nftInfo = nft.getJSONArray("data");
            if (nftInfo==null || nftInfo.size()==0){
                return StringUtils.EMPTY;
            }
        }
        return response.getBody();
    }
}
