package com.advance.reptile.wx.shelf.controller;

import com.advance.reptile.common.Response;
import com.advance.reptile.wx.shelf.service.IShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wx/shelf")
public class ShelfController {

    @Autowired
    private IShelfService shelfService;

    @RequestMapping("/books")
    public Response getBooks(){
        String userId = "0EFFF6396D10457C83927BB4A2224241";
        return Response.success(shelfService.getShelfBooks(userId));
    }

    @RequestMapping("/add/{bookId}")
    public Response addBookShelf(@PathVariable("bookId")String bookId){
        return Response.success();
    }

    @RequestMapping("/delete")
    public Response deleteShelfBook(@RequestParam String ids){
        shelfService.delShelfBooks(ids);
        return Response.success();
    }

}
