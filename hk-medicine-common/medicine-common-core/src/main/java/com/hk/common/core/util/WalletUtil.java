package com.hk.common.core.util;

import org.web3j.crypto.*;
import org.web3j.utils.Numeric;
import org.web3j.crypto.Sign.SignatureData;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author pengzhengfa
 */
public class WalletUtil {
    /**
     * 以太坊自定义的签名消息都以以下字符开头
     * 参考 eth_sign in https://github.com/ethereum/wiki/wiki/JSON-RPC
     */
    public static final String PERSONAL_MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";
    public static final String MESSAGE_TMPL = "I am signing my one-time nonce: %s";
    private static final int RECOVER_ITERATIONS = 4;
    private static final int SIGNATURE_BYTE_SIZE = 64;
    private static final int V_OFFSET = 27;

    /**
     * 对签名消息,原始消息,账号地址三项信息进行认证,判断签名是否有效
     *
     * @param signature 签名字符串
     * @param message   原始消息
     * @param address   以太坊地址
     * @return 签名是否有效
     */
    public static boolean validate(String signature, String message, String address) {
        byte[] msgHash = getMessageHash(message);
        SignatureData signatureData = parseSignature(signature);

        // 尝试恢复地址
        for (int i = 0; i < RECOVER_ITERATIONS; i++) {
            String recoveredAddress = recoverAddress(signatureData, msgHash, i);
            if (address.equalsIgnoreCase(recoveredAddress)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 构造签名的消息哈希
     *
     * @param message 原始消息
     * @return 哈希值
     */
    private static byte[] getMessageHash(String message) {
        String prefix = PERSONAL_MESSAGE_PREFIX + message.length();
        return Hash.sha3((prefix + message).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 解析签名字符串为 SignatureData 对象
     *
     * @param signature 签名字符串
     * @return SignatureData 对象
     */
    private static SignatureData parseSignature(String signature) {
        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[SIGNATURE_BYTE_SIZE];
        if (v < V_OFFSET) {
            v += V_OFFSET;
        }
        return new SignatureData(
                v,
                Arrays.copyOfRange(signatureBytes, 0, 32),
                Arrays.copyOfRange(signatureBytes, 32, SIGNATURE_BYTE_SIZE));
    }

    /**
     * 根据签名数据和消息哈希恢复公钥并生成地址
     *
     * @param signatureData 签名数据
     * @param msgHash       消息哈希
     * @param recId         恢复id
     * @return 恢复的以太坊地址
     */
    private static String recoverAddress(SignatureData signatureData, byte[] msgHash, int recId) {
        BigInteger publicKey = Sign.recoverFromSignature(
                (byte) recId,
                new ECDSASignature(new BigInteger(1, signatureData.getR()), new BigInteger(1, signatureData.getS())),
                msgHash);
        return (publicKey != null) ? "0x" + Keys.getAddress(publicKey) : null;
    }
}