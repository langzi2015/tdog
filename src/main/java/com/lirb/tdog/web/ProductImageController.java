package com.lirb.tdog.web;

import com.lirb.tdog.pojo.Product;
import com.lirb.tdog.pojo.ProductImage;
import com.lirb.tdog.service.CategoryService;
import com.lirb.tdog.service.ProductImageService;
import com.lirb.tdog.service.ProductService;
import com.lirb.tdog.util.ImageUtil;
import com.lirb.tdog.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductImageController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/products/{pid}/productImages")
    public List<ProductImage> list(@RequestParam("type") String type,
                                   @PathVariable("pid") int pid) throws Exception {
        Product product = productService.get(pid);
        if(ProductImageService.type_single.equals(type)) {
            return productImageService.listSingleProductImages(product);
        }
        else if(ProductImageService.type_detail.equals(type)) {
            return productImageService.listDetailProductImages(product);
        }
        else {
            return new ArrayList<>();
        }
    }

    @PostMapping("/productImages")
    public Object add(@RequestParam("pid") int pid,
                      @RequestParam("type") String type,
                      MultipartFile image, HttpServletRequest request) throws Exception {
        ProductImage bean = new ProductImage();
        Product product = productService.get(pid);
        bean.setProduct(product);
        bean.setType(type);
        productImageService.add(bean);
        String folder = "img/";
        if(ProductImageService.type_single.equals(bean.getType())){
            folder +="productSingle";
        }
        else{
            folder +="productDetail";
        }
        File imageFolder= new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder,bean.getId()+".jpg");
        String fileName = file.getName();
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        try {
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(ProductImageService.type_single.equals(bean.getType())){
            String imageFolder_small= request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle= request.getServletContext().getRealPath("img/productSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.getParentFile().mkdirs();
            f_middle.getParentFile().mkdirs();
            ImageUtil.resizeImage(file, 56, 56, f_small);
            ImageUtil.resizeImage(file, 217, 190, f_middle);
        }
        return bean;
    }

    @DeleteMapping("/productImages/{id}")
    public Object delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        ProductImage bean = productImageService.get(id);
        productImageService.delete(id);
        String folder = "img/";
        if(ProductImageService.type_single.equals(bean.getType()))
            folder +="productSingle";
        else
            folder +="productDetail";
        File  imageFolder= new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder,bean.getId()+".jpg");
        String fileName = file.getName();
        file.delete();
        if(ProductImageService.type_single.equals(bean.getType())){
            String imageFolder_small= request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle= request.getServletContext().getRealPath("img/productSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.delete();
            f_middle.delete();
        }
        return Result.success();
    }
}
