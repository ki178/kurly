package com.bhs.sssss.controllers;

import com.bhs.sssss.entities.ImageEntity;
import com.bhs.sssss.entities.InquiryEntity;
import com.bhs.sssss.entities.ReviewEntity;
import com.bhs.sssss.results.CommonResult;
import com.bhs.sssss.results.Result;
import com.bhs.sssss.results.WriteResult;
import com.bhs.sssss.services.InquiryService;
import com.bhs.sssss.services.ReviewService;
import com.bhs.sssss.vos.PageVo;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/kurly")
public class GoodsController {
    private final InquiryService inquiryService;
    private final ReviewService reviewService;

    @Autowired
    public GoodsController(InquiryService inquiryService, ReviewService reviewService) {
        this.inquiryService = inquiryService;
        this.reviewService = reviewService;
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@RequestParam(value = "index", required = false, defaultValue = "0") int index) {
        ImageEntity image = this.reviewService.getImage(index);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .ok()
                .contentLength(image.getData().length)
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(image.getData());
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postImage(@RequestParam(value = "upload") MultipartFile file) throws IOException {
        ImageEntity image = new ImageEntity();
        image.setData(file.getBytes());
        image.setContentType(file.getContentType());
        image.setName(file.getOriginalFilename());
        JSONObject response = new JSONObject();
        boolean result = this.reviewService.uploadImage(image);
        if (result) {
            response.put("url", "/kurly/image?index=" + image.getIndex());
        }
        return response.toString();
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getInquiriesAndReviews(
            @RequestParam(value = "inquiryPage", required = false, defaultValue = "1") int inquiryPage,
            @RequestParam(value = "reviewPage", required = false, defaultValue = "1") int reviewPage) {

        // 문의 사항과 리뷰 목록 가져오기
        Pair<PageVo, List<InquiryEntity>> pairInquiries = this.inquiryService.getInquiriesByPage(inquiryPage);
        Pair<PageVo, List<ReviewEntity>> pairReviews = this.reviewService.getReviewsByPage(reviewPage);

        // 총 리뷰 개수 가져오기
        int totalReviews = this.reviewService.getTotalReviewCount();

        // 리뷰 이미지 처리
        for (ReviewEntity review : pairReviews.getRight()) {
            if (review.getImageIndex() != null) {
                ImageEntity image = this.reviewService.getImage(review.getImageIndex());
                review.setImages(List.of(image));
            }
        }

        ModelAndView modelAndView = new ModelAndView("goods/index");
        modelAndView.addObject("inquiryPageVo", pairInquiries.getLeft());
        modelAndView.addObject("reviewPageVo", pairReviews.getLeft());
        modelAndView.addObject("inquiries", pairInquiries.getRight());
        modelAndView.addObject("reviews", pairReviews.getRight());
        modelAndView.addObject("totalReviews", totalReviews);
        return modelAndView;
    }

    @RequestMapping(value = "/inquiry", method = RequestMethod.GET)
    public ModelAndView getWrite() {
        return new ModelAndView("write/inquiry");
    }

    @RequestMapping(value = "/inquiry", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postWrite(InquiryEntity inquiry) {
        JSONObject response = new JSONObject();
        WriteResult result = this.inquiryService.write(inquiry);
        response.put(Result.NAME, result.name().toLowerCase());
        return response.toString();
    }

    @RequestMapping(value = "/review", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getReview(@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        Pair<PageVo, List<ReviewEntity>> pair = this.reviewService.getReviewsByPage(page);
        List<ReviewEntity> reviews = pair.getRight();

        for (ReviewEntity review : reviews) {
            if (review.getImageIndex() != null) {
                ImageEntity image = this.reviewService.getImage(review.getImageIndex());
                review.setImages(List.of(image));
            }
        }

        ModelAndView modelAndView = new ModelAndView("write/review");
        modelAndView.addObject("pageVo", pair.getLeft());
        modelAndView.addObject("reviews", reviews);
        return modelAndView;
    }

    @RequestMapping(value = "/review", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postReview(@RequestParam(value = "title") String title,
                             @RequestParam(value = "content") String content,
                             @RequestParam(value = "image", required = false) MultipartFile imageFile) throws IOException {
        ReviewEntity review = new ReviewEntity();
        review.setWriter("작성자");
        review.setTitle(title);
        review.setContent(content);

        JSONObject response = new JSONObject();
        Result result = this.reviewService.write(review, imageFile);
        response.put("result", result.name().toLowerCase());
        return response.toString();
    }

    // 리뷰 수정 처리
    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    @ResponseBody
    public String modifyReview(@RequestParam("index") int index,
                               @RequestParam("title") String title,
                               @RequestParam("content") String content,
                               @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        ReviewEntity review = new ReviewEntity();
        review.setIndex(index);
        review.setTitle(title);
        review.setContent(content);

        CommonResult result = reviewService.modifyReview(review, image);
        JSONObject response = new JSONObject();
        response.put("result", result.name().toLowerCase());
        return response.toString();
    }

    @RequestMapping(value = "/modify", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getModify(@RequestParam(value = "index", required = false, defaultValue = "0") int index) {
        ModelAndView modelAndView = new ModelAndView();

        // index에 해당하는 리뷰를 가져옵니다.
        ReviewEntity review = this.reviewService.getReviewByIndex(index);

        // 리뷰가 존재하고, 삭제되지 않은 경우에만 수정 가능
        if (review != null && review.getDeletedAt() == null) {
            modelAndView.addObject("review", review); // 리뷰 객체를 HTML로 전달
            modelAndView.setViewName("goods/modify"); // "goods/modify.html"로 이동
        } else {
            modelAndView.setViewName("redirect:/kurly/index");
        }
        // 기본 뷰 페이지 설정
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteReview(@RequestParam(value = "index", required = true) int index) {
        JSONObject response = new JSONObject();
        CommonResult result = reviewService.deleteReview(index); // 서비스 호출

        if (result == CommonResult.SUCCESS) {
            response.put("result", "success");
        } else {
            response.put("result", "failure");
        }
        return response.toString();
    }
}
