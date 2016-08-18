package com.github.hualuomoli.extend.file.dealer;

import com.github.hualuomoli.extend.file.service.FileService.Dealer;
import com.github.hualuomoli.extend.file.service.FileService.DealerAdaptor;

public abstract class FileUploadDealerAdaptor implements FileUploadDealer {

	@Override
	public Dealer getDealer() {
		return new DealerAdaptor();
	}

}
