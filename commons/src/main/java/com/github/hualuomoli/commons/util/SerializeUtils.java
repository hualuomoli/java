package com.github.hualuomoli.commons.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 序列化和反序列化
 * @author hualuomoli
 *
 */
public class SerializeUtils {

	private static final Logger logger = LoggerFactory.getLogger(SerializeUtils.class);

	private SerializeUtils() {
	}

	// 序列化
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			logger.warn("serialize object error. {}", e);
		}
		return null;
	}

	// 反序列化
	@SuppressWarnings("unchecked")
	public static <T> T unserialize(byte[] bytes) {
		if (bytes == null)
			return null;
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (T) ois.readObject();
		} catch (Exception e) {
			logger.warn("serialize object error. {}", e);
		}
		return null;
	}

	// 克隆
	public static <T, R> R clone(T t) {
		return unserialize(serialize(t));
	}

}
