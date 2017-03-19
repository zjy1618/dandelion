package com.zjy.dandelion.controller;

import com.zjy.dandelion.entity.Order;
import com.zjy.dandelion.entity.OrderDetail;
import com.zjy.dandelion.page.Pager;
import com.zjy.dandelion.page.PagerHelper;
import com.zjy.dandelion.service.core.ExportService;
import com.zjy.dandelion.service.core.OrderService;
import com.zjy.dandelion.utils.DateTimeUtil;
import com.zjy.dandelion.utils.HttpResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;

@Controller
@RequestMapping("order")
public class OrderController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Resource
    private OrderService orderService;

    @Resource
    private ExportService exportService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(String orderNumber, Date startDate, Date endDate, String status, String mobile,
                       String userName, Model model, Pager<Order> pager) {

        Map<String, Object> param_map = new HashMap<>();
        Map<String, String> pager_map = new HashMap<>();

        if (StringUtils.isNotBlank(orderNumber)) {
            param_map.put("orderNumber", orderNumber);
            pager_map.put("orderNumber", orderNumber);
            model.addAttribute("orderNumber", orderNumber);
        }

        if (startDate != null) {
            param_map.put("startDate", startDate);
            pager_map.put("startDate", DateTimeUtil.getDate(startDate,"yyyy-MM-dd HH:mm:ss"));
            model.addAttribute("startDate", DateTimeUtil.getDate(startDate,"yyyy-MM-dd HH:mm:ss"));
        }

        if (endDate != null) {
            param_map.put("endDate", endDate);
            pager_map.put("endDate", DateTimeUtil.getDate(endDate,"yyyy-MM-dd HH:mm:ss"));
            model.addAttribute("endDate", DateTimeUtil.getDate(endDate,"yyyy-MM-dd HH:mm:ss"));
        }

        if (StringUtils.isNotBlank(mobile)) {
            param_map.put("mobile", mobile);
            pager_map.put("mobile", mobile);
            model.addAttribute("mobile", mobile);
        }

        if (StringUtils.isNotBlank(status)) {
            param_map.put("status", status);
            pager_map.put("status", status);
            model.addAttribute("status", status);
        }

        if (StringUtils.isNotBlank(userName)) {
            param_map.put("userName", userName);
            pager_map.put("userName", userName);
            model.addAttribute("userName", userName);
        }
        orderService.findByPager(param_map, pager);

        Map<String, Object> statInfo = orderService.statOrder();
        model.addAttribute("statInfo", statInfo);

        /**
         * 分页助手信息
         */
        PagerHelper<Order> pagerHelper = new PagerHelper<>();
        pagerHelper.setBaseUrl("list");
        pagerHelper.setPager(pager);
        pagerHelper.setParams(pager_map);
        model.addAttribute("pagerHelper", pagerHelper);
        model.addAttribute("list", pager.getList());

        return "order/list";
    }

    @RequestMapping("/detail/{orderNumber}")
    public String detail(@PathVariable String orderNumber, ModelMap model) {

        Order order = orderService.findOrderByOrderNumber(orderNumber);
        model.put("order", order);

        return "order/detail";
    }

    /**
     * 删除订单
     */
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse deleteOrder(String orderNumber, HttpServletRequest request) {
        HttpResponse response = new HttpResponse();
        Order order = orderService.findOrderByOrderNumber(orderNumber);
        if(order == null || order.getStatus() != Order.Status.UNPAY){
            response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
            response.setStatus(HttpResponse.STATUS_FAIL);
            response.setMessage("删除订单失败");
            return response;
        }


        boolean delete = orderService.deleteOrderByOrderNumber(orderNumber);
        if (delete) {
            response.setCode(HttpResponse.CODE_OK);
            response.setStatus(HttpResponse.STATUS_SUCCESS);
            response.setMessage("删除订单成功");
        } else {
            response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
            response.setStatus(HttpResponse.STATUS_FAIL);
            response.setMessage("删除订单失败");
        }
        return response;
    }

    @RequestMapping(value = "setPaid", method = RequestMethod.POST)
    public String setPaid(RedirectAttributes redirect, Long[] orderId) {

        redirect.addFlashAttribute("active", "display");

        int success = 0;
        try {
            for (Long id : orderId) {
                if (orderService.setPaid(id)) {
                    success++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (success == orderId.length) {
            redirect.addFlashAttribute("msg", success + "条设置支付成功");
        } else {
            redirect.addFlashAttribute("msg", success + "条设置支付成功," + (orderId.length - success) + "条未设置支付成功");
        }

        return "redirect:/order/list";
    }

    /**
     * 导出
     */
    @RequestMapping(value = "export")
    public void export(String orderNumber, Date startDate, Date endDate, String status, String mobile,
                       String userName, HttpServletResponse response){

        String fileName = "订单记录";

        String[] titles = new String[] { "订单号", "下单时间", "状态", "产品数量", "订单金额",
                "手机号","客户名","邮政编码","详细地址","留言",
                "产品名称","产品编码","款式","购买数量", "类别"};


        Map<String, Object> param_map = new HashMap<>();

        if (StringUtils.isNotBlank(orderNumber)) {
            param_map.put("orderNumber", orderNumber);
        }

        if (startDate != null) {
            param_map.put("startDate", startDate);
            String date_s = DateFormatUtils.ISO_DATE_FORMAT.format(startDate);
            fileName += "_" + date_s;
        }

        if (endDate != null) {
            param_map.put("endDate", endDate);
            String date_e = DateFormatUtils.ISO_DATE_FORMAT.format(endDate);
            fileName += "_" + date_e;
        }

        if (StringUtils.isNotBlank(mobile)) {
            param_map.put("mobile", mobile);
        }

        if (StringUtils.isNotBlank(status)) {
            param_map.put("status", status);
        }

        if (StringUtils.isNotBlank(userName)) {
            param_map.put("userName", userName);
        }

        response.setContentType("application/vnd.ms-excel");

        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        try {
            response.addHeader("Content-Disposition", String.format("attachment; filename=\"%s.xlsx\"",
                    new String(fileName.getBytes("gbk"), "iso-8859-1")));

            Workbook workbook = null;
            workbook = new SXSSFWorkbook(500); // keep 1000 rows in memory, exceeding rows will be flushed to disk

            CellStyle cellStyle = workbook.createCellStyle();
//            cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
//            cellStyle.setBorderTop(CellStyle.BORDER_THIN);
//            cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
//            cellStyle.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
            Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 10);
            cellStyle.setFont(font);

            Sheet sheet = workbook.createSheet("订单列表");

            Row row1 = sheet.createRow(1);

            for(int cols=0;cols<titles.length;cols++){
                Cell cell = row1.createCell(cols);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(titles[cols]);//写入内容
            }
            //"订单号", "下单时间", "状态", "产品数量", "订单金额",
            // "手机号","客户名","邮政编码","详细地址","留言",
            // "产品名称","产品编码","款式","购买数量", "类别"
            sheet.setColumnWidth(0, 5000); //设置单元格宽度  订单号
            sheet.setColumnWidth(1, 5000); //设置单元格宽度  下单时间
            sheet.setColumnWidth(2, 2000); //设置单元格宽度  状态
            sheet.setColumnWidth(3, 2000); //设置单元格宽度  产品数量
            sheet.setColumnWidth(4, 4000); //设置单元格宽度  订单金额

            sheet.setColumnWidth(5, 4000); //设置单元格宽度  手机号
            sheet.setColumnWidth(6, 4000); //设置单元格宽度  客户名
            sheet.setColumnWidth(7, 4000); //设置单元格宽度  邮政编码
            sheet.setColumnWidth(8, 8000); //设置单元格宽度  详细地址
            sheet.setColumnWidth(9, 8000); //设置单元格宽度  留言

            sheet.setColumnWidth(10, 6000); //设置单元格宽度  产品名称
            sheet.setColumnWidth(11, 4000); //设置单元格宽度  产品编码
            sheet.setColumnWidth(12, 3000); //设置单元格宽度  款式
            sheet.setColumnWidth(13, 2000); //设置单元格宽度  购买数量
            sheet.setColumnWidth(14, 4000); //设置单元格宽度  类别

            Pager<Order> pager = new Pager<>();
            pager.setPageSize(500);
            Integer pageNumber = 1;
            int row_index = 2;

            do{
                pager.setPageNumber(pageNumber);
                List<Order> list = orderService.findByPager(param_map, pager);
                ////"订单号", "下单时间", "状态", "产品数量", "订单金额", "手机号", "授权号", "产品名称","产品编码","款式","购买数量", "公司", "展厅"
                for (Order order : list) {
                    // 打印明细
                    List<OrderDetail> detaiList = order.getOrderDetails();
                    if(detaiList != null && detaiList.size()>0){

                        for(OrderDetail orderDetail:detaiList){
                            //打印订单明细 -- "产品名称","产品编码","款式","购买数量", "类别"
                            Row row_j_sheet = sheet.createRow(row_index);

                            exportService.createCell(cellStyle, row_j_sheet, 10, orderDetail.getName());
                            exportService.createCell(cellStyle, row_j_sheet, 11, orderDetail.getProductCode());
                            exportService.createCell(cellStyle, row_j_sheet, 12, orderDetail.getProductStyleInfo());
                            exportService.createCell(cellStyle, row_j_sheet, 13, orderDetail.getProductCount());
                            exportService.createCell(cellStyle, row_j_sheet, 14, orderDetail.getProductSortName());
                            row_index += 1;
                        }
                    }

                    //int firstRow, int lastRow, int firstCol, int lastCol
                    CellRangeAddress region=new CellRangeAddress(row_index-detaiList.size(), row_index-1, 0, 0);
                    CellRangeAddress region1=new CellRangeAddress(row_index-detaiList.size(), row_index-1, 1, 1);
                    CellRangeAddress region2=new CellRangeAddress(row_index-detaiList.size(), row_index-1, 2, 2);
                    CellRangeAddress region3=new CellRangeAddress(row_index-detaiList.size(), row_index-1, 3, 3);
                    CellRangeAddress region4=new CellRangeAddress(row_index-detaiList.size(), row_index-1, 4, 4);
                    CellRangeAddress region5=new CellRangeAddress(row_index-detaiList.size(), row_index-1, 5, 5);
                    CellRangeAddress region6=new CellRangeAddress(row_index-detaiList.size(), row_index-1, 6, 6);
                    CellRangeAddress region7=new CellRangeAddress(row_index-detaiList.size(), row_index-1, 7, 7);
                    CellRangeAddress region8=new CellRangeAddress(row_index-detaiList.size(), row_index-1, 8, 8);
                    CellRangeAddress region9=new CellRangeAddress(row_index-detaiList.size(), row_index-1, 9, 9);

                    sheet.addMergedRegion(region);
                    sheet.addMergedRegion(region1);
                    sheet.addMergedRegion(region2);
                    sheet.addMergedRegion(region3);
                    sheet.addMergedRegion(region4);
                    sheet.addMergedRegion(region5);
                    sheet.addMergedRegion(region6);
                    sheet.addMergedRegion(region7);
                    sheet.addMergedRegion(region8);
                    sheet.addMergedRegion(region9);

                    //"订单号", "下单时间", "状态", "产品数量", "订单金额",
                    // "手机号","客户名","邮政编码","详细地址","留言",
                    Row row_j_sheet = sheet.getRow(row_index-detaiList.size());

                    exportService.createCell(cellStyle, row_j_sheet, 0, order.getOrderNumber());

                    exportService.createCell(cellStyle, row_j_sheet, 1, DateTimeUtil.getDate(order.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));

                    exportService.createCell(cellStyle, row_j_sheet, 2, order.getStatusValue());

                    Cell cell3 = row_j_sheet.createCell(3);
                    cell3.setCellValue(order.getProductAllCount());
                    cell3.setCellStyle(cellStyle);

                    Cell cell4 = row_j_sheet.createCell(4);
                    cell4.setCellValue(order.getAmount().doubleValue());
                    cell4.setCellStyle(cellStyle);
                    exportService.createCell(cellStyle, row_j_sheet, 5, order.getMobile());
                    exportService.createCell(cellStyle, row_j_sheet, 6, order.getUserName());
                    exportService.createCell(cellStyle, row_j_sheet, 7, order.getPostCode());
                    exportService.createCell(cellStyle, row_j_sheet, 8, order.getAddress());
                    exportService.createCell(cellStyle, row_j_sheet, 9, order.getNote());

                    row_index += 1;
                }



                if(pager.isNext()){
                    pageNumber++;
                }
                pager.setList(null);
            } while (pager.isNext());

            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);

            outputStream.close();
            workbook.close();
            response.flushBuffer();

        }catch (Exception e) {
            logger.error("订单导出：" + e.getMessage());
            e.printStackTrace();
        }
    }


}
