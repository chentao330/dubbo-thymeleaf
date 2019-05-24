package com.syedu.Controller;


import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pojo.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Reference
    private UserService userService;

    @RequestMapping("/addUserPage")
    public String addUserPage(){
        return "addUser";
    }

    @RequestMapping("/addUser")
    public void addUser(User user, Model model, HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        userService.addUser(user);
//        if (i!=1){
//            model.addAttribute("msg","添加失败");
//        }else{
//            model.addAttribute("msg","添加成功");
//        }

        request.getRequestDispatcher("/index").forward(request,response);
    }
    @RequestMapping("/index")
    public String selectUserByPage(@RequestParam(value = "currentPage",required =false)Integer currentPage, Model model){
        if (null==currentPage|| currentPage<1){
            currentPage=1;
        }
        int pagesize=10;

        int count = userService.pageCount();

        int totalPage=count /pagesize;

        if (count % pagesize!=0){
            totalPage=totalPage+1;
        }

        int pageIndex=(currentPage-1)*pagesize;

        Map<String,Integer> pagemap =new HashMap<String, Integer>();
        pagemap.put("pageIndex",pageIndex);
        pagemap.put("pagesize",pagesize);

        List<User> users = userService.selectUserByPage(pagemap);
        model.addAttribute("users",users);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalPage",totalPage);
        return "index";
    }
    @RequestMapping("/deleteUser")
    public void deleteUser(int id,Model model,HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        userService.deleteUser(id);
        request.getRequestDispatcher("/index").forward(request,response);
    }
    @RequestMapping("/selectUserById")
    public String selectUserById(int id, Model model, HttpSession session){
        User user = userService.selectUserById(id);
        model.addAttribute("user",user);
        session.setAttribute("id",id);
        return "updateUser";
    }
    @RequestMapping("/updateUser")
    public void updateUser(User user,Model model, HttpSession session,HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        int id = (int)session.getAttribute("id");
        user.setId(id);
        userService.updateUser(user);
        request.getRequestDispatcher("/index").forward(request,response);
    }


}
