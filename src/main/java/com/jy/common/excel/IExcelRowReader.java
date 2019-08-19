package com.jy.common.excel;

import java.util.List;

/**
 * 2017/02/08
 * 
 * @author lisongbai
 *
 */
public interface IExcelRowReader {

	/**
	 * 根据业务逻辑对数据进行处理。
	 * 
	 * @param sheetIndex
	 * @param curRow
	 * @param rowlist
	 */
	void getRows(int sheetIndex, int curRow, List<String> rowlist);

}
