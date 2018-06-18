package com.kingguanzhang.dealsites.service.impl;


import com.kingguanzhang.dealsites.dao.ProductMapper;
import com.kingguanzhang.dealsites.dto.Msg;
import com.kingguanzhang.dealsites.pojo.Product;
import com.kingguanzhang.dealsites.pojo.ProductExample;
import com.kingguanzhang.dealsites.service.ProductService;
import com.kingguanzhang.dealsites.util.ImgUtil;
import com.kingguanzhang.dealsites.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@CacheConfig(cacheNames = "pro")
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     * 查询店铺内所有商品
     * @param shopId
     * @return
     */
    @Override
    public List<Product> getProductList(Integer shopId) {
        if (null == shopId){
            throw new RuntimeException("未能获取到店铺Id");
        }
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        List<Product> productList = productMapper.selectByExample(productExample);
        return productList;
    }

    /**
     * 获取商品详情
     * @param productId
     * @return
     */
    @Override
    public Product getProduct(Integer productId) {
        if (null == productId){
            throw new RuntimeException("未能获取到商品Id");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        return product;
    }

    /**
     * 新增商品
     * @param product
     * @param productImgInputStream
     * @param originalFilename
     */
    @Override
    public void addProduct(Product product, InputStream productImgInputStream, String originalFilename) {
        if (null == product){
            throw new RuntimeException("商品信息解析失败");
        }

        try {
            product.setEnableStatus(1);//0代表上架,1代表下架
            product.setCreateTime(new Date());
            product.setEditTime(new Date());
            int effectedNum = productMapper.insert(product);
            System.out.println( "执行添加商品返回的结果值是 : " + effectedNum);
            if (effectedNum <= 0){

                throw new RuntimeException("商品创建失败");
            }else{
                if (null != productImgInputStream){
                    try{
                        String productImgAddr = addproductImg(product, productImgInputStream,originalFilename);
                    }catch (Exception e){
                        throw new RuntimeException("设置图片地址错误 : " + e.getMessage());

                    }

                }
            }
            int en = productMapper.updateByPrimaryKey(product);
            System.out.println("执行更新商品返回的结果值是 : " + en);
            if (en <= 0){
                throw new RuntimeException("更新商品图片信息失败");
            }
        }catch (Exception e){
            throw new RuntimeException("添加商品异常 : " + e.getMessage());
        }

    }

    /**
     * 更新商品并更新图片的方法;
     * @param product
     * @param inputStream
     * @param originalFilename
     */
    @Override
    public Msg updateProductWithImg(Product product, InputStream inputStream, String originalFilename) {
        if (null == product){
            return Msg.fail().setMsg("添加商店失败,店铺信息不能为空!");
        }

        try {
            //更新商品会进入下架状态;
            product.setEnableStatus(1);
            product.setCreateTime(new Date());
            product.setEditTime(new Date());

            if (null != inputStream){
                try{
                    String shopImgAddr = addproductImg(product, inputStream,originalFilename);
                }catch (Exception e){
                    throw new RuntimeException("设置图片地址错误 : " + e.getMessage());

                }

            }

            int en = productMapper.updateByPrimaryKeySelective(product);
            System.out.println("执行更新商品返回的结果值是 : " + en);
            if (en <= 0){
                throw new RuntimeException("更新商品图片信息失败");
            }
        }catch (Exception e){
            throw new RuntimeException("更新商品异常 : " + e.getMessage());
        }

        //在所有操作都执行完成之后,返回提示;
        return Msg.success().setMsg("更新商品成功,请重新上架");
    }

    /**
     * 获取分类下所有商品;
     * @return
     */
    @Override
    public List<Product> getOnSellProductListByCategoryIdAndShopId(Integer categoryId,Integer shopId) {
        if (null == categoryId || null == shopId ){
            throw new RuntimeException("未能获取到分类Id或店铺Id");
        }
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        criteria.andProductCategoryIdEqualTo(categoryId);
        criteria.andEnableStatusEqualTo(0);
        List<Product> productList = productMapper.selectByExample(productExample);
        return productList;
    }

    /**
     * 获取所有店铺某个分类下所有商品;
     * @return
     */
    @Override
    public List<Product> getOnSellProductListByCategoryId(Integer categoryId) {
        if (null == categoryId  ){
            throw new RuntimeException("未能获取到分类Id");
        }
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andProductCategoryIdEqualTo(categoryId);
        criteria.andEnableStatusEqualTo(0);
        List<Product> productList = productMapper.selectByExample(productExample);
        return productList;
    }

    /**
     * 获取所有店铺的所有在售商品;最按后编辑时间倒序;
     * @return
     */
    @Cacheable(value = "pro",key = "list")
    @Override
    public List<Product> getAllOnSellProductList() {
        ProductExample productExample = new ProductExample();
        productExample.setOrderByClause("edit_time DESC");
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andEnableStatusEqualTo(0);
        List<Product> productList = productMapper.selectByExample(productExample);
        return productList;
    }

    /**
     * 更新商品不带图片的方法;
     * @param product
     * @return
     */
    @Override
    public int updateProduct(Product product) {
        if (null == product  ){
            throw new RuntimeException("未能获取到商品");
        }
        //更新商品会进入下架状态
        product.setEnableStatus(1);
        int i=0;
        try{
             i = productMapper.updateByPrimaryKeySelective(product);
        }catch (Exception e){
            throw new RuntimeException("更新商品失败");
        }
        return i;
    }

    /**
     * 删除商品
     * @param productId
     * @return
     */
    @Override
    public Integer removeProduct(Integer productId) {
        if (null == productId  ){
            throw new RuntimeException("未能获取到商品Id");
        }
        int i=0;
        try{
             i = productMapper.deleteByPrimaryKey(productId);
        }catch (Exception e){
            throw new RuntimeException("删除商品失败");
        }
        return i;
    }

    /**
     * 上架商品
     * @param productId
     * @return
     */
    @Override
    public Integer shelveProduct(Integer productId) {
        if (null == productId  ){
            throw new RuntimeException("未能获取到商品Id");
        }
        Product product = new Product();
        product.setProductId(productId);
        product.setEnableStatus(0);
        int i=0;
        try{
             i = productMapper.updateByPrimaryKeySelective(product);
        }catch (Exception e){
            throw new RuntimeException("上架商品失败");
        }
        return i;
    }

    /**
     * 下架商品
     * @param productId
     * @return
     */
    @Override
    public Integer unShelveProduct(Integer productId) {
        if (null == productId  ){
            throw new RuntimeException("未能获取到商品Id");
        }
        Product product = new Product();
        product.setProductId(productId);
        product.setEnableStatus(1);
        int i=0;
        try{
            i = productMapper.updateByPrimaryKeySelective(product);
        }catch (Exception e){
            throw new RuntimeException("下架商品失败");
        }
        return i;
    }

    /**
     * 查询所有上架中的商品
     * @param shopId
     * @return
     */
    @Override
    public List<Product> getShelveProductList(Integer shopId) {
        if (null == shopId  ){
            throw new RuntimeException("未能获取到店铺Id");
        }
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        criteria.andEnableStatusEqualTo(0);
        List<Product> productList = productMapper.selectByExample(productExample);
        return productList;
    }

    /**
     * 查询所有下架中的商品
     * @param shopId
     * @return
     */
    @Override
    public List<Product> getUnShelveProduct(Integer shopId) {
        if (null == shopId  ){
            throw new RuntimeException("未能获取到店铺Id");
        }
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        criteria.andEnableStatusEqualTo(1);
        List<Product> productList = productMapper.selectByExample(productExample);
        return productList;
    }

    /**
     * 批量删除商品
     * @param productIdList
     * @return
     */
    @Override
    public Integer deleteProducts(List<Integer> productIdList) {
        if (null == productIdList || 0 == productIdList.size() ){
            throw new RuntimeException("未能获取到商品Id");
        }
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andProductIdIn(productIdList);
        int i=0;
        try{
            i = productMapper.deleteByExample(productExample);
        }catch (Exception e){
            throw new RuntimeException("批量删除失败");
        }
        return i;
    }

    /**
     * 批量上架商品
     * @param productIdList
     * @return
     */
    @Override
    public Integer putawayProducts(List<Integer> productIdList) {
        if (null == productIdList || 0 == productIdList.size() ){
            throw new RuntimeException("未能获取到商品Id");
        }
        Product product = new Product();
        product.setEnableStatus(0);
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andProductIdIn(productIdList);
        int i=0;
        try{
            i = productMapper.updateByExampleSelective(product,productExample);
        }catch (Exception e){
            throw new RuntimeException("批量上架失败");
        }
        return i;
    }

    /**
     * 批量下架商品
     * @param productIdList
     * @return
     */
    @Override
    public Integer soldoutProducts(List<Integer> productIdList) {
        if (null == productIdList || 0 == productIdList.size() ){
            throw new RuntimeException("未能获取到商品Id");
        }
        Product product = new Product();
        product.setEnableStatus(1);
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andProductIdIn(productIdList);
        int i=0;
        try{
            i = productMapper.updateByExampleSelective(product,productExample);
        }catch (Exception e){
            throw new RuntimeException("批量下架失败");
        }
        return i;
    }

    /**
     * 查询所有店铺的所有商品
     * @return
     */
    @Override
    public List<Product> getAllProductList() {
        List<Product> productList = productMapper.selectByExample(null);
        return productList;
    }

    /**
     * 查询所有店铺的所有上架中商品
     * @return
     */
    @Override
    public List<Product> getAllPutawayProductList() {
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andEnableStatusEqualTo(0);
        List<Product> products = productMapper.selectByExample(productExample);
        return products;
    }

    /**
     * 查询所有店铺的所有下架中商品
     * @return
     */
    @Override
    public List<Product> getAllSoldoutProduct() {
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andEnableStatusEqualTo(1);
        List<Product> products = productMapper.selectByExample(productExample);
        return products;
    }



    private String addproductImg(Product product, InputStream productImgInputStream, String fileName) {
        String productImagePath = PathUtil.getProductImagePath(product.getProductId());
        String productImgAddr = ImgUtil.generateThumbnail(productImgInputStream,productImagePath,fileName);
        product.setImgAddr(productImgAddr);
        return productImagePath;
    }
}
