package cn.dyhack.barvisual.app;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import cn.dyhack.barvisual.map.VoPersonsMapper;
import cn.dyhack.barvisual.pojo.tables.pojos.Bars;
import cn.dyhack.barvisual.pojo.tables.pojos.Total;
import cn.dyhack.barvisual.resp.AgeAndTimeResp;
import cn.dyhack.barvisual.resp.AgeCount;
import cn.dyhack.barvisual.resp.BarRelevantResp;
import cn.dyhack.barvisual.resp.ExportData;
import cn.dyhack.barvisual.resp.InternetUserFilterBean;
import cn.dyhack.barvisual.resp.InternetUsersCount;
import cn.dyhack.barvisual.resp.PersonsByAge;
import cn.dyhack.barvisual.service.BarsServiceImpl;
import cn.dyhack.barvisual.service.TotalsServiceImpl;
import cn.dyhack.barvisual.util.ExportExcel;
import cn.signit.wesign.lib.common.type.JacksonConverter;

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
     * 查找流动人口，并通过关键字过滤再过滤里面的流动人口(前端未使用)
     * @author zhangke
     * @since 0.0.4
     */
    @RequestMapping(value = "/floating_population", method = { RequestMethod.GET, RequestMethod.POST })
    public List<Total> getFloatingPopulation(@RequestParam(required = false) String fields,
            @RequestParam(required = false, defaultValue = "float_population=1") String filters,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "5555555") Integer limit,
            @RequestParam(required = false) String sorts) {
        return totalsService.getFloatingPopulation(fields, filters, offset, limit, sorts);
    }
    
    /**
     * 通过时间间隔找到不同时间段的上网人数
     * @author zhangke
     * @since 0.0.4
     */
    @RequestMapping(value = "/internet_users", method = { RequestMethod.GET, RequestMethod.POST })
    public List<InternetUsersCount> getUsersByTimeSplit( 
    		@RequestParam(required = true) long startTime,
            @RequestParam(required = true) long endTime, 
            @RequestParam(required = true) long interval,
            @RequestParam(required = false) String barIds,
            @RequestParam(required = false, defaultValue = "0") int type,
            @RequestParam(required = false) String ageTime) {
        if (barIds == null) {
            barIds = "";
        }

        List<InternetUserFilterBean> ageTimeT = new ArrayList<InternetUserFilterBean>();
        if (ageTime != null) {
            ageTimeT = JacksonConverter.decodeAsList(ageTime, InternetUserFilterBean.class);
        }
        return totalsService.selectAllByTimeSplit(startTime, endTime, interval, barIds, ageTimeT, type);
    }
    
    /**
     * 通过上网最大的时间和分成多少段以及网吧ID,以及上网是否在这个时间段里面
     * 来获取年龄，上网时长,上网的人数
     * @author zhangke
     * @throws Exception 
     * @since 0.0.4
     */
    
    @RequestMapping(value = "/age_time", method = { RequestMethod.GET, RequestMethod.POST })
    public List<AgeAndTimeResp> getInternetAgeAndTime(
    		@RequestParam(required = false) Long startTime,
            @RequestParam(required = false) Long endTime,
            @RequestParam(required = false) String barIds,
            @RequestParam(required = false, defaultValue = "0") int type,
            @RequestParam(required = true) int maxInternetTime,
            @RequestParam(required = true) int interval) throws Exception {
	    	if (barIds == null) {
	            barIds = "";
	        }
            return totalsService.selectInternetAgeAndTime(startTime, endTime, maxInternetTime, interval, barIds, type);

    }
    
    /**
     * 通过过滤条件(开始时间，结束时间,最大最小年龄，最大早少上网时间)来判断(barId(网吧ID):count(上网人数):float_count(流动人口数量))
     * @author zhangke
     * @since 0.0.4
     */
    @RequestMapping(value= "/filterinfo",method = {RequestMethod.GET,RequestMethod.POST})
    public List<BarRelevantResp> getBarRelevantInfo(
    		@RequestParam(required = true) long startTime,
            @RequestParam(required = true) long endTime,
            @RequestParam(required = false) String barIds,
            @RequestParam(required = false, defaultValue = "0") int type,
            @RequestParam(required = false) String ageTime) {
    	
    	if(barIds == null) {
            barIds = "";
        }
        
        List<InternetUserFilterBean> ageTimeT = new ArrayList<InternetUserFilterBean>();
        if(ageTime != null) {
            ageTimeT = JacksonConverter.decodeAsList(ageTime, InternetUserFilterBean.class);
        }
        
        return totalsService.getBarRelevantResp(startTime, endTime, barIds, ageTimeT, type);
    }
    
     /**
      * 获取省份和对应的流动人口数量
      */
    @RequestMapping(value = "province-usercount",method = {RequestMethod.GET,RequestMethod.POST})
    public HashMap<String,Integer>  getFloatPersonByProvince(
	    		@RequestParam(required = true) long startTime,
	            @RequestParam(required = true) long endTime,
	            @RequestParam(required = false) String barIds,
	            @RequestParam(required = false, defaultValue = "0") int type,
	            @RequestParam(required = false) String ageTime
    		)
    {
    	if(barIds == null) {
            barIds = "";
        }
        
        List<InternetUserFilterBean> ageTimeT = new ArrayList<InternetUserFilterBean>();
        if(ageTime != null) {
            ageTimeT = JacksonConverter.decodeAsList(ageTime, InternetUserFilterBean.class);
        }
        
        return totalsService.getFloatPersonByProvince(startTime, endTime, barIds, ageTimeT, type);
    }
   
    @RequestMapping(value= "/age-count",method = {RequestMethod.GET,RequestMethod.POST})
    public List<AgeCount> getAgeCount(
							    		@RequestParam(required = true) long startTime,
							            @RequestParam(required = true)long endTime,
							            @RequestParam(required = true, defaultValue = "1")long interval,
							            @RequestParam(required = false) String barIds,
							            @RequestParam(required = false) String ageTime,
							            @RequestParam(required = false, defaultValue = "0") int type
							         ) {
        if(barIds == null) {
            barIds = "";
        }
        
        List<InternetUserFilterBean> ageTimeT = new ArrayList<InternetUserFilterBean>();
        if(ageTime != null) {
            ageTimeT = JacksonConverter.decodeAsList(ageTime, InternetUserFilterBean.class);
        }
        return totalsService.selectAgeCount(startTime,endTime,interval,barIds,ageTimeT, type);
    }
    
    @RequestMapping(value= "/internet-time-distribution",method = {RequestMethod.GET,RequestMethod.POST})
    public List<Long> queryInternetTimeDistribution(
            @RequestParam(required = true) long startTime,
            @RequestParam(required = true) long endTime,
            @RequestParam(required = false, defaultValue = "0") int type,
            @RequestParam(required = false) String barIds,
            @RequestParam(required = false) String ageTime) {
    	
        if (barIds == null) {
            barIds = "";
        }
        List<InternetUserFilterBean> ageTimeT = new ArrayList<InternetUserFilterBean>();
        if (ageTime != null) {
            ageTimeT = JacksonConverter.decodeAsList(ageTime, InternetUserFilterBean.class);
        }
        
        return totalsService.queryInternetTimeDistribution(startTime, endTime,barIds,ageTimeT, type);
    }
    
    /**
     * 
     * 导出筛选后的相应的数据
     *
     * @param startTime 上线时间
     * @param endTime 下线时间
     * @param barIds 多个网吧id
     * @param ageTime 年龄和上网时间条件
     * @author zhangke
     * @since 0.0.6
     */
    @RequestMapping(value= "/export-data",method = {RequestMethod.GET,RequestMethod.POST})
    public void exportData(
    		@RequestParam(required = true) long startTime,
            @RequestParam(required = true) long endTime,
            @RequestParam(required = false) String barIds,
            @RequestParam(required = false, defaultValue = "0") int type,
            @RequestParam(required = false) String ageTime,
            HttpServletResponse response)
    {   
    	if (barIds == null) {
            barIds = "";
        }
    	
        List<InternetUserFilterBean> ageTimeT = new ArrayList<InternetUserFilterBean>();
        if (ageTime != null) {
            ageTimeT = JacksonConverter.decodeAsList(ageTime, InternetUserFilterBean.class);
        }
           HashMap<String,ExportData> tempresult= totalsService.exportData(startTime,endTime,barIds,ageTimeT);
           List<ExportData> exoprtReuslt = new ArrayList<ExportData>(tempresult.values());
           List<PersonsByAge> underAuditResult =  VoPersonsMapper.INSTATNCE.ListPersonToPersonVos(totalsService.exportAuditData());
           for(PersonsByAge p:underAuditResult)
           {   
               SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
               long time = new Long(p.getBirthday());
                p.setBirthday(sf.format(new java.util.Date(time*1000)));
                p.setSex(p.getSex().equals("1")?"男":"女");

           }
           //写入到excel中,并传返回给前端
           Map<String,String> titleMap = new LinkedHashMap<String,String>();
           String []sheetName = new String[]{"黑网吧信息","未成年人信息"};
           titleMap.put("barId", "网吧ID");
           titleMap.put("barName", "网吧名称");
           titleMap.put("underAudit", "未成年人数量");
           ExportExcel.initHSSFWorkbook(sheetName);
           ExportExcel.excelExport(0,exoprtReuslt, titleMap, sheetName);
           titleMap.clear();
           titleMap.put("id","用户ID");
           titleMap.put("name", "网吧名称");
           titleMap.put("sex", "性别");
           titleMap.put("areaid", "地区id");
           titleMap.put("birthday", "出生日期");
           HSSFWorkbook workbook = ExportExcel.excelExport(1,underAuditResult, titleMap, sheetName);
           //workbook.createSheet("未成年信息");
           try {
           String fileName = URLEncoder.encode("黑网吧", "UTF-8");   
               //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型   
               response.setContentType("multipart/form-data");   
               //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)   
               response.setHeader("Content-disposition","attachment;filename="+fileName+".xls");
               workbook.write(response.getOutputStream());
               
               
        } catch (IOException e) {
            e.printStackTrace();
        }
           

    }
    
}



