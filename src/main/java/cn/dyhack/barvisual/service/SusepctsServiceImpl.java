package cn.dyhack.barvisual.service;

import static cn.dyhack.barvisual.pojo.tables.Records.RECORDS;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dyhack.barvisual.dao.RecordMapperImpl;
import cn.dyhack.barvisual.dao.SuspectMapperImpl;
import cn.dyhack.barvisual.pojo.tables.pojos.Suspects;
import cn.dyhack.barvisual.pojo.tables.pojos.Records;
import cn.dyhack.barvisual.pojo.tables.pojos.RecordsPersons;
import cn.dyhack.barvisual.pojo.tables.pojos.Persons;
import static cn.dyhack.barvisual.pojo.Tables.PERSONS;

/**
 * 
 * suspects服务层
 *
 * @author zhangke
 * @since 0.0.1
 */
@Service("SusepctsServiceImpl")
public class SusepctsServiceImpl {

    @Autowired
    SuspectMapperImpl suspectMapper;

    @Autowired
    RecordMapperImpl recordMapper;

    @Autowired
    BarsServiceImpl barsService;
    
    @Autowired
    PersonsServiceImpl personsService;
    
    @Autowired
    RecordsPersonsServiceImpl recordsPersonsService;
    
    /**
     * 
     * 通过上网时间来查找非法网吧
     *
     * @author zhangke
     * @since 0.0.1
     */
    public void getSusepctBarsByTime()
    {
        List<Records> barRecords = recordMapper.selectByCondition(RECORDS.OFFLINETIME.sub(RECORDS.ONLINETIME).greaterThan(3600L*24*3).toString());
        personsService.getSuitedPerson(barRecords);
        
    }
    
    /**
     * 
     * 找到使用未成年人信息的网吧
     * 并存入到csv文件中
     * @author zhangke
     * @since 0.0.1
     */
    public void getAuditToCsv()
    {
        Set<String> barSet = new HashSet<>();
        //查找未成年人
        List<RecordsPersons> persons = recordsPersonsService.selectAll();
        for(RecordsPersons person:persons)
        {   
            barSet.add(person.getBarid());
        }
        System.out.println(barSet.size());
    }

    /**
     * 
     * 获取黑网吧
     *
     * @author zhangke
     * @throws Exception 
     * @since 0.0.1
     */
    public void getSuspectBars() throws Exception {
        int result= 0;
        // 判断如果嫌疑人只在一个网吧上网并且在
        // 周一到周五以及周末时间上网，并在这些时间段上网占比
        // 达80%以上，则视为嫌疑身份证
         List<Suspects> suspects = suspectMapper.selectByconditon("count >= 5 ");
        // 判断这些只在网吧上网5次及以上的人
        for (Suspects su : suspects) {
            List<Records> barrecords = recordMapper.selectByCondition(RECORDS.PERSONID.eq(su.getPersonid()).toString());
            if(barrecords==null)
                continue;
            Set<String> barId = new HashSet<>();
            for(Records re:barrecords)
            {
                barId.add(re.getBarid());
            }
            if(barId.size() != 1) {
            continue;}
            if (barrecords.size() == su.getCount()&&chargeDatetime(barrecords)) {
                barsService.updateIllegalBar(barrecords.get(0)
                        .getBarid());
                result++;
            }
        }
        System.out.println("==========>:"+result);

    }

    /**
     * 
     * 判断是否在以下时间范围内并且占比达到总上网次数的80%
     *
     * @throws (可填，此方法需要抛出的异常描述，格式:
     *             "@throws 异常类名 描述信息").
     * @see (可填，引用或参考其他方法资源，如: {@link java.lang.Integer#decode}).
     * @author zhangke
     * @throws Exception 
     * @since (可填，chargeDatetime方法定义开始的版本，应与定义时的pom.xml版本一致)
     */
    private boolean chargeDatetime(List<Records> barRecords) throws Exception {
        double num = 0;
        for (Records re : barRecords) {
            if (stampToDate(re.getOnlinetime()) >= 12 && stampToDate(re.getOnlinetime()) <= 14
                    || stampToDate(re.getOnlinetime()) >= 18 && stampToDate(re.getOnlinetime()) <= 22
                    ||(dayForWeek(re.getOnlinetime())>=5&&dayForWeek(re.getOnlinetime())<=7
                    &&dayForWeek(re.getOfflinetime())>=5&&dayForWeek(re.getOfflinetime())<=7
                    )) {
                num++;   
            }

        }
        if(num/barRecords.size()>=0.8)
        {
            return true;
        }
        return false;
    }

    /*
     * 将时间戳转换为时间
     */
    private int stampToDate(Long s) {
        int res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        Date date = new Date(s*1000);
        res = Integer.valueOf(simpleDateFormat.format(date));
        return res;
    }

    /**
     * 判断当前日期是星期几
     * 
     * @param pTime
     *            修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    private  int dayForWeek(Long pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(stampToDate1(String.valueOf(pTime))));
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }
    

    /* 
     * 将时间戳转换为时间
     */
    private  String stampToDate1(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt*1000);
        res = simpleDateFormat.format(date);
        return res;
    }
    
    
    // 根据时间戳(秒)计算2016年时间的年龄年龄
    private int getAgeFromBirthTime(long birthTimeLong) {
        Date birthday = new Date(birthTimeLong * 1000);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(116, 10, 01));
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
    }

    }

