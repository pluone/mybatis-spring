package org.example.web.controller;

import org.example.web.model.SysDict;
import org.example.web.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/dicts")
public class DictController {
    @Autowired
    private DictService dictService;

    @RequestMapping
    public ModelAndView dicts(SysDict sysDict, Integer offset, Integer limit) {
        ModelAndView mv = new ModelAndView("dicts");
        List<SysDict> dicts = dictService.findBySysDict(sysDict, offset, limit);
        mv.addObject("dicts", dicts);
        return mv;
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView add(Long id) {
        ModelAndView mv = new ModelAndView("dict_add");
        SysDict sysDict;
        if(id == null){
            //如果 id 不存在，就是新增数据，创建一个空对象即可
            sysDict = new SysDict();
        } else {
            //如果 id 存在，就是修改数据，把原有的数据查询出来
            sysDict = dictService.findById(id);
        }
        mv.addObject("model", sysDict);
        return mv;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ModelAndView save(SysDict sysDict) {
        ModelAndView mv = new ModelAndView();
        try {
            dictService.saveOrUpdate(sysDict);
            mv.setViewName("redirect:/dicts");
        } catch (Exception e){
            mv.setViewName("dict_add");
            mv.addObject("msg", e.getMessage());
            mv.addObject("model", sysDict);
        }
        return mv;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap delete(@RequestParam Long id) {
        ModelMap modelMap = new ModelMap();
        try {
            boolean success = dictService.deleteById(id);
            modelMap.put("success", success);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.getMessage());
        }
        return modelMap;
    }
}
