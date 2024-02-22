package cc.backend.user.controller;

import cc.backend.common.Result;
import cc.backend.entity.Article;
import cc.backend.entity.SearchData;
import cc.backend.user.service.impl.IndexArticleServiceImpl;
import cc.backend.user.service.impl.IndexImgServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @FileName ResourcesController
 * @Description 基础资源信息展示，未登录也可查看
 * @Author Tiamo_Null
 * @date 2023-08-20
 **/
@RestController
@RequestMapping("user/resources")
public class ResourcesController {

    @Autowired
    private IndexImgServiceImpl indexImgService;
    /**
     * TODO 图片
     */
    @GetMapping("/image")
    public ResponseEntity<List<String>> getImage() throws IOException {
        List<String> imageList = new ArrayList<>();
        //  从resources/static/img获取图片
        ClassPathResource imageResource = new ClassPathResource("static/sys_img/2023.jpg");
        InputStream imageStream = imageResource.getInputStream();
        byte[] imageBytes = imageStream.readAllBytes();
        String base64Image1 = Base64.getEncoder().encodeToString(imageBytes);
        imageList.add(base64Image1);
        //  设置响应头，告诉前端返回是图片数据
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(imageList,headers, HttpStatus.OK);
    }

    /**
     * TODO 首页走马灯图片获取
     */
    @GetMapping("/getIndexImage")
    public Result getIndexImage() {
        List<String> indexImage = indexImgService.getIndexImage();
        return Result.successCDM(indexImage,"获取走马灯图片数据成功");
    }
    @Autowired
    private IndexArticleServiceImpl articleService;

    /**
     * @description TODO 获取首页文章
     * @param pageData
     * @return: cc.backend.common.Result
     */
    @PostMapping("/getIndexArticle")
    public Result getIndexArticle(@RequestBody SearchData<Article> pageData) {
        SearchData<Article> indexArticle = articleService.getIndexArticle(pageData);
        return Result.successCDM(indexArticle,"获取文章成功");
    }

    /**
     * @description TODO 获取单个文章内容
     * @param articleId
     * @return: cc.backend.common.Result
     */
    @PostMapping("/getArticle")
    public Result getArticle(@RequestParam int articleId) {
        Article article = articleService.getArticle(articleId);
        System.out.println(article);
        return Result.successCDM(article,"获取文章"+articleId+"成功");
    }

}
