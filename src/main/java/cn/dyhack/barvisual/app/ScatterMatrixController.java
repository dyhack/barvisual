package cn.dyhack.barvisual.app;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.tools.ant.taskdefs.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dyhack.barvisual.pojo.tables.pojos.Bars;
import cn.dyhack.barvisual.pojo.tables.pojos.Total;
import cn.dyhack.barvisual.resp.AgeAndTimeResp;
import cn.dyhack.barvisual.resp.AgeCount;
import cn.dyhack.barvisual.resp.BarRelevantResp;
import cn.dyhack.barvisual.resp.InternetUsersCount;
import cn.dyhack.barvisual.resp.ProvinceFloatCountResp;
import cn.dyhack.barvisual.service.BarsServiceImpl;
import cn.dyhack.barvisual.service.TotalsServiceImpl;

@CrossOrigin(origins = {"http://localhost:8090", "null"})
@RestController
public class ScatterMatrixController {

    @Autowired
    TotalsServiceImpl totalsService;
    @Autowired
    BarsServiceImpl barsService;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 
     * 获取网吧
     *
     * @return 网吧地址
     * @author zhangke
     * @since 0.0.1
     */
    @GetMapping(value = "/bars")
    @PostMapping(value = "/bars")
    public List<Bars> getIllegalBars(@RequestParam(required = false) String fields, @RequestParam(required = false)  String filters,
            @RequestParam(required = false, defaultValue = "0") Integer offset, @RequestParam(required = false, defaultValue = "5555555") Integer limit, @RequestParam(required = false) String sorts) 
    {
        return barsService.getAllBars(fields, filters, offset, limit, sorts);
    }

    /**
     * 
     * 返回直接使用未成年身份证的网吧，和人员的详细信息
     *
     * @author zhangke
     * @since 0.0.1
     */

    @RequestMapping(value= "/illegal_bar",method = {RequestMethod.GET,RequestMethod.POST})
    public List<Total> getInternetBarDataSet() throws JsonProcessingException
    {
        return totalsService.selectAudit();
    }
    

    /**
     * 查找流动人口，并通过关键字过滤再过滤里面的流动人口
     * @author zhangke
     * @since 0.0.4
     */
    @RequestMapping(value= "/floating_population",method = {RequestMethod.GET,RequestMethod.POST})
    public List<Total> getFloatingPopulation(@RequestParam(required = false) String fields, @RequestParam(required = false,defaultValue = "float_population=1")  String filters,
            @RequestParam(required = false, defaultValue = "0") Integer offset, @RequestParam(required = false, defaultValue = "5555555") Integer limit, @RequestParam(required = false) String sorts) 
    {
        return totalsService.getFloatingPopulation(fields, filters, offset, limit, sorts);
    }
    

    
    /**
     * 通过时间间隔找到不同时间段的上网人数
     * @author zhangke
     * @since 0.0.4
     */
    @RequestMapping(value= "/internet_users",method = {RequestMethod.GET,RequestMethod.POST})
    public List<InternetUsersCount> getUsersByTimeSplit(@RequestParam(required = true) long startTime,@RequestParam(required = true)long endTime,@RequestParam(required = true)long interval,@RequestParam(required = false) String barIds)
    {
        return totalsService.selectAllByTimeSplit(startTime,endTime,interval,barIds);
    }
    
    
    /**
     * 通过上网最大的时间和分成多少段以及网吧ID
     * 来获取年龄，上网时长,上网的人数
     * @author zhangke
     * @since 0.0.4
     */
    
    @RequestMapping(value= "/age_time",method = {RequestMethod.GET,RequestMethod.POST})
    public List<AgeAndTimeResp> getInternetAgeAndTime(@RequestParam(required = true) int maxTime,@RequestParam(required =true) int interval,@RequestParam(required = false) String barIds) throws Exception
    {
        return totalsService.selectInternetAgeAndTime(maxTime,interval,barIds);
    }
    
    /**
     * 通过过滤条件(开始时间，结束时间,最大最小年龄，最大早少上网时间)来判断(barId(网吧ID):count(上网人数):float_count(流动人口数量))
     * @author zhangke
     * @since 0.0.4
     */
    @RequestMapping(value= "/filterinfo",method = {RequestMethod.GET,RequestMethod.POST})
    public List<BarRelevantResp> getBarRelevantInfo(@RequestParam(required = true) long startTime,
            @RequestParam(required = true) long endTime, @RequestParam(required = true) int minAge,
            @RequestParam(required = true) int maxAge, @RequestParam(required = true) long maxInternetTime,
            @RequestParam(required = true) long minInternetTime) {
        return totalsService.getBarRelevantResp(startTime, endTime, minAge, maxAge, maxInternetTime, minInternetTime);
    }
    
     /**
      * 获取省份和对应的流动人口数量
      */
    @RequestMapping(value = "province-usercount",method = {RequestMethod.GET,RequestMethod.POST})
    public HashMap<String,Integer>  getFloatPersonByProvince()
    {
        return totalsService.getFloatPersonByProvince();
    }
   
    @RequestMapping(value= "/age-count",method = {RequestMethod.GET,RequestMethod.POST})
    public List<AgeCount> getAgeCount() {
        return totalsService.selectAgeCount();
    }
    
    @RequestMapping(value= "/internet-time-distribution",method = {RequestMethod.GET,RequestMethod.POST})
    public List<Long> queryInternetTimeDistribution(
    		@RequestParam(required = true) long startTime,
    		@RequestParam(required = true)long endTime) {
        return totalsService.queryInternetTimeDistribution(startTime, endTime);
    }
    
}



