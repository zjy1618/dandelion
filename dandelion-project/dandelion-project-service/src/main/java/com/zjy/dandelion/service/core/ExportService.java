package com.zjy.dandelion.service.core;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

/**
 * Created by zhuangjy on 16/8/28.
 */
public interface ExportService {
    public void createCell(CellStyle cellStyle, Row row, int position, Object object);
}
