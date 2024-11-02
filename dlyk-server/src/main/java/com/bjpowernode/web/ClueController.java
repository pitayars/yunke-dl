package com.bjpowernode.web;

import com.bjpowernode.domain.po.TClue;
import com.bjpowernode.domain.query.ClueQuery;
import com.bjpowernode.domain.result.R;
import com.bjpowernode.service.ClueService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class ClueController {

    @Resource
    private ClueService clueService;

    @GetMapping(value = "/api/clues")
    public R cluePage(@RequestParam(value = "current", required = false) Integer current) {
        if (current == null) {
            current = 1;
        }

        PageInfo<TClue> pageInfo = clueService.getClueByPage(current);
        return R.OK(pageInfo);
    }

    /**
     * 验证手机号是否录入过
     *
     * @param phone
     * @return
     */
    @GetMapping(value = "/api/clue/{phone}")
    public R checkPhone(@PathVariable(value = "phone") String phone) {
        int count = clueService.checkPhoneByCount(phone);
        return count <= 0 ? R.OK() : R.FAIL();
    }

    /**
     * 录入线索
     *
     * @param clueQuery
     * @param token
     * @return
     */
    @PostMapping(value = "/api/clue")
    public R addClue(ClueQuery clueQuery, @RequestHeader(value = "Authorization") String token) {
        clueQuery.setToken(token);
        int save = clueService.saveClue(clueQuery);

        return save >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 根据id查询线索详情
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/api/clue/detail/{id}")
    public R clueDetail(@PathVariable(value = "id") Integer id) {
        TClue tClue = clueService.getClueById(id);

        return R.OK(tClue);
    }

    /**
     * 编辑线索
     *
     * @param clueQuery
     * @param token
     * @return
     */
    @PutMapping(value = "/api/clue")
    public R updateClue(ClueQuery clueQuery, @RequestHeader(value = "Authorization") String token) {
        clueQuery.setToken(token);
        int update = clueService.updateClue(clueQuery);

        return update >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 上传/导入Excel文件
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/api/importExcel")
    public R importExcel(MultipartFile file, @RequestHeader(value = "Authorization") String token) throws IOException {
        //@RequestParam
        //PathVariable
        //@RequestBody
        //@RequestHeader
        //request.getParamter()
        //XxxQuery 接收对象（参数映射到对象中）
        //MultipartFile 接收文件
        System.out.println(file);

        clueService.importExcel(file.getInputStream(), token);

        //如果上面的方法执行，没有异常，那么说明上传成功，否则抛出异常会被全局异常处理器捕获，返回500错误
        return R.OK();
    }

    /**
     * 根据id删除线索
     *
     * @param id
     * @return
     */
    @PreAuthorize(value = "hasAuthority('clue:delete')")
    @DeleteMapping(value = "/api/clue/{id}")
    public R delClue(@PathVariable(value = "id") Integer id) {
        int del = clueService.delClueById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 根据id批量删除线索
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/api/clue/batch")
    public R batchDelClue(@RequestParam(value = "ids") String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        int batchDel = clueService.batchDelClueByIds(idList);
        return batchDel >= idList.size() ? R.OK() : R.FAIL();
    }
}
