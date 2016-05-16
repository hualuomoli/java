package com.github.hualuomoli.tool.creator;

import java.util.List;

import com.github.hualuomoli.tool.creator.dealer.DbDealer;
import com.github.hualuomoli.tool.creator.dealer.FileDealer;
import com.github.hualuomoli.tool.creator.entity.CreatorTable;
import com.github.hualuomoli.tool.creator.util.CreatorUtils;
import com.google.common.collect.Lists;

/**
 * 生成器
 * @author hualuomoli
 *
 */
public class CreatorAdaptor implements Creator {

	private FileDealer fileDealer;
	private DbDealer dbDealer;

	public void setFileDealer(FileDealer fileDealer) {
		this.fileDealer = fileDealer;
	}

	public void setDbDealer(DbDealer dbDealer) {
		this.dbDealer = dbDealer;
	}

	@Override
	public void execute(Class<?>... clses) {

		if (clses == null || clses.length == 0) {
			return;
		}

		List<CreatorTable> tableList = Lists.newArrayList();
		List<Class<?>> clsList = Lists.newArrayList();

		// create file
		for (Class<?> cls : clses) {
			CreatorTable creatorTable = CreatorUtils.getCreatorTable(cls);
			// 执行
			fileDealer.execute(cls, creatorTable);

			clsList.add(cls);
			tableList.add(creatorTable);
		}

		// create DB
		dbDealer.execute(clsList, tableList);

	}

}
