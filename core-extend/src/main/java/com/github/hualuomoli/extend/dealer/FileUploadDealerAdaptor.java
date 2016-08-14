package com.github.hualuomoli.extend.dealer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.extend.service.FileService.Dealer;
import com.github.hualuomoli.extend.service.FileService.DealerAdaptor;

@Service(value = "com.github.hualuomoli.extend.dealer.FileUploadDealerAdaptor")
@Transactional(readOnly = true)
public abstract class FileUploadDealerAdaptor implements FileUploadDealer {

	@Override
	public Dealer getDealer() {
		return new DealerAdaptor();
	}

}
