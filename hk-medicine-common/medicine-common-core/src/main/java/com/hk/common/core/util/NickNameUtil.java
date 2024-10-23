package com.hk.common.core.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pengzhengfa
 */
public class NickNameUtil {

    private static final String salt = "**";

    private static final String characters = "ABCDEF0123456789";

    private static final Integer prefixLength = 3;

    private static final Integer totalLength = 5;

    private static final SecureRandom random = new SecureRandom();

    public static List<String> randomNicknameList(Integer num) {
        List<String> nicknameList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            StringBuilder nickname = new StringBuilder();
            if (random.nextBoolean()) {
                for (int j = 0; j < prefixLength; j++) {
                    int index = random.nextInt(characters.length());
                    nickname.append(characters.charAt(index));
                }
            } else {
                nickname.append("0x");
                int index = random.nextInt(characters.length());
                nickname.append(characters.charAt(index));
            }
            nickname.append(salt);
            if (nickname.length() > totalLength) {
                nickname.setLength(totalLength);
            }
            nicknameList.add(nickname.toString());
        }
        return nicknameList;
    }
}
