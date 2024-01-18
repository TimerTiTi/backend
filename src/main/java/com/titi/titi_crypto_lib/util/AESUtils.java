package com.titi.titi_crypto_lib.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.titi.titi_crypto_lib.constant.AESCipherModes;
import com.titi.titi_crypto_lib.constant.CryptoAlgorithms;
import com.titi.titi_crypto_lib.exception.TiTiCryptoException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AESUtils {

	private static final int IV_LENGTH = 16;
	private static final int GCM_TAG_LENGTH = 128;

	public static byte[] encrypt(byte[] key, byte[] data, AESCipherModes cipherMode) throws TiTiCryptoException {
		validateParameters(key, data);
		final SecretKeySpec secretKey = new SecretKeySpec(key, CryptoAlgorithms.AES.name());
		return cryptoCore(secretKey, data, Cipher.ENCRYPT_MODE, cipherMode);
	}

	public static byte[] decrypt(byte[] key, byte[] data, AESCipherModes cipherMode) throws TiTiCryptoException {
		validateParameters(key, data);
		final SecretKeySpec secretKey = new SecretKeySpec(key, CryptoAlgorithms.AES.name());
		return cryptoCore(secretKey, data, Cipher.DECRYPT_MODE, cipherMode);
	}

	private static void validateParameters(byte[] key, byte[] data) {
		if (key == null || key.length == 0) {
			throw new TiTiCryptoException("key must not be null or empty.");
		} else if (data == null || data.length == 0) {
			throw new TiTiCryptoException("data must not be null or empty.");
		}
	}

	private static byte[] cryptoCore(Key key, byte[] data, int cryptoMode, AESCipherModes cipherMode) {
		try {
			final Cipher cipher = Cipher.getInstance(cipherMode.getTransformation());
			final String hexStringKey = ConvertUtils.byteArrayToHexString(key.getEncoded());
			final byte[] iv = hexStringKey.substring(0, IV_LENGTH).getBytes();
			AlgorithmParameterSpec algorithmParameterSpec = switch (cipherMode) {
				case AESCipherModes.GCM_NO_PADDING -> new GCMParameterSpec(GCM_TAG_LENGTH, iv);
				case AESCipherModes.CTR_NO_PADDING -> new IvParameterSpec(iv);
			};
			cipher.init(cryptoMode, key, algorithmParameterSpec);
			return cipher.doFinal(data);
		} catch (
			InvalidParameterException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
			InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e
		) {
			throw new TiTiCryptoException("Invalid data is used for AES encryption or decryption.", e);
		}
	}

}
