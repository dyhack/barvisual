package cn.dyhack.barvisual.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.JoinType;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectQuery;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.signit.wesign.lib.common.Validator;
import cn.signit.wesign.lib.common.string.Case;
import cn.signit.wesign.lib.common.type.NamingStyle;
import cn.signit.wesign.mss.common.pojo.po.DistinctCondition;
import cn.signit.wesign.mss.common.pojo.po.SelectConditions;
import cn.signit.wesign.mss.common.util.http.HttpCaseFormater;

/**
 * mapper常用操作封装.
 *
 * @author zhangbo
 * @since 1.0.0
 */
public class CommonMapper {
    private static final Logger LOG = LoggerFactory.getLogger(CommonMapper.class);
    private static final Pattern DOUBLE_QUOTATION_MARK = Pattern.compile("\"");
    private static final Pattern NON_PRINT_CHARACTERS = Pattern.compile("[\t\r\n]");
    private static final Pattern DATABASE_TABLE_COLUMN = Pattern.compile("(?<==|<|>| |^)(\\w+)\\.(\\w+)\\.(\\w+)");
    private static final Pattern TABLE_COLUMN = Pattern
            .compile("(?<=^|&|,|\\||=|<|>|\\(|(?i)and|(?i)or)(\\w+)\\.(\\w+)\\b(?!@)");
    private static final Pattern LEFT_KEY = Pattern.compile("(\\w+)(?=\\s*[=!<>])");
    private static final Pattern RIGHT_VALUE = Pattern
            .compile("(?<=(?:=|<|>)\\s{0,50}\\b)(.+?)(?=\\s*(?:,|&|\\||\\)|(?i)\\s+and\\s+|(?i)\\s+or\\s+|$))");
    private static final Pattern LIKE_IN_NOTIN = Pattern
            .compile("(?<=[,|]\\s{0,50})(\\w+)(?=\\s+(?i)(like|(?:(NOT\\s+)*in)))");
    private static final Pattern BETWEEN_VALUE1 = Pattern
            .compile("(?<=\\s{1,50}(?i)between\\s{1,50})([^' ]+?)(?=\\s+(?i)and\\s+)");
    private static final Pattern BETWEEN_VALUE2 = Pattern.compile(
            "(?<=\\s{1,50}between\\s{1,50}.{1,1000}?\\s(?i)and\\s{1,50})([^' ]+?)(?=\\s*(,|&|\\||\\)|(?i)\\s+and\\s+|(?i)\\s+or\\s+|$))");
    private static final Pattern AND = Pattern.compile("\\s*[&,]\\s*");
    private static final Pattern OR = Pattern.compile("\\s*\\|\\s*");
    private static final Pattern IN_OP = HttpCaseFormater.IN_OP_PATTERN;
    private static final Pattern SPECIAL_KEYWORDS = Pattern.compile("(?:\\s*\\^\\s*|\\s+(?i)AND\\s+)");
    private static final Pattern MULTIPLE_BACKQUOTES = Pattern
            .compile("(?<=(=|<|>)\\s{0,50})('+)((?i)X'[0-9a-fA-F]+')(\\2)");
    @Autowired(required = false)
    private DSLContext dsl;
    private static final int DEFAULT_LIMIT = 20;
    private static final int DEFAULT_OFFSET = 0;
    private static NamingStyle DEFAULT_FIELD_CASE = NamingStyle.SNAKE;
    protected static final String HASH = "T(org.springframework.data.redis.core.script.DigestUtils).sha1DigestAsHex";

    /**
     * 重新设置sql字段命名风格.
     *
     * @param namingStyle
     *            新的命名风格
     * @return 当前对象
     * @author zhd
     * @since 1.0.0
     */
    protected synchronized CommonMapper resetFieldNamingStyle(NamingStyle namingStyle) {
        if (namingStyle != null) {
            DEFAULT_FIELD_CASE = namingStyle;
        }
        return this;
    }

    /**
     * 查询表记录.
     *
     * @param table
     *            查询的表
     * @param fields
     *            返回数据包含字段；为空值或null的时候返回所有字段
     * @param filters
     *            条件过滤"key=value"的csv格式
     * @param limit
     *            分页参数，当前数据的页大小，默认为20
     * @param offset
     *            分页参数，当前数据的偏移量，默认为0
     * @param sorts
     *            排序，例如："+id"（按id字段升序）或者"-cost"（按cost字段降序）
     * @return 成功返回查询的记录，失败返回空值[];
     * @author zhangbo
     * @since 1.0.0
     */
    protected Result<Record> selectRecords(Table<?> table, String fields, String filters, Integer offset, Integer limit,
            String sorts,String orderBy) {
        if (Validator.isEmpty(filters) || Validator.isEmpty(filters.trim())) {
            Condition nullCondition = null;
            return selectRecords(table, fields, nullCondition, offset, limit, sorts,orderBy);
        } else {
            String formatedFilters = filtersFormatFactory(filters);
            formatedFilters = dateFormat(table, formatedFilters);
            return selectRecords(table, fields, DSL.condition(formatedFilters), offset, limit, sorts,orderBy);
        }
    }

    /**
     * 查询表记录.
     *
     * @param table
     *            查询的表
     * @param fields
     *            返回数据包含字段；为空值或null的时候返回所有字段
     * @param filters
     *            条件过滤"key=value"的csv格式
     * @param limit
     *            分页参数，当前数据的页大小，默认为20
     * @param offset
     *            分页参数，当前数据的偏移量，默认为0
     * @param sorts
     *            排序，例如："+id"（按id字段升序）或者"-cost"（按cost字段降序）
     * @return 成功返回查询的记录，失败返回空值[];
     * @author zhangbo
     * @since 1.0.0
     */
    protected Result<Record> selectRecords(Table<?> table, String fields, Condition filters, Integer offset,
            Integer limit, String sorts,String groupBy) {
        SelectQuery<Record> selectQuery = buildSelectQuery(table, fields, filters, offset, limit, sorts,groupBy);
        return selectQuery.fetch();
    }

    /**
     * 查询表记录.
     * 
     * @param targetPOClass
     *            需要返回的查询列表中的目标对象
     * @param table
     *            查询的表
     * @param fields
     *            返回数据包含字段；为空值或null的时候返回所有字段
     * @param filters
     *            条件过滤"key=value"的csv格式
     * @param limit
     *            分页参数，当前数据的页大小，默认为20
     * @param offset
     *            分页参数，当前数据的偏移量，默认为0
     * @param sorts
     *            排序，例如："+id"（按id字段升序）或者"-cost"（按cost字段降序）
     * @return 成功返回查询的记录，失败返回空值[];
     * @author zhd
     * @since 1.0.0
     */
    protected <T> List<T> selectRecords(Class<?> targetPOClass, Table<?> table, String fields, String filters,
            Integer offset, Integer limit, String sorts,String groupBy) {
        if (Validator.isEmpty(filters) || Validator.isEmpty(filters.trim())) {
            Condition nullCondition = null;
            return selectRecords(targetPOClass, table, fields, nullCondition, offset, limit, sorts,groupBy);
        } else {
            String formatedFilters = filtersFormatFactory(filters);
            formatedFilters = dateFormat(table, formatedFilters);
            return selectRecords(targetPOClass, table, fields, DSL.condition(formatedFilters), offset, limit, sorts,groupBy);
        }
    }

    /**
     * 查询表记录.
     * 
     * @param targetPOClass
     *            需要返回的查询列表中的目标对象
     * @param table
     *            查询的表
     * @param fields
     *            返回数据包含字段；为空值或null的时候返回所有字段
     * @param filters
     *            条件过滤"key=value"的csv格式
     * @param limit
     *            分页参数，当前数据的页大小，默认为20
     * @param offset
     *            分页参数，当前数据的偏移量，默认为0
     * @param sorts
     *            排序，例如："+id"（按id字段升序）或者"-cost"（按cost字段降序）
     * @return 成功返回查询的记录，失败返回空值[];
     * @author zhd
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> selectRecords(Class<?> targetPOClass, Table<?> table, String fields, Condition filters,
            Integer offset, Integer limit, String sorts,String groupBy) {
        SelectQuery<Record> selectQuery = buildSelectQuery(table, fields, filters, offset, limit, sorts,groupBy);
        return (List<T>) selectQuery.fetchInto(targetPOClass);
    }

    protected SelectQuery<Record> buildSelectQuery(Table<?> table, String fields, Condition filters, Integer offset,
            Integer limit, String sorts,String groupBy) {
        String tempFields = fields;
        SelectQuery<Record> selectQuery = dsl.selectQuery();
        if (tempFields != null && !tempFields.trim().isEmpty()) {
            tempFields = fieldsFormat(tempFields);
            for (String field : tempFields.split(",")) {
                if (table.field(field.trim()) != null) {
                    selectQuery.addSelect(table.field(field.trim()));
                }
            }
        }
        selectQuery.addFrom(table);
        if (filters != null) {
            selectQuery.addConditions(filters);
        }
        selectQuery.addLimit(offsetFormat(offset), limitFormat(limit));
        if (sorts != null && !sorts.trim().isEmpty()) {
            selectQuery.addOrderBy(sortsFormat(sorts));
        }
        if (groupBy != null && !groupBy.trim().isEmpty()) {
            selectQuery.addGroupBy(table.field(groupBy));
        }
        return selectQuery;
    }

    /**
     * 可选字段方式，条件查询一条或多条记录.
     *
     * @param table
     *            表
     * @param fields
     *            返回数据包含字段；为空值的时候返回所有数据
     * @param conditions
     *            条件
     * @return 成功返回查询的记录，失败返回空值;
     * @author zhangbo
     * @since 1.0.0
     */
    protected Result<Record> selectRecordsByConditionsWithFields(Table<?> table, String fields, Condition conditions,String groupBy) {
        SelectQuery<Record> selectQuery = buildSelectQuery(table, fields, conditions);
        return selectQuery.fetch();
    }

    /**
     * 可选字段方式，条件查询一条或多条记录.
     * 
     * @param targetPOClass
     *            需要返回的查询列表中的目标对象
     * @param table
     *            表
     * @param fields
     *            返回数据包含字段；为空值的时候返回所有数据
     * @param conditions
     *            条件
     * @param <T>
     *            成功返回查询的记录列表类型
     * @return 成功返回查询的记录，失败返回空值;
     * @author zhd
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> selectRecordsByConditionsWithFields(Class<?> targetPOClass, Table<?> table, String fields,
            Condition conditions) {
        SelectQuery<Record> selectQuery = buildSelectQuery(table, fields, conditions);
        return (List<T>) selectQuery.fetchInto(targetPOClass);
    }

    protected SelectQuery<Record> buildSelectQuery(Table<?> table, String fields, Condition conditions) {
        String tempFields = fields;
        SelectQuery<Record> selectQuery = dsl.selectQuery();
        if (tempFields != null && !tempFields.trim().isEmpty()) {
            tempFields = fieldsFormat(tempFields);
            for (String field : tempFields.split(",")) {
                if (table.field(field.trim()) != null) {
                    selectQuery.addSelect(table.field(field.trim()));
                }
            }
        }
        selectQuery.addFrom(table);
        if (conditions != null) {
            selectQuery.addConditions(conditions);
        }
        return selectQuery;
    }

    /**
     * 
     * 联合多个查询条件封装,并执行查询.
     *
     * @param selectQuerys
     *            多个查询查询条件封装
     * @return 联合查询执行结果
     * @author pxy
     * @since 1.0.0
     */
    @SafeVarargs
    protected final Result<Record> unionRecord(Field<?>[] fields, Integer offset, Integer limit, String sorts,
            SelectQuery<Record>... selectQuerys) {
        return unionSelectQuery(fields, offset, limit, sorts, Arrays.asList(selectQuerys)).fetch();
    }

    /**
     * 
     * 联合多个查询条件封装,并执行查询.
     *
     * @param selectQuerys
     *            多个查询查询条件封装
     * @return 联合查询执行结果
     * @author pxy
     * @since 1.0.0
     */
    protected final Result<Record> unionRecord(Field<?>[] fields, Integer offset, Integer limit, String sorts,
            List<SelectQuery<Record>> selectQuerys) {
        return unionSelectQuery(fields, offset, limit, sorts, selectQuerys).fetch();
    }

    /**
     * 
     * 联合多个查询条件封装,并执行统计.
     *
     * @param selectQuerys
     *            多个查询查询条件封装
     * @return 联合查询执行统计结果
     * @author pxy
     * @since 1.2.4
     */
    protected final int unionRecordCount(Field<?>[] fields, Integer offset, Integer limit, String sorts,
            List<SelectQuery<Record>> selectQuerys) {
        return dsl.fetchCount(unionSelectQuery(fields, offset, limit, sorts, selectQuerys));
    }

    /**
     * 
     * 联合多个查询条件封装，返回联合好的查询条件封装.
     *
     * @param selectQuerys
     *            多个查询结果
     * @return 联合好的查询条件封装
     * @author pxy
     * @since 1.0.0
     */
    protected final SelectQuery<Record> unionSelectQuery(Field<?>[] fields, Integer offset, Integer limit, String sorts,
            List<SelectQuery<Record>> selectQuerys) {
        if (selectQuerys == null || selectQuerys.isEmpty()) {
            return null;
        }
        SelectQuery<Record> result = selectQuerys.get(0);
        for (int i = 1; i < selectQuerys.size(); i++) {
            result.union(selectQuerys.get(i));
        }
        result.addOffset(offsetFormat(offset));
        result.addLimit(limitFormat(limit));
        if (sorts != null && !sorts.trim().isEmpty()) {
            result.addOrderBy(sortsFormat(sorts));
        }
        return result;
    }

    /**
     * 左外链接查询表记录.
     * 
     *
     * @param leftTable
     *            左外链接查询左表
     * @param rightTable
     *            左外链接查询右表
     * @param leftTableField
     *            左表参照字段
     * @param rightTableField
     *            右表参照字段
     * @param distinct
     *            是否合并同类项
     * @param selectConditions
     *            查询列表记录时通用的条件封装对象
     * @return 成功返回查询的记录，失败返回空值[]
     * @author zhangbo
     * @since 1.0.0
     */
    protected Result<Record> selectRecordsLeftJoin(Table<?> leftTable, Table<?> rightTable,
            TableField<?, ?> leftTableField, TableField<?, ?> rightTableField, DistinctCondition distinct,
            SelectConditions selectConditions) {
        return selectRecordsLeftJoinSelectQuery(leftTable, rightTable, leftTableField, rightTableField, distinct,
                selectConditions).fetch();
    }

    /**
     * 左外链接查询的查询语句封装.
     * 
     *
     * @param leftTable
     *            左外链接查询左表
     * @param rightTable
     *            左外链接查询右表
     * @param leftTableField
     *            左表参照字段
     * @param rightTableField
     *            右表参照字段
     * @param distinct
     *            是否合并同类项
     * @param selectConditions
     *            查询列表记录时通用的条件封装对象
     * @return 成功返回查询的记录，失败返回空值[]
     * @author zhangbo
     * @since 1.0.0
     */
    protected SelectQuery<Record> selectRecordsLeftJoinSelectQuery(Table<?> leftTable, Table<?> rightTable,
            TableField<?, ?> leftTableField, TableField<?, ?> rightTableField, DistinctCondition distinct,
            SelectConditions selectConditions) {
        SelectQuery<Record> selectQuery = dsl.selectQuery();
        String fields = selectConditions.getFields();
        String filters = selectConditions.getFilters();
        String sorts = selectConditions.getSorts();
        Integer limit = selectConditions.getLimit();
        Integer offset = selectConditions.getOffset();
        String extraFields = selectConditions.getExtraFields();
        selectQuery.setDistinct(distinct.getDistinct());
        if (fields == null && distinct.getDistinct()) {
            fields = "";
            for (Field<?> field : distinct.getTable().fields()) {
                fields += field.getName() + ",";
            }
        }
        fields = fieldsFormat(fields);
        if (fields != null && !fields.trim().isEmpty()) {
            for (String field : fields.split(",")) {
                if (distinct.getDistinct()
                        & (distinct.getTable() == null || leftTable.getName().equals(distinct.getTable().getName()))
                        & leftTable.field(field.trim()) != null) {
                    selectQuery.addSelect(leftTable.field(field.trim()));
                }
                if (distinct.getDistinct()
                        & (distinct.getTable() == null || rightTable.getName().equals(distinct.getTable().getName()))
                        & rightTable.field(field) != null) {
                    selectQuery.addSelect(rightTable.field(field));
                }
            }
        }
        if (extraFields != null && !extraFields.trim().isEmpty()) {
            for (String eFileds : extraFields.split(",")) {
                selectQuery.addSelect(leftTable.field(eFileds.trim()) == null ? rightTable.field(eFileds.trim())
                        : leftTable.field(eFileds.trim()));
            }
        }
        selectQuery.addFrom(leftTable);
        String condition = leftTableField + "=" + rightTableField;
        condition = condition.replace("\"", "`");
        selectQuery.addJoin(rightTable, JoinType.LEFT_OUTER_JOIN, DSL.condition(condition));
        if (filters != null && !filters.trim().isEmpty()) {
            String formatedFilters = filtersFormatFactory(filters);
            formatedFilters = dateFormat(leftTable, formatedFilters);
            formatedFilters = dateFormat(rightTable, formatedFilters);
            selectQuery.addConditions(DSL.condition(formatedFilters));
        }
        selectQuery.addLimit(offsetFormat(offset), limitFormat(limit));
        if (sorts != null && !sorts.trim().isEmpty()) {
            selectQuery.addOrderBy(sortsFormat(sorts));
        }
        return selectQuery;
    }

    private String dateFormat(Table<?> table, String src) {
        String dst = src;
        for (Field<?> field : table.fields()) {
            if (field.getType().equals(Date.class)) {
                dst = dst.replaceAll("`" + table.getName() + "`.`" + field.getName() + "`",
                        "unix_timestamp(`" + table.getName() + "`.`" + field.getName() + "`)");
            }
        }
        return dst;
    }

    /**
     * 选择字段格式化.
     *
     * @param fields
     *            待格式化的若干选择字段
     * @return 格式化后的字段,若为null
     * @author zhd
     * @since 1.0.0
     */
    protected String fieldsFormat(String fields) {
        if (fields == null) {
            return null;
        }
        return HttpCaseFormater.formatQuery(fields, DEFAULT_FIELD_CASE);
    }

    /**
     * 选择字段格式化并以CSV格式获取每个字段数组.
     *
     * @param fields
     *            待格式化的若干选择字段
     * @return 格式化后的字段,若为null
     * @author zhd
     * @since 1.0.0
     */
    protected String[] fieldsFormatAndSplit(String fields) {
        String fFields = fieldsFormat(fields);
        if (fFields == null) {
            return null;
        }
        return fFields.split(",");
    }

    /**
     * 
     * 兼容原来的filter编写方式，将其序列化为jooq可用的filter格式.
     *
     * @param filters
     *            带, & |符号的filters
     * @return jooq可用的标准sql条件字符串
     * @see {@link cn.signit.wesign.mss.common.dao.mapper.CommonMapper#filtersFormatFactory}
     *      .
     * @author pxy
     * @author zhd
     * @since 1.0.0
     */
    @Deprecated
    protected static String filtersFormat(String filters) {
        String tempFilters = filters;
        if (tempFilters == null) {
            return null;
        }
        LOG.info("过时filtersFormat方法filters格式化前: [ {} ]", filters);
        tempFilters = HttpCaseFormater.formatQuery(filters, DEFAULT_FIELD_CASE);
        char[] chs = tempFilters.toCharArray();
        List<Integer> op = new ArrayList<Integer>();
        int chLen = chs.length;
        boolean isOp = false;
        for (int i = 0; i < chLen; i++) {
            if ((chs[i] == '=' || chs[i] == '>' || chs[i] == '<') && i < chLen - 1 && !isOp) {
                if (chs[i] == '>' && chs[i + 1] == '=' || chs[i] == '<' && chs[i + 1] == '='
                        || chs[i] == '!' && chs[i + 1] == '=') {
                    op.add(i + 2);
                } else {
                    op.add(i + 1);
                }
                isOp = true;
            }
            if (isOp && (chs[i] == '&' || chs[i] == ',' || chs[i] == '|' || chs[i] == ')')) {
                op.add(i);
                isOp = false;
            }
        }
        if (isOp) {
            op.add(chLen);
        }
        StringBuilder strBuilder = new StringBuilder(tempFilters);
        for (int i = op.size() - 1; i >= 0; i--) {
            strBuilder.insert(op.get(i), "'");
        }
        // 格式化非打印字符和多重单引号
        String formatFilters = strBuilder.toString().replaceAll("(?:\n|\t|\r)", "").replaceAll("'\\s*'", "'")
                .replaceAll("'\\s*(?i)X'", "X'").replaceAll("'\\s*`", "`").replaceAll("`\\s*'", "`");
        // 格式化特殊字符为逻辑运算符
        formatFilters = formatFilters.replace("&", " AND ").replace(",", " AND ").replace("|", " OR ");
        // 格式化特殊符号到SQL中的特殊符号
        formatFilters = formatFilters.replace('^', ',').replace('"', '`');
        LOG.info("过时filtersFormat方法filters格式化后: [ {} ]", formatFilters);
        return formatFilters;
    }

    /**
     * 
     * 支持mySql常用关键字like,in,not in,between and ,is null,is not
     * null,not的filter的过滤器.
     *
     * 注：暂不支持 NOT(a <=> NULL )这种形式,
     * 
     * @param filters
     *            需要过滤格式的filter
     * @return 格式化之后的filter
     * @see {@link cn.signit.wesign.mss.common.dao.mapper.CommonMapper#filtersFormat}
     *      .
     * @author Ning
     * @author zhd
     * @since 1.0.0
     */
    protected String deprecatedFiltersFormatFactory(String filters) {
        if (filters == null) {
            return null;
        }
        LOG.debug("filtersFormatFactory方法filters格式化前: [ {} ]", filters);
        String str = HttpCaseFormater.formatQuery(filters, DEFAULT_FIELD_CASE);
        // 统一去掉所有的的非value"",去掉所有的非打印字符 以便做后续处理
        str = str.replaceAll("\"", "").replaceAll("(?:\n|\t|\r)", "");
        // 对所有的逻辑符号的左右边形如 库.表.列 的值加上 ``
        str = str.replaceAll("(\\w+)\\.(\\w+)\\.(\\w+)", "`$1`.`$2`.`$3`");
        // 对所有的逻辑符号的左右边形如 表.列 的值加上 ``
        str = str.replaceAll("(?<=^|&|,|\\||=|<|>|\\(|(?i)and|(?i)or)(\\w+)\\.(\\w+)", "`$1`.`$2`");
        // 对所有逻辑符号左边的列名加上``
        str = str.replaceAll("(\\w+)(?=\\s*[=!<>])", "`$1`");
        // 对所有逻辑符号右边的value加上''
        str = str.replaceAll("(?<=(?:=|<|>)\\s{0,50}\\b)(.+?)(?=\\s*(?:,|&|\\||\\)|(?i)\\s+and\\s+|(?i)\\s+or\\s+|$))",
                "'$1'");
        // 对关键字 like 、in、not in左边的列名加上``
        str = str.replaceAll("(?<=[,|]\\s{0,50})(\\w+)(?=\\s+(?i)(like|(?:(NOT\\s+)*in)))", "`$1`");
        // 对关键字between.value1.and..value2 中的value1部分加上''
        str = str.replaceAll("(?<=\\s{1,50}(?i)between\\s{1,50})([^' ]+?)(?=\\s+(?i)and\\s+)", "'$2'");
        // 对关键字between.value1.and..value2 中的value2部分加上''
        str = str.replaceAll(
                "(?<=\\s{1,50}between\\s{1,50}.{1,1000}?\\s(?i)and\\s{1,50})([^' ]+?)(?=\\s*(,|&|\\||\\)|(?i)\\s+and\\s+|(?i)\\s+or\\s+|$))",
                "'$1'");
        // 格式化特殊字符为逻辑运算符
        str = str.replaceAll("\\s*&\\s*", " AND ").replaceAll("\\s*,\\s*", " AND ").replaceAll("\\s*\\|\\s*", " OR ");
        // 对特殊关键词的格式化
        str = specialKeyWordsFormat(str);
        // 格式化多重单引号
        str = str.replaceAll("(?<=(=|<|>)\\s{0,50})('+)((?i)X'[0-9a-fA-F]+')(\\2)", "$3");
        LOG.debug("filtersFormatFactory方法filters格式化后: [ {} ]", str);
        return str;
    }

    /**
     * 
     * 支持mySql常用关键字like,in,not in,between and ,is null,is not
     * null,not的filter的过滤器.
     *
     * 注：暂不支持 NOT(a <=> NULL )这种形式,
     * 
     * @param filters
     *            需要过滤格式的filter
     * @return 格式化之后的filter
     * @see {@link cn.signit.wesign.mss.common.dao.mapper.CommonMapper#filtersFormat}
     *      .
     * @author Ning
     * @author zhd
     * @since 1.0.0
     */
    protected String filtersFormatFactory(String filters) {
        if (filters == null) {
            return null;
        }
        LOG.debug("filtersFormatFactory方法filters格式化前: [ {} ]", filters);
        String str = HttpCaseFormater.formatQuery(filters, DEFAULT_FIELD_CASE);
        // 统一去掉所有的非value值的双引号
        str = DOUBLE_QUOTATION_MARK.matcher(str).replaceAll("");
        // 去掉所有的非打印字符 以便做后续处理
        str = NON_PRINT_CHARACTERS.matcher(str).replaceAll("");
        // 对所有的逻辑符号的左右边形如 库.表.列 的值加上 ``
        str = DATABASE_TABLE_COLUMN.matcher(str).replaceAll("`$1`.`$2`.`$3`");
        // 对所有的逻辑符号的左右边形如 表.列 的值加上 ``
        str = TABLE_COLUMN.matcher(str).replaceAll("`$1`.`$2`");
        // 对所有逻辑符号左边的列名加上``
        str = LEFT_KEY.matcher(str).replaceAll("`$1`");
        // 对所有逻辑符号右边的value加上''
        str = RIGHT_VALUE.matcher(str).replaceAll("'$1'");
        // 对关键字 like 、in、not in左边的列名加上``
        str = LIKE_IN_NOTIN.matcher(str).replaceAll("`$1`");
        // 对关键字between.value1.and..value2 中的value1部分加上''
        str = BETWEEN_VALUE1.matcher(str).replaceAll("'$1'");
        // 对关键字between.value1.and..value2 中的value2部分加上''
        str = BETWEEN_VALUE2.matcher(str).replaceAll("'$1'");
        // 格式化特殊字符为逻辑运算符
        str = AND.matcher(str).replaceAll(" AND ");
        str = OR.matcher(str).replaceAll(" OR ");
        // 对特殊关键词的格式化
        str = specialKeyWordsFormat(str) ;
        // 格式化多重单引号
        str = MULTIPLE_BACKQUOTES.matcher(str).replaceAll("$3");
        LOG.debug("filtersFormatFactory方法filters格式化后: [ {} ]", str);
        return str;
    }

    // 特殊关键词的格式化
    private String specialKeyWordsFormat(String filters) {
        LOG.debug("对IN()操作的格式化支持：排除'^'和'AND'符号,里面每个值自动加上单引号");
        Matcher m = IN_OP.matcher(filters);
        StringBuffer buf = new StringBuffer();
        while (m.find()) {
            String temp = SPECIAL_KEYWORDS.matcher(m.group()).replaceAll(",");
            m.appendReplacement(buf, temp);
        }
        m.appendTail(buf);
        LOG.debug("对xxx操作的格式化支持...");
        return buf.toString();
    }

    /**
     * 
     * 模糊查询条件封装.
     *
     * @param scope
     *            需要模糊查询的字段
     * @param keyword
     *            查询关键词
     * @return 查询条件
     * @author luliangwei
     * @since 1.0.0
     */
    protected String fuzzyQuery(String scope, String keyword) {
        String tempScope = scope;
        StringBuilder sb = new StringBuilder("");
        if (tempScope != null || "".equals(tempScope)) {
            tempScope = fieldsFormat(tempScope);
            String[] fileds = tempScope.split(",");
            for (int i = 0; i < fileds.length; i++) {
                if (i < fileds.length - 1) {
                    sb.append(fileds[i]).append(" LIKE '%").append(keyword).append("%' OR ");
                } else {
                    sb.append(fileds[i]).append(" LIKE '%").append(keyword).append("%'");
                }
            }
            return sb.toString();
        }
        return "";
    }

    /**
     * 
     * 格式化sorts,将其序列化为jooq可用的排序格式.
     *
     * @param sorts
     *            带+ -符号的排序方式，如 +id -title
     * @return jooq排序可用的Collection.
     *         </p>
     *         如 +id : [id asc]
     *         </p>
     *         +id,-title :[id asc, title desc]
     * @author zhangbo
     * @since 1.0.0
     */
    protected List<? extends SortField<?>> sortsFormat(String sorts) {
        String tempSorts = sorts;
        if (tempSorts == null) {
            return null;
        }
        List<SortField<?>> sortFields = new ArrayList<>();
        for (String sort : tempSorts.split(",")) {
            int indexPlus = sort.indexOf("+");
            int indexMinus = sort.indexOf("-");
            if (indexMinus >= 0) {
                sortFields.add(DSL.field(Case.to(sort.substring(indexMinus + 1), DEFAULT_FIELD_CASE)).desc());
            } else if (indexPlus >= 0) {
                sortFields.add(DSL.field(Case.to(sort.substring(indexPlus + 1), DEFAULT_FIELD_CASE)).asc());
            }
        }
        return sortFields;
    }

    /**
     * 格式化limit,limit不符合格式时，设其值为20.
     *
     * @param limit
     *            分页参数，当前数据的页大小，默认为20
     * @return 格式化后的limit
     * @author zhangbo
     * @since 1.0.0
     */
    protected int limitFormat(Integer limit) {
        if (limit == null || limit < 0) {
            return DEFAULT_LIMIT;
        }
        return limit;
    }

    /**
     * 格式化offset,offset不符合格式时，设其值为0.
     *
     * @param offset
     *            分页参数，当前数据的偏移量，默认为0
     * @return 格式化后的offset
     * @author zhangbo
     * @since 1.0.0
     */
    protected int offsetFormat(Integer offset) {
        if (offset == null || offset < 0) {
            return DEFAULT_OFFSET;
        }
        return offset;
    }
    /**
     * 格式化非空更新的记录.
     *
     * @param record
     *            待更新的记录
     * @return 格式化后只允许非空更新的记录
     * @author zhd
     * @since 1.0.0
     */
    protected <R extends Record> R formatUpdateSelective(R record) {
        for (Field<?> f : record.fields()) {
            if (record.getValue(f) == null) {
                record.changed(f, false);
            } else {
                record.changed(f, true);
            }
        }
        return record;
    }

    /**
     * 获取指定记录中非空的字段列表.
     *
     * @param record
     *            指定记录
     * @return 记录中非空的字段列表
     * @author zhd
     * @since 1.2.6
     */
    protected List<Field<?>> getNotNullFields(Record record) {
        List<Field<?>> tmpFields = new ArrayList<Field<?>>();
        for (Field<?> f : record.fields()) {
            if (record.getValue(f) != null) {
                tmpFields.add(f);
            }
        }
        return tmpFields;
    }
}

