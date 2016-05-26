package com.github.hualuomoli.commons.bmap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.commons.bmap.decode.DecodeParam;
import com.github.hualuomoli.commons.bmap.decode.DecodeParam.Coordtype;
import com.github.hualuomoli.commons.bmap.decode.DecodeParam.Pois;
import com.github.hualuomoli.commons.bmap.decode.DecodeResult;
import com.github.hualuomoli.commons.bmap.encode.EncodeParam;
import com.github.hualuomoli.commons.bmap.encode.EncodeResult;
import com.github.hualuomoli.commons.json.JsonMapper;

public class BMapUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(BMapUtils.class);

	@Test
	public void testEncodeLocation() {
		EncodeParam param = new EncodeParam();
		param.setAddress("四季景园");
		param.setCity("青岛市");
		EncodeResult result = BMapUtils.encodeLocation(param);
		logger.debug("location {}", JsonMapper.toJsonString(result));

	}

	@Test
	public void testDecodeLocation() {
		DecodeParam param = new DecodeParam();
		param.setCoordtype(Coordtype.bd09ll);
		param.setLocation("22.62527778,114.0566667");
		param.setPois(Pois.show);

		DecodeResult result = BMapUtils.decodeLocation(param);
		logger.debug("location {}", JsonMapper.toJsonString(result));
	}

}
