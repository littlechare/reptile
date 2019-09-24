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
    public Response getBooks(String bookName){
        String userId = "0EFFF6396D10457C83927BB4A2224241";
        return Response.success(shelfService.getShelfBooks(userId, bookName));
    }

    @RequestMapping("/add/{bookId}")
    public Response addBookShelf(@PathVariable("bookId")String bookId){
        if(shelfService.addBookShelf("0EFFF6396D10457C83927BB4A2224241",bookId)){
            return Response.success(true);
        }else{
            return Response.success(false);
        }
    }

    @RequestMapping("/delete/{id}")
    public Response deleteShelfBook(@PathVariable("id") String id){
        shelfService.delShelfBooks(id);
        return Response.success();
    }

}
