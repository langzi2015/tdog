package com.lirb.tdog.web;

import com.lirb.tdog.pojo.Property;
import com.lirb.tdog.service.PropertyService;
import com.lirb.tdog.util.Page4Navigator;
import com.lirb.tdog.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
public class PropertyController {
    @Autowired
    PropertyService propertyService;

    @GetMapping("/categories/{cid}/properties")
    public Page4Navigator<Property> list(@PathVariable("cid") int cid,
                                         @RequestParam(value = "start", defaultValue = "0") int start,
                                         @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        return propertyService.list(cid, start, size,5);
    }

    @GetMapping("/properties/{id}")
    public Property get(@PathVariable("id") int id) throws Exception {
        return propertyService.get(id);
    }

    @PostMapping("/properties")
    public Object add(@RequestBody Property bean) throws Exception {
        propertyService.add(bean);
        return bean;
    }

    @DeleteMapping("/properties/{id}")
    public Object delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        propertyService.delete(id);
        return Result.success();
    }

    @PutMapping("/properties")
    public Object update(@RequestBody Property bean) throws Exception {
        propertyService.update(bean);
        return bean;
    }

}
