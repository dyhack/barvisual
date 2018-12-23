package cn.dyhack.barvisual.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dyhack.barvisual.dao.PersonMapperImpl;
import cn.dyhack.barvisual.pojo.tables.pojos.Persons;
import cn.dyhack.barvisual.pojo.tables.pojos.Records;

@Service("PersonsServiceImpl")
public class PersonsServiceImpl {

    @Autowired
    PersonMapperImpl personMapper;
    
    @Autowired
    BarsServiceImpl barsService;
    
    
    public List<Persons> selectAll()
    {
        return personMapper.selectAll();
    }
    
    /*
     * 找到persons表里面的未成年人
     * */
    
    public List<Persons> selectAudit()
    {
        return personMapper.selectAudit();
    }
    public List<Persons> selectByCondition(String filter)
    {
       return personMapper.selectByConditon(filter);
    }

    public void getSuitedPerson(List<Records> barRecords) {
        for (Records re : barRecords) {
            Persons person = personMapper.selectPersonsById((re.getPersonid()));
            if(person!=null)
            {
                if(getAgeFromBirthTime(person.getBirthday())>=40)
                {
                    barsService.updateIllegalBar(re.getBarid(), 10);
                }
            }else
            {
                System.err.println("查询不到personid:"+re.getPersonid());
            }
        }
    }
    

    
 // 根据时间戳计算年龄
    private int getAgeFromBirthTime(long birthTimeLong) {
        Date birthday = new Date(birthTimeLong * 1000);
        Calendar cal = Calendar.getInstance();
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


