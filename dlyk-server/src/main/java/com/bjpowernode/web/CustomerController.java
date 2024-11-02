package com.bjpowernode.web;

import com.alibaba.excel.EasyExcel;
import com.bjpowernode.domain.po.TClue;
import com.bjpowernode.domain.po.TCustomer;
import com.bjpowernode.domain.query.CustomerQuery;
import com.bjpowernode.domain.result.R;
import com.bjpowernode.service.CustomerService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;

@RestController
public class CustomerController {

    @Resource
    private CustomerService customerService;

    /**
     * 线索转换为客户
     *
     * @param customerQuery
     * @param token
     * @return
     */
    @PostMapping(value = "/api/clue/customer")
    public R convertCustomer(@RequestBody CustomerQuery customerQuery, @RequestHeader(value = "Authorization") String token) {
        customerQuery.setToken(token);
        Boolean convert = customerService.convertCustomer(customerQuery);
        return convert ? R.OK() : R.FAIL();
    }

    /**
     * 客户列表分页查询
     *
     * @param current
     * @return
     */
    @GetMapping(value = "/api/customers")
    public R cluePage(@RequestParam(value = "current", required = false) Integer current) {
        if (current == null) {
            current = 1;
        }

        PageInfo<TCustomer> pageInfo = customerService.getCustomerByPage(current);
        return R.OK(pageInfo);
    }

    /**
     * 导出Excel(下载Excel)
     *
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/api/exportExcel")
    public void exportExcel(HttpServletResponse response,
                            @RequestParam(value = "ids", required = false) String ids) throws IOException {

        //后端就给前端返回一个Excel文件
        response.setContentType("application/octet-stream"); //二进制流类型，因为是返回文件
        String fileName = URLEncoder.encode("户信息列表", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx"); //设置响应头为附件（文件），也就是告诉浏览器，我们返回的是文件，你不要当做html解析，而是弹出下载框下载

        //生成好excel文件，把该文件返回给前端浏览器即可
        customerService.exportExcel(response.getOutputStream(), ids);
    }

    /**
     * 根据id查询客户详情
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/api/customer/detail/{id}")
    public R clueDetail(@PathVariable(value = "id") Integer id) {
        TCustomer tCustomer = customerService.getCustomerById(id);

        return R.OK(tCustomer);
    }
}
