package com.zjy.dandelion.service.core.impl;

import com.zjy.dandelion.service.core.ExportService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

/**
 * Created by zhuangjy on 16/8/28.
 */
@Service
public class ExportServiceImpl implements ExportService {
    @Override
    public void createCell(CellStyle cellStyle, Row row, int position, Object object) {
        Cell cell = row.createCell(position);
        if(object==null){
            object="";
        }
        cell.setCellValue(object.toString());
        cell.setCellStyle(cellStyle);
    }
}
