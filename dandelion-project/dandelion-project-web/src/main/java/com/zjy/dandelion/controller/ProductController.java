package com.zjy.dandelion.controller;

import com.zjy.dandelion.entity.*;
import com.zjy.dandelion.model.ProductExcel;
import com.zjy.dandelion.page.Pager;
import com.zjy.dandelion.page.PagerHelper;
import com.zjy.dandelion.service.core.*;
import com.zjy.dandelion.utils.HttpResponse;
import com.zjy.dandelion.utils.IdGenerator;
import com.zjy.dandelion.utils.ImageTool;
import com.zjy.dandelion.utils.ImportExecl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Resource
    private ProductService productService;

    @Resource
    private ProductSortService productSortService;

    @Resource
    private ProductImgService productImgService;

    @Resource
    private ProductStyleService productStyleService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(String productCode, Long productSortId, String name, String status, Model model, Pager<Product> pager) {

        List<ProductSort> productSort_list = productSortService.findAll();
        model.addAttribute("productSort_list", productSort_list);

        Map<String, Object> param_map = new HashMap<>();
        Map<String, String> pager_map = new HashMap<>();

        if (StringUtils.isNotBlank(productCode)) {
            param_map.put("productCode", productCode);
            pager_map.put("productCode", productCode);
            model.addAttribute("productCode", productCode);
        }

        if (productSortId != null) {
            param_map.put("productSortId", productSortId);
            pager_map.put("productSortId", productSortId.toString());
            model.addAttribute("productSortId", productSortId);
        }

        if (StringUtils.isNotBlank(name)) {
            param_map.put("name", name);
            pager_map.put("name", name);
            model.addAttribute("name", name);
        }

        if (StringUtils.isNotBlank(status)) {
            param_map.put("status", status);
            pager_map.put("status", status);
            model.addAttribute("status", status);
        }

        productService.findByPager(param_map, pager);

        /**
         * 分页助手信息
         */
        PagerHelper<Product> pagerHelper = new PagerHelper<>();
        pagerHelper.setBaseUrl("list");
        pagerHelper.setPager(pager);
        pagerHelper.setParams(pager_map);
        model.addAttribute("pagerHelper", pagerHelper);
        model.addAttribute("list", pager.getList());

        return "product/list";
    }

    @RequestMapping("/update/{productCode}")
    public String update(@PathVariable String productCode, ModelMap model) {

        Product product = productService.findProductByCode(productCode);
        model.addAttribute("product", product);
        model.addAttribute("productStyle_List", product.getProductStyleList());

        List<ProductSort> productSort_list = productSortService.findAll();
        model.addAttribute("productSort_list", productSort_list);

        return "product/update";
    }

    /**
     * 修改产品
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse update(Product product, MultipartFile coverImg, HttpServletRequest request) {
        HttpResponse response = new HttpResponse();
        try {
            if (coverImg != null && coverImg.getSize() > 0) {
                boolean isImg = ImageTool.isImageAllowType(coverImg.getOriginalFilename());
                if(!isImg){
                    response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
                    response.setStatus(HttpResponse.STATUS_FAIL);
                    response.setMessage("图片格式错误");
                    return response;
                }
            }
            boolean add = productService.updateProduct(product);
            if (add) {
                if(coverImg != null && coverImg.getSize() > 0){
                    // 上传成功的绝对路径（不包含文件名）
                    String realPath = request.getSession().getServletContext().getRealPath("product_img/") + File.separator + product.getProductCode() + File.separator;

                    String newFileName = ImageTool.upload(coverImg, realPath, "cover_" + IdGenerator.getProductID());
                    if(StringUtils.isNotBlank(newFileName)){
                        ProductImg img = productImgService.findSmallByProductCode(product.getProductCode());
                        if(img != null){
                            ImageTool.deletePicture(request.getSession().getServletContext().getRealPath(img.getUrl()));
                        }
                        //相对路径
                        String url = File.separator + "product_img" + File.separator + product.getProductCode() + File.separator + newFileName;
                        ProductImg productImg = new ProductImg();
                        productImg.setProductCode(product.getProductCode());
                        productImg.setUrl(url);
                        productImg.setType(ProductImg.Type.SMALL);
                        productImgService.updateProductImg(productImg);
                    } else {
                        //相对路径
                        response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
                        response.setStatus(HttpResponse.STATUS_FAIL);
                        response.setMessage("图片保存失败 ");
                        return response;
                    }
                }
            } else {
                response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
                response.setStatus(HttpResponse.STATUS_FAIL);
                response.setMessage("保存失败");
            }
        } catch (Exception e) {

            logger.error("保存失败", e);

            response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
            response.setStatus(HttpResponse.STATUS_FAIL);
            response.setMessage("保存失败 " + e.getMessage());
        }

        response.setCode(HttpResponse.CODE_OK);
        response.setStatus(HttpResponse.STATUS_SUCCESS);
        response.setMessage("修改成功");
        return response;

    }

    @RequestMapping("/initAdd")
    public String initAdd(Model model) {
        List<ProductSort> productSort_list = productSortService.findAll();
        model.addAttribute("productSort_list", productSort_list);
        return "product/add";
    }

    @RequestMapping("/initImport")
    public String initImport() {
        return "product/import";
    }

    /**
     * 新增产品
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse add(Product product, MultipartFile coverImg,
                            @RequestParam(value = "productStyleIds") Long[] productStyleIds, HttpServletRequest request) {
        HttpResponse response = new HttpResponse();
        try {
            if (coverImg != null && coverImg.getSize() > 0) {
                boolean isImg = ImageTool.isImageAllowType(coverImg.getOriginalFilename());
                if(!isImg){
                    response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
                    response.setStatus(HttpResponse.STATUS_FAIL);
                    response.setMessage("图片格式错误");
                    return response;
                }
            } else {
                response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
                response.setStatus(HttpResponse.STATUS_FAIL);
                response.setMessage("缩略图不许为空!");
                return response;
            }
            boolean add = productService.saveProduct(product);
            if(add){
                for (Long productStyleId : productStyleIds) {
                    if (productStyleId == null) {
                        continue;
                    }
                    ProductStyle productStyle = productStyleService.findProductStyleById(productStyleId);
                    productStyle.setProductCode(product.getProductCode());
                    productStyleService.updateProductStyle(productStyle);
                }
                // 上传成功的绝对路径（不包含文件名）
                String realPath = request.getSession().getServletContext().getRealPath("product_img/") + File.separator + product.getProductCode() + File.separator;

                String newFileName = ImageTool.upload(coverImg, realPath, "cover_" + IdGenerator.getProductID());
                if(StringUtils.isNotBlank(newFileName)){
                    //相对路径
                    String url = File.separator + "product_img" + File.separator + product.getProductCode() + File.separator + newFileName;
                    ProductImg productImg = new ProductImg();
                    productImg.setProductCode(product.getProductCode());
                    productImg.setUrl(url);
                    productImg.setType(ProductImg.Type.SMALL);
                    productImgService.saveProductImg(productImg);
                }
            } else {
                response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
                response.setStatus(HttpResponse.STATUS_FAIL);
                response.setMessage("添加产品失败!");
                return response;
            }

        } catch (Exception e) {
            logger.error("保存失败", e);
        }

        response.setCode(HttpResponse.CODE_OK);
        response.setStatus(HttpResponse.STATUS_SUCCESS);
        response.setMessage("添加成功");
        return response;
    }

    /**
     * 批量开售产品
     * @param redirect
     * @param productId
     * @return
     */
    @RequestMapping(value = "onSellAll", method = RequestMethod.POST)
    public String onSellAll(RedirectAttributes redirect, Long[] productId) {

        redirect.addFlashAttribute("active", "display");

        int success = 0;
        try {
            for (Long id : productId) {
                Product product = productService.findProductById(id);
                if (product != null && product.getStatus() != Product.Status.ONSELL) {
                    boolean update = productService.onSellProduct(product.getProductCode());
                    if(update){
                        success++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (success == productId.length) {
            redirect.addFlashAttribute("msg", success + "个产品开售成功");
        } else {
            redirect.addFlashAttribute("msg", success + "个产品开售成功," + (productId.length - success) + "个产品开售失败");
        }

        return "redirect:/product/list";
    }

    /**
     * 开售产品
     */
    @RequestMapping(value = "/onSell", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse onSell(String productCode) {
        HttpResponse response = new HttpResponse();
        boolean update = productService.onSellProduct(productCode);
        if (update) {
            response.setCode(HttpResponse.CODE_OK);
            response.setStatus(HttpResponse.STATUS_SUCCESS);
            response.setMessage("开售产品成功");
        } else {
            response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
            response.setStatus(HttpResponse.STATUS_FAIL);
            response.setMessage("开售产品失败");
        }
        return response;
    }

    /**
     * 批量停售产品
     * @param redirect
     * @param productId
     * @return
     */
    @RequestMapping(value = "soldOutAll", method = RequestMethod.POST)
    public String soldOutAll(RedirectAttributes redirect, Long[] productId) {

        redirect.addFlashAttribute("active", "display");

        int success = 0;
        try {
            for (Long id : productId) {
                Product product = productService.findProductById(id);
                if (product != null && product.getStatus() == Product.Status.ONSELL) {
                    boolean update = productService.soldOutProduct(product.getProductCode());
                    if(update){
                        success++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (success == productId.length) {
            redirect.addFlashAttribute("msg", success + "个产品停售成功");
        } else {
            redirect.addFlashAttribute("msg", success + "个产品停售成功," + (productId.length - success) + "个产品停售失败");
        }

        return "redirect:/product/list";
    }

    /**
     * 停售产品
     */
    @RequestMapping(value = "/soldOut", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse soldOut(String productCode) {
        HttpResponse response = new HttpResponse();
        boolean update = productService.soldOutProduct(productCode);
        if (update) {
            response.setCode(HttpResponse.CODE_OK);
            response.setStatus(HttpResponse.STATUS_SUCCESS);
            response.setMessage("停售产品成功");
        } else {
            response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
            response.setStatus(HttpResponse.STATUS_FAIL);
            response.setMessage("停售产品失败");
        }
        return response;
    }

    /**
     * 删除产品
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse delete(String productCode, HttpServletRequest request) {
        HttpResponse response = new HttpResponse();
        Product product = productService.findProductByCode(productCode);
        if(product == null || product.getStatus() != Product.Status.CREATE){
            response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
            response.setStatus(HttpResponse.STATUS_FAIL);
            response.setMessage("删除产品失败");
            return response;
        }

        // 产品图片的绝对路径（不包含文件名）
        String realPath = request.getSession().getServletContext().getRealPath("product_img/") + File.separator + product.getProductCode() + File.separator;

        boolean delete = productService.deleteProduct(product, realPath);
        if (delete) {
            response.setCode(HttpResponse.CODE_OK);
            response.setStatus(HttpResponse.STATUS_SUCCESS);
            response.setMessage("删除产品成功");
        } else {
            response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
            response.setStatus(HttpResponse.STATUS_FAIL);
            response.setMessage("删除产品失败");
        }
        return response;
    }

    /**
     * 导入产品
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse importProduct(MultipartFile productFile, HttpServletRequest request){
        HttpResponse response = new HttpResponse();

        if (productFile != null && productFile.getSize() > 0) {
            // 上传成功的绝对路径（不包含文件名）
            String realPath = request.getSession().getServletContext().getRealPath("upload/product") + File.separator ;

            String newFileName = ImageTool.upload(productFile, realPath,"products");
            if (StringUtils.isNotEmpty(newFileName)) {// 不为空代表上传成功
                String realUrl = realPath + File.separator + newFileName;
                ImportExecl poi_product = new ImportExecl();
                List<ProductExcel> productExcelList = new ArrayList<ProductExcel>();
                productExcelList = poi_product.read(realUrl, ProductExcel.class, 0, 1);
                for(ProductExcel productExcel:productExcelList){
                    Product product = new Product();
                    String productCode = "P" + IdGenerator.getProductID();
                    product.setName(productExcel.getProductName());
                    product.setProductCode(productCode);
                    ProductSort productSort = productSortService.findProductSortByName(productExcel.getCommpany());
                    product.setProductSort(productSort);
                    boolean add = productService.saveProduct(product);

                    if(add){
                        ProductStyle productStyle = new ProductStyle(productCode,"大样", new BigDecimal(productExcel.getDaYang()),"元/件");
                        ProductStyle productStyle1 = new ProductStyle(productCode,"吊卡", new BigDecimal(productExcel.getDiaoKa()),"元/套");
                        ProductStyle productStyle2 = new ProductStyle(productCode,"面料", new BigDecimal(productExcel.getMianLiao()),"元/米");
                        productStyleService.saveProductStyle(productStyle);
                        productStyleService.saveProductStyle(productStyle1);
                        productStyleService.saveProductStyle(productStyle2);
                    }


                }

            }
        }

        response.setCode(HttpResponse.CODE_OK);
        response.setStatus(HttpResponse.STATUS_SUCCESS);
        response.setMessage("导入产品成功");

        return response;
    }

}
