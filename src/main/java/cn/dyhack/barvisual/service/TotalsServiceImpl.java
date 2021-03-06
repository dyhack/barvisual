package cn.dyhack.barvisual.service;

import static cn.dyhack.barvisual.pojo.tables.Total.TOTAL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dyhack.barvisual.dao.TotalMapperImpl;
import cn.dyhack.barvisual.pojo.tables.pojos.Bars;
import cn.dyhack.barvisual.pojo.tables.pojos.Persons;
import cn.dyhack.barvisual.pojo.tables.pojos.ProvinceId;
import cn.dyhack.barvisual.pojo.tables.pojos.Total;
import cn.dyhack.barvisual.resp.AgeAndTimeResp;
import cn.dyhack.barvisual.resp.AgeCount;
import cn.dyhack.barvisual.resp.BarRelevantResp;
import cn.dyhack.barvisual.resp.ExportData;
import cn.dyhack.barvisual.resp.InternetUserFilterBean;
import cn.dyhack.barvisual.resp.InternetUsersCount;
import cn.dyhack.barvisual.resp.ProvinceCount;
import cn.dyhack.barvisual.resp.ProvinceFloatCountResp;
import cn.dyhack.barvisual.resp.TotalByTime;

@Service("TotalsServiceImpl")
public class TotalsServiceImpl {
    
    @Autowired
    TotalMapperImpl totalMapper;
    
    @Autowired
    BarsServiceImpl barsService;
    
    @Autowired
    PersonsServiceImpl personsService;
    
    
    @Autowired
    ProvinceIdServiceImpl provinceIdService;
    
    private List<TotalByTime> totals;
    
    private List<TotalByTime> tempTotals;
    
    private List<Persons> underAudits = new ArrayList<>();
    
    private List<Persons> persons = new ArrayList<>();
    
    private HashSet<String> persinIds = new HashSet<>();
    
    private HashMap<String, Persons> barIdPerson = new HashMap<>();
    
//    private List<TotalByTime> totalsByTime;
//    
//    private List<TotalByTime> tempTotalsByTime;
//    
    private List<Bars> bars;
    
    private HashMap<String,String> barIdNameSet = new HashMap<>() ;
    
    /**
     * 格式<年龄,上网人员集合>
     */
     
    HashMap<Long, List<TotalByTime>> ageMap = new HashMap<Long,List<TotalByTime>>();
    
    /**
     * 格式<上网时间,上网人员集合>
     */
    HashMap<Long, List<TotalByTime>> timeMap = new HashMap<Long,List<TotalByTime>>();
    
    HashMap<String,Long> internetTimeMap = new HashMap<>();
    
    int [][]ageTime;
    
    /**
     * 
     * 默认最先加载total表里面的数据到内存中，方便后面的计算
     *
     * @author zhangke
     * @since 0.0.1
     */
    @PostConstruct
    private void preLoadTotals()
    { 
        persons = personsService.selectAll();
        Set<Long> internetTime = new HashSet<>();
        for(int i=0;i<=100;i++)
        {
            ageMap.put((long)i,new ArrayList<>());
        }
        for(int i=1;i<=216;i++)
        {
            timeMap.put((long)i,new ArrayList<>());
            internetTime.add((long)i);
        }
       this.totals = totalMapper.selectAllTime();
       System.out.println("====>初始化开始"+totals.size());
       bars = barsService.selectAll();
       //通过他的年龄分段
       //通过他的上网时间程度分段
       for(Bars bar:bars)
       {
          barIdNameSet.put(bar.getId(), bar.getName());
       }
       for(TotalByTime t:totals)   
       {
           
           //将表里的日期字段变成年龄(计算从上线时间到出生日期的年龄)
           t.setAge((long)getAgeFromBirthTime(t.getOnlinetime(),Long.valueOf(t.getBirthday())));
           //上网时间
           long barsTime = (long)Math.ceil((double)t.getInternet_time()/600);
           //时间为216个间隔,每10分钟一个间隔，一共1.5天
           if(barsTime<1)
           {
               barsTime =1;
           }
           if(barsTime>216)
           {
               barsTime = 216;
           }
           if(internetTime.contains(barsTime))
           {
               timeMap.get(barsTime).add(t);
           }
           else
           {
               System.err.println("=》》》》》》》》》出现不存在的上网时间，有毒");
           }
           if(t.getAge()>100)
           {
               System.out.println();
               ageMap.get(Long.valueOf(100)).add(t);
               }
           else {
           ageMap.get(t.getAge()).add(t);}
          
        }
           
       System.out.println("====>获取网吧数据读入内存中");
       
       
       System.out.println("====>初始化加载完成");
    }
    public List<Total> selectBycondition(String filter)
    {
        return totalMapper.selectTotalByCondition(filter);
    }
    
    public List<TotalByTime> filterByCondition(String barIds,long startTime,long endTime,List<InternetUserFilterBean> ageAndTimes){
    	return filterByCondition(barIds, startTime, endTime, ageAndTimes, 0);
    }
    
    public List<TotalByTime> filterByCondition(String barIds,long startTime,long endTime,List<InternetUserFilterBean> ageAndTimes, int type)
    {
        List<TotalByTime> tempTotalByTime = new ArrayList<>();
        
        //构建网吧列表
        String barIdArray[] = barIds.split(",");
        HashSet<String> barIdSet = new HashSet(Arrays.asList(barIdArray));
        
        //构建年龄和上网时间MAP
        HashMap<Integer, InternetUserFilterBean> ageAndTimeMap = new HashMap<Integer, InternetUserFilterBean>();
        for(InternetUserFilterBean ageAndTime : ageAndTimes) {
        	ageAndTimeMap.put(ageAndTime.getAge(), ageAndTime);
        }
        
        
        for (TotalByTime t : totals) {
        	if(type == 0) { //0就是全部
        		
        	} else if(type == 1) { //1是未成年
        		if(t.getAge() >= 18) {
        			continue;
        		}
        	} else if(type == 2){
        		if(t.getFloatPopulation() != 1) {
        			continue;
        		}
        	}
        	
        	//判断网吧是否符合条件
        	if(barIds.length() > 0) {
        		if(!barIdSet.contains(t.getBarid())) {
        			continue;
        		}
        	}
        	
        	//判断时间是否符合条件
        	if(startTime != 0 && endTime != 0) {
        		if(t.getOnlinetime() >= endTime || t.getOfflinetime() <= startTime) {
        			continue;
        		}
        	}
        	
        	//判断年龄和上网时长是否符合
        	if(ageAndTimes.size() > 0) {
        		int age = t.getAge().intValue();
            	if(!ageAndTimeMap.containsKey(age)) {
            		continue;
            	}
            	InternetUserFilterBean ageAndTime = ageAndTimeMap.get(age);
            	
            	boolean result = false;
            	for(int[] time : ageAndTime.getTimes()) {
            		int minTime = time[0];
            		int maxTime = time[1];
            		if(t.getInternet_time() > minTime && t.getInternet_time() < maxTime) {
            			result = true;
            			break;
            		}
            	}
                if(!result) continue;
        	}
        	
            
            tempTotalByTime.add(t);
        }
       
        
        System.out.println("=====>过滤获得:" + tempTotalByTime.size());
        return tempTotalByTime;
    }
    
    /**
     * 
     * 找到直接使用未成年人身份证的网吧(年龄距离上网小于18周岁)
     *
     * @return total 列表
     * @author zhangke
     * @since 0.0.1
     */
    public List<Total> selectAudit()
    {
        String filter = "TIMESTAMPDIFF(\r\n" + 
                "        YEAR,\r\n" + 
                "        FROM_UNIXTIME(\r\n" + 
                "            birthday,\r\n" + 
                "            '%Y-%m-%d %H:%i:%s'\r\n" + 
                "        ),\r\n" + 
                "        FROM_UNIXTIME(onlinetime,'%Y-%m-%d %H:%i:%s')\r\n" + 
                "    ) <18";
        return totalMapper.selectTotalByCondition(filter);
    }
    
    public List<Total> getFloatingPopulation(String fields, String filters, int offset, int limit, String sorts)
    {
        return totalMapper.getFloatPopleBar(fields, filters, offset, limit, sorts);
    }
    
    /**
     * 
     * 设置流动人口
     *
     * @return 返回流动人口的详细信息
     * @author zhangke
     * @since 0.0.1
     */
    public void setFloatingPopulation()
    {
        List<Total> persons =  totalMapper.selectFloatPopulation();
        for(Total person:persons)
        {
            totalMapper.setFloat(person);
        }
    }
    
    /**
     * 
     * 通过时间查找上网时间在当前时间里面的用户
     * 如果上线时间不在筛选时间段里
     *
     * @param startTime 筛选的开始时间段(时间戳格式)
     * @param endTime 筛选的结束时间段(时间戳格式)
     * @return 在这个时间段里面上网的用户
     * @author zhangke
     * @since 0.0.1
     */
    
    public List<Total> selectUsersByTime(Long startTime, Long endTime) {
        return totalMapper.selectTotalByCondition(TOTAL.ONLINETIME.between(startTime, endTime)
                .or(TOTAL.ONLINETIME.between(startTime, endTime)
                        .or(TOTAL.ONLINETIME.lessOrEqual(startTime)
                                .and(TOTAL.OFFLINETIME.greaterOrEqual(endTime)))
                        .or(TOTAL.ONLINETIME.greaterOrEqual(startTime)
                                .and(TOTAL.OFFLINETIME.lessOrEqual(endTime))))
                .toString());
    }
    
    /**
     * 通过时间间隔，获取在这个时间段里面的上网人数
     *
     * @param startTime 时间间隔(开始时间)
     * @param endTime 时间间隔(结束时间)
     * @param interval 时间间隔,以秒为单位
     * @return InternetUsersCount列表
     * @author zhangke
     * @since 0.0.1
     */
 
    public List<InternetUsersCount> selectAllByTimeSplit(long startTime,long endTime,long interval,String barIds, List<InternetUserFilterBean> ageTime, int type)
    {   
    	List<TotalByTime> tempTotals = filterByCondition(barIds, startTime, endTime, ageTime, type);
    	
        System.out.println("===========>进入接口"+ "-----" + new Date().toGMTString());
//        if(barIds == null)
//        {
//            tempTotals = totals;
//        }
//        else
//        {
//            String barIdArray[] = barIds.split(",");
//            HashSet<String> bars = new HashSet<String>();
//            tempTotals = totals;
//            for(String barId : barIdArray){
//                bars.add(barId);
//            }
//            
//            List<TotalByTime> temp = new ArrayList<>();
//            for(TotalByTime t:tempTotals)
//            {
//                if(bars.contains(t.getBarid())) {
//                    temp.add(t);
//                }
//            }
//            tempTotals = temp;
//        }
        System.out.println("===========>加载完成"+ "-----" + new Date().toGMTString());
        long blocks = new Double(Math.ceil(((endTime - startTime) / (interval * 1.0)))).longValue();
        List<InternetUsersCount> results = new ArrayList<>();
        List<Integer> onarr = new ArrayList<Integer>();
        List<Integer> offarr = new ArrayList<Integer>();
        
        List<Integer> unadult_countonarr = new ArrayList<Integer>();
        List<Integer> unadult_countoffarr = new ArrayList<Integer>();
        for(int i = 0; i < blocks; i++) {
            onarr.add(0);
            offarr.add(0);
            unadult_countonarr.add(0);
            unadult_countoffarr.add(0);
            results.add(new InternetUsersCount());
        }
        System.out.println("===========>处理完成"+ "-----" + new Date().toGMTString());
        //实行前端传入网吧id动态刷新
        for(TotalByTime t: tempTotals) {
            long on = t.getOnlinetime();
            long off = t.getOfflinetime();
            int start = new Double(Math.floor(((on - startTime) / (interval * 1.0)))).intValue();
            int end = new Double(Math.floor(((off - startTime) / (interval * 1.0)))).intValue();
            if(start >= blocks || end < 0) continue;
            if(start < 0) start = 0;
            if(end >= blocks) end = (int)blocks - 1;

            onarr.set(start, onarr.get(start) + 1);
            offarr.set(end, offarr.get(end) + 1);
            
            if(t.getAge() < 18) {
            	unadult_countonarr.set(start, unadult_countonarr.get(start) + 1);
                unadult_countoffarr.set(end, unadult_countoffarr.get(end) + 1);
            }
        }
        
        int count = 0;
        int unadult_count = 0;
        for(int i = 0; i < blocks; i++) {
            if(i > 0){
                count-=offarr.get(i - 1);
                unadult_count-=unadult_countoffarr.get(i - 1);
            }
            count+=onarr.get(i);
            unadult_count+=unadult_countonarr.get(i);
            
            results.set(i,new InternetUsersCount(startTime+(interval*(i)), startTime+(interval*(i+1)), count, unadult_count));
        }
            

        System.out.println("===========>完成" + "-----" + new Date().toGMTString());
        return results;
    } 
    
    /**
     * 
     * 获取用户的上网时间和上网年龄 以及在这2个参数的上网人数
     *
     * @author zhangke
     * @throws Exception 
     * @since 0.0.1
     */
    public List<AgeAndTimeResp> selectInternetAgeAndTime(Long startTime,Long endTime,int maxInternetTime, int interval, String barIds, int type)
            throws Exception {
//        boolean fiilterTime = true;
//        if(startTime==null||endTime==null)
//        {
//            fiilterTime = false;
//        }
//        List<TotalByTime> tempTotalsByTime = totals;
//    
//        if (barIds == null) {
//
//        } else {
//            String barIdArray[] = barIds.split(",");
//            List<TotalByTime> temp = new ArrayList<>();
//            for (TotalByTime t : tempTotalsByTime) {
//                for (String s : barIdArray) {
//                    if (t.getBarid()
//                            .equals(s)) {
//                        if(fiilterTime&&!(t.getOnlinetime() >= endTime || t.getOfflinetime() <= startTime))
//                        {
//                        temp.add(t);
//                        }
//                    }
//                }
//            }
//            tempTotalsByTime = temp;
//        }
        List<TotalByTime> tempTotalsByTime = filterByCondition(barIds, startTime, endTime, new ArrayList<InternetUserFilterBean>(), type);
        
        System.out.println(tempTotalsByTime.size());
        
        List<AgeAndTimeResp> resultList = new ArrayList<>();
        int splits = maxInternetTime / interval;
        ageTime = new int[ageMap.keySet()
                .size() + 1][splits + 1];
        for (TotalByTime t : tempTotalsByTime) {
            int tempAge = t.getAge()
                    .intValue();
            int tempTime = (int) Math.ceil((double) t.getInternet_time() / interval);
            if (tempAge >= 50) {
                if (tempAge >= 200) {
                    // tempTotalsByTime.remove(t);
                    System.out.println("存在大于200岁的老妖怪");
                }
                if (tempAge >= 100) {
                    tempAge = 100;
                }
            }
            if (tempTime >= splits) {
                tempTime = splits;
            }
            ageTime[tempAge][tempTime]++;
        }

        for (int i = 0; i <= 100; i++) {
            for (int j = 0; j <= splits; j++) {
                resultList.add(new AgeAndTimeResp(i, j, ageTime[i][j]));
            }
        }
        return resultList;
    }
    
    
 // 根据时间戳计算年龄
    public int getAgeFromBirthTime(long onlinetime,long birthTimeLong) {
        try {
        Date birthday=null;
        if(birthTimeLong<0)
        {
            String result2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(birthTimeLong*1000);
            try {
                birthday = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(result2);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        else
        {
            birthday = new Date(birthTimeLong*1000);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(onlinetime*1000));
        if (cal.before(birthday)) { // 出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR); // 当前年份
        int monthNow = cal.get(Calendar.MONTH); // 当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); // 当前日期
        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth; // 计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth)
                    age--;// 当前日期在生日之前，年龄减一
            } else {
                age--;// 当前月份在生日之前，年龄减一

            }
        }
        return age;
    }catch (Exception e) {
        System.err.println(e.getMessage());
        return 0;
    }
    }
    
    public List<BarRelevantResp> getBarRelevantResp(long startTime,long endTime,String barIds, List<InternetUserFilterBean> ageTime, int type) {
		   List<TotalByTime> tempTotalsByTime = filterByCondition(barIds, startTime, endTime, ageTime, type);
		
		   HashMap<String,BarRelevantResp> tempBars = new HashMap();
		   for(Bars b:bars)
		   {
		       tempBars.put(b.getId(),new BarRelevantResp(b.getId(),0,0));
		   }
		   for(TotalByTime t: tempTotalsByTime)
		   {
		       if(tempBars.get(t.getBarid())==null)
		       {
		           continue;
		       }
		       //获取所有的过滤开始时间，结束时间，上网最大年龄，上网最小年龄，上网时长数据
		       BarRelevantResp bar = tempBars.get(t.getBarid());
		       if(t.getFloatPopulation()==1)
		       {
		           bar.setFloat_count(bar.getFloat_count() + 1);
		       }
		       
		       if(t.getAge() < 18) {
		    	   bar.setUnder_age_count(bar.getUnder_age_count() + 1);
		       }
		       
		       bar.setCount(bar.getCount() + 1);
		   }
		   Collection<BarRelevantResp> values = tempBars.values() ;// 得到全部的value
		   return new ArrayList<>(values);

    }
    
    
    /**
     * 获取网民年龄统计
     * @return
     */
    public List<AgeCount> selectAgeCount(long startTime,long endTime,long interval,String barIds, List<InternetUserFilterBean> ageTime, int type){
        //TODO: 
        List<TotalByTime> safeTotals = filterByCondition(barIds, startTime, endTime, ageTime, type);
        
        List<AgeCount> results = new ArrayList<AgeCount>();
        int blocks = (int) (100/interval);
        for(int i = 0; i <= blocks; i++) {
            results.add(new AgeCount(i*interval, 0));
        }
        
        for(TotalByTime total: safeTotals) {
            int age = total.getAge().intValue();
            if(age > 100) age = 100;
            if((age)/interval<=blocks) age = (int) (age/interval); 
            results.get(age).increase();
        }
        
        return results;
    }
    
    /**
     * 获取网民上网时间分布
     * @return
     */
    
    public List<Long> queryInternetTimeDistribution(long startTime, long endTime,String barIds, List<InternetUserFilterBean> ageTime,int type){
        
        //7*24段从星期一开始
        List<Long> results = new ArrayList<Long>();
        for(int i = 0; i < 7 * 24; i++) {
            results.add(new Long(0));
        }
        
        Date startDate = new Date(startTime*1000);
        int startDay = (startDate.getDay() + 6) % 7 + 1;
        int startHour = startDate.getHours();
        
        startTime = startTime - startDate.getMinutes() * 60 - startDate.getSeconds();
        
        List<InternetUsersCount> counts = selectAllByTimeSplit(startTime, endTime, 3600, barIds, ageTime, type);
        
        int index = (startDay - 1) * 24 + startHour;
        for (InternetUsersCount count: counts) {
            results.set(index, results.get(index) + count.getCount());
            index = (index + 1) % (7 * 24);
//          System.out.println("====>" + index + ":  " + (Math.floor(index / 24) + 1) + "-" + (index % 24) + "---" + results.get(index) + "---" + count.getCount() );
        }
        
        return results;
    }
    
	/**
	 * 
	 * 获取省份和对应的流动人口的数量
	 *
	 * @return 返回省份名称:流动人口的数量
	 * @author zhangke
	 * @since 0.0.5
	 */
    public HashMap<String,Integer> getFloatPersonByProvince(long startTime, long endTime,String barIds, List<InternetUserFilterBean> ageTime,int type) {
    	
    	List<TotalByTime> safeTotals = filterByCondition(barIds, startTime, endTime, ageTime, type);
    	
        ProvinceFloatCountResp provinceFloatCountResp = new ProvinceFloatCountResp();
        ProvinceCount provinceCount = new ProvinceCount();
        HashMap<Integer, String> provinceId = new HashMap<>();
        List<ProvinceId> provinceIds = provinceIdService.getAll();
        HashMap<String,Integer> idCount = new HashMap<>();
        for (ProvinceId p : provinceIds) {
            provinceId.put(p.getProvinceId(), p.getProvinceName());
            idCount.put(p.getProvinceName(), 0);
        }
        for (TotalByTime t : safeTotals) {
            int count ;
            int proviId;
            //如果为流动人口
            if(t.getFloatPopulation() ==1)
            {   //获取省份id
                proviId = Integer.valueOf(t.getAreaid().toString().substring(0,2));
                if (provinceId.containsKey(proviId)) {
                    count =  idCount.get(provinceId.get(proviId));
                    idCount.put(provinceId.get(proviId),++count);
                }
                
            }else
            {
                
                count =  idCount.get(provinceId.get(50));
               idCount.put(provinceId.get(50), ++count);
            }
        }
        return idCount;
    }
    
    public HashMap<String,ExportData> exportData(long startTime, long endTime,String barIds, List<InternetUserFilterBean> ageTime)
    {
    	persinIds.clear();
    	underAudits.clear();
        //构建网吧列表
    	Map<String,Bars> barsMap = new HashMap<String,Bars>();
		 for(Bars bar:bars)
	     {
			 barsMap.put(bar.getId(), bar);
	     }
        //已经通过过滤条件过滤的数据
        List<TotalByTime> safeTotals = filterByCondition(barIds, startTime, endTime, ageTime);
        HashMap<String,ExportData> exportDatas = new HashMap<>();
        
        for(TotalByTime t:safeTotals)
        {
            String barId = t.getBarid();
            ArrayList<String> tempPersonIds = new ArrayList<>();
            ExportData tempExportData = null;
            if(exportDatas.containsKey(barId)) {
            	tempExportData = exportDatas.get(barId);
            } else {
            	tempExportData = new ExportData();
            	tempExportData.setBarId(barId);
            	if(barsMap.containsKey(barId)) {
            		tempExportData.setBarName(barsMap.get(barId).getName());            		
            	} else {
            		tempExportData.setBarName("未记录的网吧");
            	}
            	exportDatas.put(barId, tempExportData);
            }
            
            int underAudit = tempExportData.getUnderAudit();
            int suspectIdNums = tempExportData.getSuspectIdNums();
            if(t.getAge()<18)
            {
                tempExportData.setUnderAudit(++underAudit);
                if(persinIds.contains(t.getPersonid()))
                {
                }
                else
                {
                    persinIds.add(t.getPersonid());
                }
            }
            tempExportData.setSuspectIdNums(++suspectIdNums);
        }
        
        
        return exportDatas;
    }
    
    public List<Persons> exportAuditData()
    {
       List<Persons> underAudits = new ArrayList<>();
    	
       for(Persons perosn:persons)
       {
           if(persinIds.contains(perosn.getId()))
           {
               underAudits.add(perosn);
           }
       }
       return underAudits;
    }
    
    
}
