package org.example.web.service.impl;

import org.apache.ibatis.session.RowBounds;
import org.example.web.mapper.DictMapper;
import org.example.web.model.SysDict;
import org.example.web.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl implements DictService {
    @Autowired
    private DictMapper dictMapper;

    @Override
    public SysDict findById(Long id) {
        return dictMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysDict> findBySysDict(SysDict sysDict, Integer offset, Integer limit) {
        RowBounds rowBounds = RowBounds.DEFAULT;
        if (offset != null && limit != null) {
            rowBounds = new RowBounds(offset, limit);
        }
        return dictMapper.selectBySysDict(sysDict, rowBounds);
    }

    @Override
    public boolean saveOrUpdate(SysDict sysDict) {
        int count;
        if (sysDict.getId() == null) {
            count = dictMapper.insert(sysDict);
        } else {
            count = dictMapper.updateById(sysDict);
        }
        return count == 1;
    }

    @Override
    public boolean deleteById(Long id) {
        return dictMapper.deleteById(id) == 1;
    }
}
