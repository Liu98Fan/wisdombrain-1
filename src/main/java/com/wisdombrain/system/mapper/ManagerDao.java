package com.wisdombrain.system.mapper;

import com.wisdombrain.system.entities.ParentPermission;
import com.wisdombrain.system.entities.Permission;
import com.wisdombrain.system.entities.UrlMapping;
import com.wisdombrain.system.entities.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author:LIUFAN
 * @date:2018/11/22
 */
public interface ManagerDao {
    @Select("select id,username,date,newdate,del_flag from  user_account_tb order by newdate DESC where del_flag=0 ")
    public List<User> getAllUser();

    @Select("select count(*) from user_account_tb where del_flag = 0;")
    public int getUserCount();

    @Select("select id,username,date,newdate,del_flag from user_account_tb where del_flag=0 order by newdate DESC  limit #{param1},#{param2}")
    public List<User> getUserForPage(int start, int end);

    @Select("call deleteUser(#{id})")
    public String deleteUser(String id);

    @Select("call saveMapping(#{id},#{date},#{del_flag},#{url},#{className},#{methodName},#{isAuthority},#{describe},#{parentMapping},#{sonMapping})")
    public String saveMapping(UrlMapping mapping);

    @Select("select count(*) from url_tb where del_flag = 0")
    public int getAuthorityCount();

    @Select("select * from url_tb where del_flag = 0 order by date DESC limit #{param1},#{param2}")
    public List<UrlMapping> getAuthorityForTable(int start, int end);

    //@Select("SELECT parentMapping,del_flag FROM url_tb GROUP BY parentMapping HAVING del_flag = 0 limit #{param1},#{param2}")
    //更新了SQL语句
//    @Select("select a.parentMapping ,b.`describe`,a.isAuthority,a.del_flag,b.date from \n" +
//           "(SELECT parentMapping,`describe`,del_flag,isAuthority FROM url_tb  GROUP BY parentMapping HAVING del_flag = 0 and isAuthority=0) a \n" +
//           "left join parent_url_tb  b \n" +
//           "on a.parentMapping=b.url limit #{param1},#{param2}")
    @Select("select id  ,parent,`describe` as 'parentDescribe' ,del_flag,date from parent_permission_tb where del_flag = 0 limit #{param1},#{param2}")
    public List<ParentPermission> getParentAuthorityForTable(int start, int end);

    //更新sql语句
    //@Select("SELECT * from url_tb where del_flag = 0 and parentMapping = #{parentMapping}")
//    @Select("select a.id,b.`describe`,a.url,a.methodName,a.className,a.isAuthority, a.parentMapping,a.sonMapping,a.del_flag \n" +
//            "from url_tb a \n" +
//            "left JOIN permission_tb b \n" +
//            "on a.id = b.id \n" +
//            "where a.parentMapping = #{parentMapping} and a.del_flag = 0;")
    @Select("select \n" +
            "a.id as 'id',\n" +
            "a.permission as 'permission',\n" +
            "a.`describe` as 'describe',\n" +
            "a.date as 'date',\n" +
            "a.newdate as 'newdate',\n" +
            "c.id as 'parentid',\n" +
            "c.parent as 'parent',\n" +
            "c.`describe` as 'parentdescribe',\n" +
            "a.del_flag as'del_flag' \n" +
            " from permission_tb a \n" +
            "LEFT JOIN parent_permission_relation_tb b on a.id = b.permissionid \n" +
            "LEFT JOIN parent_permission_tb c on b.parentid = c.id " +
            "where c.id =#{parent}")
    public List<Permission> getSonAuthorityForTable(String parent);

    @Select("select count(*) from (SELECT DISTINCT parentMapping FROM url_tb where del_flag = 0) parent_tb")
    public int getParentAuthorityCount();

    @Select("call saveParentMapping(#{id},#{date},#{del_flag},#{url},#{isAuthority},#{describe})")
    public String saveParentUrl(UrlMapping parentMapping);
}
