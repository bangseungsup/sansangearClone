//package com.clone.sansansgear.controller;
//
//import com.clone.sansansgear.entity.Post;
//import com.clone.sansansgear.security.UserDetailsImpl;
//import org.springframework.data.domain.Page;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
//
////public class BucketController {
//
////  장바구니 인증 부분
////    @GetMapping("/api/bucket/{postId}")
////    public Page<Post> getPostsInFolder(
////            @PathVariable Long bucketId,
////            @RequestParam int price,
////            @RequestParam int productNum,
////            @RequestParam int size,
//////            @RequestParam String sortBy,      ---> 논의 필
//////            @RequestParam int page,           ---> 논의 필
////            @AuthenticationPrincipal UserDetailsImpl userDetails
////
////    ){
////        return bucketService.getPostsInFolder(
////                bucketId,
////                size,
////                price,
////                ProductNum,
//////                sortBy,       --->    논의 필
//////                page,         --->    논의 필
////                userDetails.getUser();
////
////
////        )
////    }
//
//}
